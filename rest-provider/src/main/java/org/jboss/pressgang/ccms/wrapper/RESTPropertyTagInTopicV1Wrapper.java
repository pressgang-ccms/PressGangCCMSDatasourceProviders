package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTPropertyTagInTopicV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagInTopicWrapper,
        RESTAssignedPropertyTagV1> implements PropertyTagInTopicWrapper {
    private final RESTBaseTopicV1<?, ?, ?> parent;

    protected RESTPropertyTagInTopicV1Wrapper(final RESTProviderFactory providerFactory, final RESTAssignedPropertyTagV1 propertyTag,
            boolean isRevision, final RESTBaseTopicV1<?, ?, ?> parent) {
        super(providerFactory, propertyTag, isRevision, parent);
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
    public RESTPropertyTagInTopicV1Wrapper clone(boolean deepCopy) {
        return new RESTPropertyTagInTopicV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent);
    }

    @Override
    public CollectionWrapper<PropertyTagInTopicWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTAssignedPropertyTagV1.class, true, parent,
                PropertyTagInTopicWrapper.class);
    }
}
