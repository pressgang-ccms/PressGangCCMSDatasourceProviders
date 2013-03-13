package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInTagProvider extends RESTPropertyTagProvider implements PropertyTagInTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInTagProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTPropertyTagInTagProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CollectionWrapper<PropertyTagInTagWrapper> getPropertyTagInTagRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get PropertyTagInTag revisions using V1 of the REST Interface.");
    }

    public CollectionWrapper<PropertyTagInTagWrapper> getPropertyTagInTagRevisions(int id, Integer revision,
            final RESTBaseTagV1<?, ?, ?> parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTTagV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTagV1.class, tagId, tagRevision)) {
                tag = entityCache.get(RESTTagV1.class, tagId, tagRevision);
            }
    
                    /* We need to expand the all the items in the tag collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandProperties = new ExpandDataTrunk(new ExpandDataDetails(RESTTagV1.PROPERTIES_NAME));
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTAssignedPropertyTagV1.REVISIONS_NAME));

            expandProperties.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            expand.setBranches(CollectionUtilities.toArrayList(expandProperties));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTagV1 tempTag;
            if (tagRevision == null) {
                tempTag = client.getJSONTag(tagId, expandString);
            } else {
                tempTag = client.getJSONTagRevision(tagId, tagRevision, expandString);
            }

            if (tag == null) {
                tag = tempTag;
                if (tagRevision == null) {
                    entityCache.add(tag);
                } else {
                    entityCache.add(tag, tagRevision);
                }
            } else if (tag.getProperties() == null) {
                tag.setProperties(tempTag.getProperties());
            } else {
                // Iterate over the current and old properties and add any missing objects.
                final List<RESTAssignedPropertyTagV1> properties = tag.getProperties().returnItems();
                final List<RESTAssignedPropertyTagV1> newProperties = tempTag.getProperties().returnItems();
                for (final RESTAssignedPropertyTagV1 newProperty : newProperties) {
                    boolean found = false;

                    for (final RESTAssignedPropertyTagV1 property : properties) {
                        if (property.getId().equals(newProperty.getId())) {
                            property.setRevisions(newProperty.getRevisions());

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        tag.getProperties().addItem(newProperty);
                    }
                }
            }

            for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : tag.getProperties().getItems()) {
                final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

                if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                    return getWrapperFactory().createCollection(propertyTag.getRevisions(), RESTAssignedPropertyTagV1.class, true, parent);
                }
            }

        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for PropertyTagInTag " + id + (revision == null ? "" : (", Revision " + revision)),
                    e);
        }

        return null;
    }
}
