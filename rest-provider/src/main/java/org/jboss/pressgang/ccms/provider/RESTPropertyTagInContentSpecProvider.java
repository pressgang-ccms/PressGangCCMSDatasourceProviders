package org.jboss.pressgang.ccms.provider;

import java.util.Arrays;
import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInContentSpecProvider extends RESTPropertyTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInContentSpecProvider.class);

    protected RESTPropertyTagInContentSpecProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTContentSpecV1 loadContentSpec(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONContentSpec(id, expandString);
        } else {
            return getRESTClient().getJSONContentSpecRevision(id, revision, expandString);
        }
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTPropertyTagInContentSpecRevisions(int id, Integer revision,
            final RESTContentSpecV1 parent) {
        final Integer contentSpecId = parent.getId();
        final Integer contentSpecRevision = ((RESTBaseEntityV1ProxyHandler<RESTContentSpecV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTContentSpecV1.class, contentSpecId, contentSpecRevision)) {
                contentSpec = getRESTEntityCache().get(RESTContentSpecV1.class, contentSpecId, contentSpecRevision);
            }

            // We need to expand the all the items in the contentSpec collection
            final String expandString = getExpansionString(RESTContentSpecV1.PROPERTIES_NAME, RESTAssignedPropertyTagV1.REVISIONS_NAME);

            // Load the Content Spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec = loadContentSpec(contentSpecId, contentSpecRevision, expandString);

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                getRESTEntityCache().add(contentSpec, contentSpecRevision);
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

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Revisions for PropertyTagInContentSpec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<PropertyTagInContentSpecWrapper> getPropertyTagInContentSpecRevisions(int id, Integer revision,
            final RESTContentSpecV1 parent) {
        return RESTCollectionWrapperBuilder.<PropertyTagInContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTPropertyTagInContentSpecRevisions(id, revision, parent))
                .isRevisionCollection()
                .parent(parent)
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .entityWrapperInterface(PropertyTagInContentSpecWrapper.class)
                .build();
    }
}
