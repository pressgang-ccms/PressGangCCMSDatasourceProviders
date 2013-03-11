package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseCategoryV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTCategoryV1Wrapper extends RESTBaseCategoryV1Wrapper<CategoryWrapper, RESTCategoryV1> implements CategoryWrapper {

    private final RESTCategoryV1 category;

    protected RESTCategoryV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryV1 category, boolean isRevision) {
        super(providerFactory, isRevision);
        this.category = RESTEntityProxyFactory.createProxy(providerFactory, category, isRevision);
    }

    @Override
    public RESTCategoryV1Wrapper clone(boolean deepCopy) {
        return new RESTCategoryV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    protected RESTCategoryV1 getProxyEntity() {
        return category;
    }

    @Override
    public void setTags(UpdateableCollectionWrapper<TagInCategoryWrapper> tags) {
        getProxyEntity().explicitSetTags(tags == null ? null : (RESTTagInCategoryCollectionV1) tags.unwrap());
    }

    @Override
    public void setName(String name) {
        getProxyEntity().explicitSetName(name);
    }

    @Override
    public CollectionWrapper<CategoryWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTCategoryV1.class, true);
    }
}
