package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTTagInCategoryV1Wrapper extends RESTBaseTagV1Wrapper<TagInCategoryWrapper,
        RESTTagInCategoryV1> implements TagInCategoryWrapper {
    private final RESTBaseCategoryV1<?, ?, ?> parent;
    private final RESTTagInCategoryV1 tag;

    protected RESTTagInCategoryV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagInCategoryV1 tag, boolean isRevision,
            final RESTBaseCategoryV1<?, ?, ?> parent) {
        super(providerFactory, isRevision);
        this.tag = RESTEntityProxyFactory.createProxy(providerFactory, tag, isRevision, parent);
        this.parent = parent;
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
        return new RESTTagInCategoryV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), parent);
    }

    @Override
    protected RESTTagInCategoryV1 getProxyEntity() {
        return tag;
    }

    @Override
    public CollectionWrapper<TagInCategoryWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTagInCategoryV1.class, true, parent);
    }
}
