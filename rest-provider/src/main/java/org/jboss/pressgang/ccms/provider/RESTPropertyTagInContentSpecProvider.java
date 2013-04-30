package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInContentSpecProvider extends RESTPropertyTagProvider implements PropertyTagInContentSpecProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInContentSpecProvider.class);

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTPropertyTagInContentSpecProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    protected RESTContentSpecV1 loadContentSpec(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return client.getJSONContentSpec(id, expandString);
        } else {
            return client.getJSONContentSpecRevision(id, revision, expandString);
        }
    }

    @Override
    public CollectionWrapper<PropertyTagInContentSpecWrapper> getPropertyTagInContentSpecRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException(
                "A parent is needed to get PropertyTagInContentSpec revisions using V1 of the REST Interface.");
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTPropertyTagInContentSpecRevisions(int id, Integer revision,
            final RESTContentSpecV1 parent) {
        final Integer contentSpecId = parent.getId();
        final Integer contentSpecRevision = ((RESTBaseEntityV1ProxyHandler<RESTContentSpecV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTContentSpecV1.class, contentSpecId, contentSpecRevision)) {
                contentSpec = entityCache.get(RESTContentSpecV1.class, contentSpecId, contentSpecRevision);
            }

            // We need to expand the all the items in the contentSpec collection
            final String expandString = getExpansionString(RESTContentSpecV1.PROPERTIES_NAME, RESTAssignedPropertyTagV1.REVISIONS_NAME);

            // Load the Content Spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec = loadContentSpec(contentSpecId, contentSpecRevision, expandString);

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                entityCache.add(contentSpec, contentSpecRevision);
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

            // Find the matching property tag and return it's revisions
            for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : contentSpec.getProperties().getItems()) {
                final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

                if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                    return propertyTag.getRevisions();
                }
            }
        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for PropertyTagInContentSpec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }

        return null;
    }

    public CollectionWrapper<PropertyTagInContentSpecWrapper> getPropertyTagInContentSpecRevisions(int id, Integer revision,
            final RESTContentSpecV1 parent) {
        return getWrapperFactory().createCollection(getRESTPropertyTagInContentSpecRevisions(id, revision, parent),
                RESTAssignedPropertyTagV1.class, true, parent);
    }
}