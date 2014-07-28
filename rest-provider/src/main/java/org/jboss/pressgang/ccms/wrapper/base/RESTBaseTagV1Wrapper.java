/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTagProvider;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public abstract class RESTBaseTagV1Wrapper<T extends BaseTagWrapper<T>, U extends RESTBaseTagV1<U, ?, ?>> extends RESTBaseEntityWrapper<T,
        U> implements BaseTagWrapper<T> {
    private final RESTTagProvider dataProvider;

    protected RESTBaseTagV1Wrapper(RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity) {
        this(providerFactory, entity, isRevision, null, isNewEntity);
    }

    protected RESTBaseTagV1Wrapper(RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity,
            final Collection<String> expandedMethods) {
        this(providerFactory, entity, isRevision, null, isNewEntity, expandedMethods);
    }

    protected RESTBaseTagV1Wrapper(RESTProviderFactory providerFactory, U entity, boolean isRevision,
            final RESTBaseEntityV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, parent, isNewEntity);
        dataProvider = providerFactory.getProvider(RESTTagProvider.class);
    }

    protected RESTBaseTagV1Wrapper(RESTProviderFactory providerFactory, U entity, boolean isRevision,
            final RESTBaseEntityV1<?, ?, ?> parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, parent, isNewEntity, expandedMethods);
        dataProvider = providerFactory.getProvider(RESTTagProvider.class);
    }

    protected RESTTagProvider getProvider() {
        return dataProvider;
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }

    @Override
    public CollectionWrapper<TagWrapper> getParentTags() {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getParentTags())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public CollectionWrapper<TagWrapper> getChildTags() {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getChildTags())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public UpdateableCollectionWrapper<CategoryInTagWrapper> getCategories() {
        return (UpdateableCollectionWrapper<CategoryInTagWrapper>) RESTCollectionWrapperBuilder.<CategoryInTagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getCategories())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .build();
    }

    @Override
    public PropertyTagInTagWrapper getProperty(int propertyId) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(ComponentTagV1.returnProperty(getProxyEntity(), propertyId))
                .isRevision(isRevisionEntity())
                .parent(getProxyEntity())
                .wrapperInterface(PropertyTagInTagWrapper.class)
                .build();
    }

    @Override
    public boolean containedInCategory(int categoryId) {
        return ComponentTagV1.containedInCategory(getProxyEntity(), categoryId);
    }

    @Override
    public boolean containedInCategories(List<Integer> categoryIds) {
        return ComponentTagV1.containedInCategory(getProxyEntity(), categoryIds);
    }
}
