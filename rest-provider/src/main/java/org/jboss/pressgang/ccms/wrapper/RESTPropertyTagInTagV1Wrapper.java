package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;

public class RESTPropertyTagInTagV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagInTagWrapper,
        RESTAssignedPropertyTagV1> implements PropertyTagInTagWrapper {

    protected RESTPropertyTagInTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTAssignedPropertyTagV1 propertyTag,
            boolean isRevision, final RESTBaseTagV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, propertyTag, isRevision, parent, isNewEntity);
    }

    protected RESTPropertyTagInTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTAssignedPropertyTagV1 propertyTag,
            boolean isRevision, final RESTBaseTagV1<?, ?, ?> parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, propertyTag, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTTagV1 getParentEntity() {
        return (RESTTagV1) super.getParentEntity();
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
    public RESTPropertyTagInTagV1Wrapper clone(boolean deepCopy) {
        return new RESTPropertyTagInTagV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), getParentEntity(),
                isNewEntity());
    }

    @Override
    public CollectionWrapper<PropertyTagInTagWrapper> getRevisions() {
        return RESTCollectionWrapperBuilder.<PropertyTagInTagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getRevisions())
                .isRevisionCollection()
                .parent(getParentEntity())
                .entityWrapperInterface(PropertyTagInTagWrapper.class)
                .build();
    }
}
