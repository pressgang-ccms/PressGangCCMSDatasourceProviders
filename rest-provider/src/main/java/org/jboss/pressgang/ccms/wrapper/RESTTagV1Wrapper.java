package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTTagV1Wrapper extends RESTBaseTagV1Wrapper<TagWrapper, RESTTagV1> implements TagWrapper {

    private final RESTTagV1 tag;

    protected RESTTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagV1 tag, boolean isRevision) {
        super(providerFactory, isRevision);
        this.tag = RESTEntityProxyFactory.createProxy(providerFactory, tag, isRevision);
    }

    @Override
    public RESTTagV1Wrapper clone(boolean deepCopy) {
        return new RESTTagV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity());
    }

    @Override
    protected RESTTagV1 getProxyEntity() {
        return tag;
    }

    @Override
    public CollectionWrapper<TagWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTagV1.class, true);
    }
}
