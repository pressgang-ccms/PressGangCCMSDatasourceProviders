package org.jboss.pressgang.ccms.contentspec.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInContentSpecProvider extends RESTPropertyTagProvider implements PropertyTagInContentSpecProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInContentSpecProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTPropertyTagInContentSpecProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CollectionWrapper<PropertyTagInContentSpecWrapper> getPropertyTagInContentSpecRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException(
                "A parent is needed to get PropertyTagInContentSpec revisions using V1 of the REST Interface.");
    }

    public CollectionWrapper<PropertyTagInContentSpecWrapper> getPropertyTagInContentSpecRevisions(int id, Integer revision,
            final RESTContentSpecV1 parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTContentSpecV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTContentSpecV1.class, tagId, tagRevision)) {
                contentSpec = entityCache.get(RESTContentSpecV1.class, tagId, tagRevision);
            }
    
            /* We need to expand the all the items in the contentSpec collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandProperties = new ExpandDataTrunk(new ExpandDataDetails(RESTContentSpecV1.PROPERTIES_NAME));
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTAssignedPropertyTagV1.REVISIONS_NAME));

            expandProperties.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            expand.setBranches(CollectionUtilities.toArrayList(expandProperties));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTContentSpecV1 tempContentSpec;
            if (tagRevision == null) {
                tempContentSpec = client.getJSONContentSpec(tagId, expandString);
            } else {
                tempContentSpec = client.getJSONContentSpecRevision(tagId, tagRevision, expandString);
            }

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                if (tagRevision == null) {
                    entityCache.add(contentSpec);
                } else {
                    entityCache.add(contentSpec, tagRevision);
                }
            } else if (contentSpec.getProperties() == null) {
                contentSpec.setProperties(tempContentSpec.getProperties());
            } else {
                // Iterate over the current and old properties and add any missing objects.
                final List<RESTAssignedPropertyTagV1> properties = contentSpec.getProperties().returnItems();
                final List<RESTAssignedPropertyTagV1> newProperties = tempContentSpec.getProperties().returnItems();
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
                        contentSpec.getProperties().addItem(newProperty);
                    }
                }
            }

            for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : contentSpec.getProperties().getItems()) {
                final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

                if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                    return getWrapperFactory().createCollection(propertyTag.getRevisions(), RESTAssignedPropertyTagV1.class, true, parent);
                }
            }

        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for PropertyTagInContentSpec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }

        return null;
    }
}
