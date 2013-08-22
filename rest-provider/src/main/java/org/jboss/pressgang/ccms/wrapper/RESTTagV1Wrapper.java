package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTTagV1Wrapper extends RESTBaseTagV1Wrapper<TagWrapper, RESTTagV1> implements TagWrapper {

    protected RESTTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagV1 tag, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, tag, isRevision, isNewEntity);
    }

    @Override
    public RESTTagV1Wrapper clone(boolean deepCopy) {
        return new RESTTagV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public CollectionWrapper<TagWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTagV1.class, true);
    }

    @Override
    public void setCategories(UpdateableCollectionWrapper<CategoryInTagWrapper> categories) {
        getEntity().explicitSetCategories(categories == null ? null : (RESTCategoryInTagCollectionV1) categories.unwrap());
    }
}
