package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTPropertyTagInContentSpecV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagInContentSpecWrapper,
        RESTAssignedPropertyTagV1> implements PropertyTagInContentSpecWrapper {
    private final RESTContentSpecV1 parent;

    protected RESTPropertyTagInContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTAssignedPropertyTagV1 propertyTag,
            boolean isRevision, final RESTContentSpecV1 parent, boolean isNewEntity) {
        super(providerFactory, propertyTag, isRevision, parent, isNewEntity);
        this.parent = parent;
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
    public String getValue() {
        return getProxyEntity().getValue();
    }

    @Override
    public void setValue(String value) {
        getEntity().explicitSetValue(value);
    }

    @Override
    public Boolean isValid() {
        return getProxyEntity().getValid();
    }

    @Override
    public RESTPropertyTagInContentSpecV1Wrapper clone(boolean deepCopy) {
        return new RESTPropertyTagInContentSpecV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent,
                isNewEntity());
    }

    @Override
    public CollectionWrapper<PropertyTagInContentSpecWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTAssignedPropertyTagV1.class, true, parent,
                PropertyTagInContentSpecWrapper.class);
    }
}
