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
