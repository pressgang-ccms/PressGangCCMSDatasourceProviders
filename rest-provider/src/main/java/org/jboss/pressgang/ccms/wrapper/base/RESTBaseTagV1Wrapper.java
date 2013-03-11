package org.jboss.pressgang.ccms.wrapper.base;

import java.util.List;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTagProvider;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public abstract class RESTBaseTagV1Wrapper<T extends BaseTagWrapper<T>, U extends RESTBaseTagV1<U, ?, ?>> extends RESTBaseWrapper<T,
        U> implements BaseTagWrapper<T> {

    private final RESTTagProvider dataProvider;

    protected RESTBaseTagV1Wrapper(RESTProviderFactory providerFactory, boolean isRevision) {
        super(providerFactory, isRevision);
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
        return getWrapperFactory().createCollection(getProxyEntity().getParentTags(), RESTTagV1.class, isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TagWrapper> getChildTags() {
        return getWrapperFactory().createCollection(getProxyEntity().getChildTags(), RESTTagV1.class, isRevisionEntity());
    }

    @SuppressWarnings("unchecked")
    @Override
    public UpdateableCollectionWrapper<CategoryInTagWrapper> getCategories() {
        final CollectionWrapper<CategoryInTagWrapper> collection = getWrapperFactory().createCollection(getProxyEntity().getCategories(),
                RESTCategoryInTagV1.class, isRevisionEntity(), getEntity());
        return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
    }

    @Override
    public PropertyTagInTagWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(ComponentTagV1.returnProperty(getProxyEntity(), propertyId), isRevisionEntity(),
                PropertyTagInTagWrapper.class);
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
