package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTServerUndefinedSettingV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;

public class RESTServerUndefinedSettingV1Wrapper extends RESTBaseWrapper<ServerUndefinedSettingWrapper, RESTServerUndefinedSettingV1> implements ServerUndefinedSettingWrapper {
    protected RESTServerUndefinedSettingV1Wrapper(RESTProviderFactory providerFactory, RESTServerUndefinedSettingV1 entity) {
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
    public String getValue() {
        return getEntity().getValue();
    }

    @Override
    public void setValue(String value) {
        getEntity().explicitSetValue(value);
    }
}
