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
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseCategoryV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTCategoryInTagV1Wrapper extends RESTBaseCategoryV1Wrapper<CategoryInTagWrapper,
        RESTCategoryInTagV1> implements CategoryInTagWrapper {
    private final RESTBaseTagV1<?, ?, ?> parent;

    protected RESTCategoryInTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryInTagV1 category, boolean isRevision,
            final RESTBaseTagV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, category, isRevision, parent, isNewEntity);
        this.parent = parent;
    }

    protected RESTCategoryInTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryInTagV1 category, boolean isRevision,
            final RESTBaseTagV1<?, ?, ?> parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, category, isRevision, parent, isNewEntity, expandedMethods);
        this.parent = parent;
    }

    @Override
    public RESTCategoryInTagV1Wrapper clone(boolean deepCopy) {
        return new RESTCategoryInTagV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent, isNewEntity());
    }

    @Override
    public void setName(String name) {
        getEntity().setName(name);
    }

    @Override
    public Integer getRelationshipId() {
        return getProxyEntity().getRelationshipId();
    }

    @Override
    public Integer getRelationshipSort() {
        return getProxyEntity().getRelationshipSort();
    }

    @Override
    public void setTags(UpdateableCollectionWrapper<TagInCategoryWrapper> tags) {
        getProxyEntity().setTags(tags == null ? null : (RESTTagInCategoryCollectionV1) tags.unwrap());
    }
}
