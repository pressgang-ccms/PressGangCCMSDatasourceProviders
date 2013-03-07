package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.RESTBaseCategoryV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;

public class RESTCategoryInTagV1Wrapper extends RESTBaseCategoryV1Wrapper<CategoryInTagWrapper,
        RESTCategoryInTagV1> implements CategoryInTagWrapper {
    private final RESTBaseTagV1<?, ?, ?> parent;
    private final RESTCategoryInTagV1 category;

    protected RESTCategoryInTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryInTagV1 category,
            boolean isRevision, final RESTBaseTagV1<?, ?, ?> parent) {
        super(providerFactory, isRevision);
        this.category = RESTEntityProxyFactory.createProxy(providerFactory, category, isRevision, parent);
        this.parent = parent;
    }

    @Override
    public RESTCategoryInTagV1Wrapper clone(boolean deepCopy) {
        return new RESTCategoryInTagV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent);
    }

    @Override
    protected RESTCategoryInTagV1 getProxyEntity() {
        return category;
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

    @Override
    public CollectionWrapper<CategoryInTagWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTCategoryInTagV1.class, true, parent);
    }
}
