package org.jboss.pressgang.ccms.contentspec.wrapper.base;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTBaseWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;

public abstract class RESTBaseCategoryV1Wrapper<T extends BaseCategoryWrapper<T>, U extends RESTBaseCategoryV1<U, ?,
        ?>> extends RESTBaseWrapper<T, U> implements BaseCategoryWrapper<T> {

    protected RESTBaseCategoryV1Wrapper(final RESTProviderFactory providerFactory, boolean isRevision) {
        super(providerFactory, isRevision);
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }

    @Override
    public UpdateableCollectionWrapper<TagInCategoryWrapper> getTags() {
        final CollectionWrapper<TagInCategoryWrapper> collection = getWrapperFactory().createCollection(getProxyEntity().getTags(),
                RESTTagInCategoryV1.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<TagInCategoryWrapper>) collection;
    }

    @Override
    public boolean isMutuallyExclusive() {
        return getProxyEntity().getMutuallyExclusive();
    }
}
