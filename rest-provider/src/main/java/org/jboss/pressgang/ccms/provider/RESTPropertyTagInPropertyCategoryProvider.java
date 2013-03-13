package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTPropertyTagInPropertyCategoryCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInPropertyCategoryProvider extends RESTPropertyTagProvider implements PropertyTagInPropertyCategoryProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInPropertyCategoryProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTPropertyTagInPropertyCategoryProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CollectionWrapper<PropertyTagInPropertyCategoryWrapper> getPropertyTagInPropertyCategoryRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException(
                "A parent is needed to get PropertyTagInPropertyCategory revisions using V1 of the REST Interface.");
    }

    public CollectionWrapper<PropertyTagInPropertyCategoryWrapper> getPropertyTagInPropertyCategoryRevisions(int id, Integer revision,
            final RESTPropertyCategoryV1 parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTPropertyCategoryV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTPropertyCategoryV1 propertyCategory = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTPropertyCategoryV1.class, tagId, tagRevision)) {
                propertyCategory = entityCache.get(RESTPropertyCategoryV1.class, tagId, tagRevision);
            }
    
            /* We need to expand the all the items in the propertyCategory collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandProperties = new ExpandDataTrunk(new ExpandDataDetails(RESTPropertyCategoryV1.PROPERTY_TAGS_NAME));
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(
                    new ExpandDataDetails(RESTPropertyTagInPropertyCategoryV1.REVISIONS_NAME));

            expandProperties.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            expand.setBranches(CollectionUtilities.toArrayList(expandProperties));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTPropertyCategoryV1 tempPropertyCategory;
            if (tagRevision == null) {
                tempPropertyCategory = client.getJSONPropertyCategory(tagId, expandString);
            } else {
                tempPropertyCategory = client.getJSONPropertyCategoryRevision(tagId, tagRevision, expandString);
            }

            if (propertyCategory == null) {
                propertyCategory = tempPropertyCategory;
                if (tagRevision == null) {
                    entityCache.add(propertyCategory);
                } else {
                    entityCache.add(propertyCategory, tagRevision);
                }
            } else if (propertyCategory.getPropertyTags() == null) {
                propertyCategory.setPropertyTags(tempPropertyCategory.getPropertyTags());
            } else {
                // Iterate over the current and old properties and add any missing objects.
                final List<RESTPropertyTagInPropertyCategoryV1> properties = propertyCategory.getPropertyTags().returnItems();
                final List<RESTPropertyTagInPropertyCategoryV1> newProperties = tempPropertyCategory.getPropertyTags().returnItems();
                for (final RESTPropertyTagInPropertyCategoryV1 newProperty : newProperties) {
                    boolean found = false;

                    for (final RESTPropertyTagInPropertyCategoryV1 property : properties) {
                        if (property.getId().equals(newProperty.getId())) {
                            property.setRevisions(newProperty.getRevisions());

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        propertyCategory.getPropertyTags().addItem(newProperty);
                    }
                }
            }

            for (final RESTPropertyTagInPropertyCategoryCollectionItemV1 propertyItem : propertyCategory.getPropertyTags().getItems()) {
                final RESTPropertyTagInPropertyCategoryV1 propertyTag = propertyItem.getItem();

                if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                    return getWrapperFactory().createCollection(propertyTag.getRevisions(), RESTPropertyTagInPropertyCategoryV1.class, true,
                            parent);
                }
            }

        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for PropertyTagInPropertyCategory " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }

        return null;
    }
}
