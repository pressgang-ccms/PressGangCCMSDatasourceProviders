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

package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTTagInCategoryV1Wrapper extends RESTBaseTagV1Wrapper<TagInCategoryWrapper,
        RESTTagInCategoryV1> implements TagInCategoryWrapper {

    protected RESTTagInCategoryV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagInCategoryV1 tag, boolean isRevision,
            final RESTBaseCategoryV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, tag, isRevision, parent, isNewEntity);
    }

    protected RESTTagInCategoryV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagInCategoryV1 tag, boolean isRevision,
            final RESTBaseCategoryV1<?, ?, ?> parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, tag, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTBaseCategoryV1<?, ?, ?> getParentEntity() {
        return (RESTBaseCategoryV1<?, ?, ?>) super.getParentEntity();
    }

    @Override
    public Integer getInCategorySort() {
        return getProxyEntity().getRelationshipSort();
    }

    @Override
    public Integer getRelationshipId() {
        return getProxyEntity().getRelationshipId();
    }

    @Override
    public RESTTagInCategoryV1Wrapper clone(boolean deepCopy) {
        return new RESTTagInCategoryV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), getParentEntity(),
                isNewEntity(), getProxyProcessedMethodNames());
    }

    @Override
    public void setCategories(UpdateableCollectionWrapper<CategoryInTagWrapper> categories) {
        getEntity().setCategories(categories == null ? null : (RESTCategoryInTagCollectionV1) categories.unwrap());
    }
}
