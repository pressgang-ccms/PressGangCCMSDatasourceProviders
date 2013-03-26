package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInTagProvider extends RESTPropertyTagProvider implements PropertyTagInTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInTagProvider.class);

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTPropertyTagInTagProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    protected RESTTagV1 loadTag(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return client.getJSONTag(id, expandString);
        } else {
            return client.getJSONTagRevision(id, revision, expandString);
        }
    }

    @Override
    public CollectionWrapper<PropertyTagInTagWrapper> getPropertyTagInTagRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get PropertyTagInTag revisions using V1 of the REST Interface.");
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTPropertyTagInTagRevisions(int id, Integer revision,
            final RESTBaseTagV1<?, ?, ?> parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTTagV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTagV1.class, tagId, tagRevision)) {
                tag = entityCache.get(RESTTagV1.class, tagId, tagRevision);
            }

            // We need to expand the all property tag revisions in the tag
            final String expandString = getExpansionString(RESTTagV1.PROPERTIES_NAME, RESTAssignedPropertyTagV1.REVISIONS_NAME);

            // Load the tag from the REST Interface
            final RESTTagV1 tempTag = loadTag(id, revision, expandString);

            if (tag == null) {
                tag = tempTag;
                entityCache.add(tag, tagRevision);
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
                    return propertyTag.getRevisions();
                }
            }

        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for PropertyTagInTag " + id + (revision == null ? "" : (", Revision " + revision)),
                    e);
        }

        return null;
    }

    public CollectionWrapper<PropertyTagInTagWrapper> getPropertyTagInTagRevisions(int id, Integer revision,
            final RESTBaseTagV1<?, ?, ?> parent) {
        return getWrapperFactory().createCollection(getRESTPropertyTagInTagRevisions(id, revision, parent), RESTAssignedPropertyTagV1.class,
                true, parent);
    }
}
