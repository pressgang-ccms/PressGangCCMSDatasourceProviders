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
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTTagInCategoryCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTagInCategoryProvider extends RESTTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTagInCategoryProvider.class);

    protected RESTTagInCategoryProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    public RESTTagInCategoryCollectionV1 getRESTTagInCategoryRevisions(int id, Integer revision, final RESTBaseCategoryV1<?, ?, ?> parent) {
        try {
            final Integer categoryId = parent.getId();
            final Integer categoryRevision = ((RESTBaseEntityV1ProxyHandler<RESTTagV1>) ((ProxyObject) parent).getHandler())
                    .getEntityRevision();

            RESTCategoryV1 category = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCategoryV1.class, categoryId, categoryRevision)) {
                category = getRESTEntityCache().get(RESTCategoryV1.class, categoryId, categoryRevision);
            }

            // We need to expand the all the revisions in the category
            final String expandString = getExpansionString(RESTCategoryV1.TAGS_NAME, RESTTagV1.REVISIONS_NAME);

            final RESTCategoryV1 tempCategory;
            if (categoryRevision == null) {
                tempCategory = getRESTClient().getJSONCategory(categoryId, expandString);
            } else {
                tempCategory = getRESTClient().getJSONCategoryRevision(categoryId, categoryRevision, expandString);
            }

            if (category == null) {
                category = tempCategory;
                getRESTEntityCache().add(category, categoryRevision);
            } else if (category.getTags() == null) {
                category.setTags(tempCategory.getTags());
            } else {
                // Iterate over the current and old source urls and add any missing objects.
                final List<RESTTagInCategoryV1> tags = category.getTags().returnItems();
                final List<RESTTagInCategoryV1> newTags = tempCategory.getTags().returnItems();
                for (final RESTTagInCategoryV1 newTag : newTags) {
                    boolean found = false;

                    for (final RESTTagInCategoryV1 tag : tags) {
                        if (tag.getId().equals(newTag.getId())) {
                            tag.setRevisions(newTag.getRevisions());

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        category.getTags().addItem(newTag);
                    }
                }
            }

            for (final RESTTagInCategoryCollectionItemV1 tagItem : category.getTags().getItems()) {
                final RESTTagInCategoryV1 tag = tagItem.getItem();

                if (tag.getId() == id && (revision == null || tag.getRevision().equals(revision))) {
                    return tag.getRevisions();
                }
            }

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Revisions for TagInCategory " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<TagInCategoryWrapper> getTagInCategoryRevisions(int id, Integer revision,
            final RESTBaseCategoryV1<?, ?, ?> parent) {
        return RESTCollectionWrapperBuilder.<TagInCategoryWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTagInCategoryRevisions(id, revision, parent))
                .isRevisionCollection()
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }
}
