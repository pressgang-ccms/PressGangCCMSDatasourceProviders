package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;

public class RESTPropertyTagV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagWrapper,
        RESTPropertyTagV1> implements PropertyTagWrapper {

    protected RESTPropertyTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTPropertyTagV1 propertyTag, boolean isRevision,
            boolean isNewEntity) {
        super(providerFactory, propertyTag, isRevision, isNewEntity);
    }

    protected RESTPropertyTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTPropertyTagV1 propertyTag, boolean isRevision,
            boolean isNewEntity, final Collection<String>expandedMethods) {
        super(providerFactory, propertyTag, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public void setName(String name) {
        getEntity().explicitSetName(name);
    }

    @Override
    public RESTPropertyTagV1Wrapper clone(boolean deepCopy) {
        return new RESTPropertyTagV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }
}