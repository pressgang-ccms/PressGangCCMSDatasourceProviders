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

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public abstract class RESTBaseCategoryV1Wrapper<T extends BaseCategoryWrapper<T>, U extends RESTBaseCategoryV1<U, ?,
        ?>> extends RESTBaseEntityWrapper<T, U> implements BaseCategoryWrapper<T> {

    protected RESTBaseCategoryV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTBaseCategoryV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity,
            final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, isNewEntity, expandedMethods);
    }

    protected RESTBaseCategoryV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision,
            RESTBaseEntityV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, parent, isNewEntity);
    }

    protected RESTBaseCategoryV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision,
            RESTBaseEntityV1<?, ?, ?> parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }

    @Override
    public UpdateableCollectionWrapper<TagInCategoryWrapper> getTags() {
        return (UpdateableCollectionWrapper<TagInCategoryWrapper>) RESTCollectionWrapperBuilder.<TagInCategoryWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTags())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public boolean isMutuallyExclusive() {
        return getProxyEntity().getMutuallyExclusive();
    }
}
