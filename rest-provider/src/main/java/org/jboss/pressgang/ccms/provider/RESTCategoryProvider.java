/*
  Copyright 2011-2014 Red Hat

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

import org.jboss.pressgang.ccms.rest.v1.collections.RESTCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCategoryProvider extends RESTDataProvider implements CategoryProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCategoryProvider.class);

    protected RESTCategoryProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTCategoryV1 loadCategory(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONCategory(id, expandString);
        } else {
            return getRESTClient().getJSONCategoryRevision(id, revision, expandString);
        }
    }

    public RESTCategoryV1 getRESTCategory(int id) {
        return getRESTCategory(id, null);
    }

    @Override
    public CategoryWrapper getCategory(int id) {
        return getCategory(id, null);
    }

    public RESTCategoryV1 getRESTCategory(int id, final Integer revision) {
        try {
            final RESTCategoryV1 category;
            if (getRESTEntityCache().containsKeyValue(RESTCategoryV1.class, id, revision)) {
                category = getRESTEntityCache().get(RESTCategoryV1.class, id, revision);
            } else {
                category = loadCategory(id, revision, "");
                getRESTEntityCache().add(category, revision);
            }
            return category;
        } catch (Exception e) {
            log.debug("Failed to retrieve Category " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CategoryWrapper getCategory(int id, final Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTCategory(id, revision))
                .isRevision(revision != null)
                .build();
    }

    public RESTTagInCategoryCollectionV1 getRESTCategoryTags(int id, final Integer revision) {
        try {
            RESTCategoryV1 category = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCategoryV1.class, id, revision)) {
                category = getRESTEntityCache().get(RESTCategoryV1.class, id, revision);

                if (category.getTags() != null) {
                    return category.getTags();
                }
            }

            // We need to expand the tags in the category
            final String expandString = getExpansionString(RESTCategoryV1.TAGS_NAME);

            // Load the category from the REST Interface
            final RESTCategoryV1 tempCategory = loadCategory(id, revision, expandString);

            if (category == null) {
                category = tempCategory;
                getRESTEntityCache().add(category, revision);
            } else {
                category.setTags(tempCategory.getTags());
            }
            getRESTEntityCache().add(category.getTags(), revision != null);

            return category.getTags();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Tags for Category " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTCategoryCollectionV1 getRESTCategoryRevisions(int id, final Integer revision) {
        try {
            RESTCategoryV1 category = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCategoryV1.class, id, revision)) {
                category = getRESTEntityCache().get(RESTCategoryV1.class, id, revision);

                if (category.getRevisions() != null) {
                    return category.getRevisions();
                }
            }

            // We need to expand the revisions in the category collection
            final String expandString = getExpansionString(RESTCategoryV1.REVISIONS_NAME);

            // Load the category from the REST Interface
            final RESTCategoryV1 tempCategory = loadCategory(id, revision, expandString);

            if (category == null) {
                category = tempCategory;
                getRESTEntityCache().add(category, revision);
            } else {
                category.setRevisions(tempCategory.getRevisions());
            }

            return category.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Category " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<CategoryWrapper> getCategoryRevisions(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<CategoryWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTCategoryRevisions(id, revision))
                .isRevisionCollection()
                .build();
    }
}
