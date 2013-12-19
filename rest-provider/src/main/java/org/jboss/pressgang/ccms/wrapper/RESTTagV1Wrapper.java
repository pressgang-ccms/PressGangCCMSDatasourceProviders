package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTTagV1Wrapper extends RESTBaseTagV1Wrapper<TagWrapper, RESTTagV1> implements TagWrapper {

    protected RESTTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagV1 tag, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, tag, isRevision, isNewEntity);
    }

    protected RESTTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagV1 tag, boolean isRevision, boolean isNewEntity,
            final Collection<String> expandedMethods) {
        super(providerFactory, tag, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public RESTTagV1Wrapper clone(boolean deepCopy) {
        return new RESTTagV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public void setCategories(UpdateableCollectionWrapper<CategoryInTagWrapper> categories) {
        getEntity().explicitSetCategories(categories == null ? null : (RESTCategoryInTagCollectionV1) categories.unwrap());
    }
}
