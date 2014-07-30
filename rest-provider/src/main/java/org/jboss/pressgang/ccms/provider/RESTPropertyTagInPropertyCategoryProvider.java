/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.provider;

import java.util.Arrays;
import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTPropertyTagInPropertyCategoryCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTPropertyTagInPropertyCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInPropertyCategoryProvider extends RESTPropertyTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInPropertyCategoryProvider.class);

    protected RESTPropertyTagInPropertyCategoryProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTPropertyCategoryV1 loadPropertyCategory(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONPropertyCategory(id, expandString);
        } else {
            return getRESTClient().getJSONPropertyCategoryRevision(id, revision, expandString);
        }
    }

    public RESTPropertyTagInPropertyCategoryCollectionV1 getRESTPropertyTagInPropertyCategoryRevisions(int id, Integer revision,
            final RESTPropertyCategoryV1 parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTPropertyCategoryV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTPropertyCategoryV1 propertyCategory = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTPropertyCategoryV1.class, tagId, tagRevision)) {
                propertyCategory = getRESTEntityCache().get(RESTPropertyCategoryV1.class, tagId, tagRevision);
            }
    
            // We need to expand the all the property tags revisions in the property category
            final String expandString = getExpansionString(RESTPropertyCategoryV1.PROPERTY_TAGS_NAME,
                    RESTPropertyTagInPropertyCategoryV1.REVISIONS_NAME);

            // Load the property category from the REST Interface
            final RESTPropertyCategoryV1 tempPropertyCategory = loadPropertyCategory(tagId, tagRevision, expandString);

            if (propertyCategory == null) {
                propertyCategory = tempPropertyCategory;
                getRESTEntityCache().add(propertyCategory, tagRevision);
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
                    return propertyTag.getRevisions();
                }
            }

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Revisions for PropertyTagInPropertyCategory " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<PropertyTagInPropertyCategoryWrapper> getPropertyTagInPropertyCategoryRevisions(int id, Integer revision,
            final RESTPropertyCategoryV1 parent) {
        return RESTCollectionWrapperBuilder.<PropertyTagInPropertyCategoryWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTPropertyTagInPropertyCategoryRevisions(id, revision, parent))
                .isRevisionCollection()
                .parent(parent)
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }
}
