package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerUndefinedEntityV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;

public class RESTServerUndefinedEntityV1Wrapper extends RESTBaseWrapper<ServerUndefinedEntityWrapper, RESTServerUndefinedEntityV1> implements ServerUndefinedEntityWrapper {
    protected RESTServerUndefinedEntityV1Wrapper(RESTProviderFactory providerFactory, RESTServerUndefinedEntityV1 entity) {
        super(providerFactory, entity);
    }

    @Override
    public String getKey() {
        return getEntity().getKey();
    }

    @Override
    public void setKey(String key) {
        getEntity().setKey(key);
    }

    @Override
    public Integer getValue() {
        return getEntity().getValue();
    }

    @Override
    public void setValue(Integer value) {
        getEntity().explicitSetValue(value);
    }
}
