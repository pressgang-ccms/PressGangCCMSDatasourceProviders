package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;

public class RESTPropertyTagInContentSpecV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagInContentSpecWrapper,
        RESTAssignedPropertyTagV1> implements PropertyTagInContentSpecWrapper {

    protected RESTPropertyTagInContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTAssignedPropertyTagV1 propertyTag,
            boolean isRevision, final RESTContentSpecV1 parent, boolean isNewEntity) {
        super(providerFactory, propertyTag, isRevision, parent, isNewEntity);
    }

    protected RESTPropertyTagInContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTAssignedPropertyTagV1 propertyTag,
            boolean isRevision, final RESTContentSpecV1 parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, propertyTag, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTContentSpecV1 getParentEntity() {
        return (RESTContentSpecV1) super.getParentEntity();
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
        return new RESTPropertyTagInContentSpecV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(),
                getParentEntity(), isNewEntity());
    }

    @Override
    public CollectionWrapper<PropertyTagInContentSpecWrapper> getRevisions() {
        return RESTCollectionWrapperBuilder.<PropertyTagInContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getRevisions())
                .isRevisionCollection()
                .parent(getParentEntity())
                .entityWrapperInterface(PropertyTagInContentSpecWrapper.class)
                .build();
    }
}
