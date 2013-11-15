package org.jboss.pressgang.ccms.wrapper;

import java.util.Date;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentBaseRESTEntityWithPropertiesV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTContentSpecTypeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTTextContentSpecV1Wrapper extends RESTBaseEntityWrapper<TextContentSpecWrapper,
        RESTTextContentSpecV1> implements TextContentSpecWrapper {

    protected RESTTextContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTTextContentSpecV1 entity,
            boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getProxyEntity().getTags(), RESTTagV1.class, isRevisionEntity());
    }

    @Override
    public void setTags(CollectionWrapper<TagWrapper> tags) {
        getEntity().explicitSetTags(tags == null ? null : (RESTTagCollectionV1) tags.unwrap());
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInContentSpecWrapper> collection = getWrapperFactory().createCollection(
                getProxyEntity().getProperties(), RESTAssignedPropertyTagCollectionV1.class, isRevisionEntity(), getProxyEntity(),
                PropertyTagInContentSpecWrapper.class);
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) collection;
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> properties) {
        getEntity().explicitSetProperties(properties == null ? null : (RESTAssignedPropertyTagCollectionV1) properties.unwrap());
    }

    @Override
    public String getText() {
        return getProxyEntity().getText();
    }

    @Override
    public void setText(String text) {
        getEntity().explicitSetText(text);
    }

    @Override
    public String getErrors() {
        return getProxyEntity().getErrors();
    }

    @Override
    public void setErrors(String errors) {
        getEntity().setErrors(errors);
    }

    @Override
    public String getFailed() {
        return getProxyEntity().getFailedContentSpec();
    }

    @Override
    public void setFailed(String failed) {
        getEntity().setFailedContentSpec(failed);
    }

    @Override
    public String getTitle() {
        return getProxyEntity().getTitle();
    }

    @Override
    public String getProduct() {
        return getProxyEntity().getProduct();
    }

    @Override
    public String getVersion() {
        return getProxyEntity().getVersion();
    }

    @Override
    public String getLocale() {
        return getProxyEntity().getLocale();
    }

    @Override
    public void setLocale(String locale) {
        getEntity().explicitSetLocale(locale);
    }

    @Override
    public Integer getType() {
        return RESTContentSpecTypeV1.getContentSpecTypeId(getProxyEntity().getType());
    }

    @Override
    public Date getLastModified() {
        return getProxyEntity().getLastModified();
    }

    @Override
    public PropertyTagInContentSpecWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(ComponentBaseRESTEntityWithPropertiesV1.returnProperty(getProxyEntity(), propertyId),
                isRevisionEntity(), PropertyTagInContentSpecWrapper.class);
    }

    @Override
    public CollectionWrapper<TextContentSpecWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTextContentSpecV1.class, true);
    }

    @Override
    public TextContentSpecWrapper clone(boolean deepCopy) {
        return getWrapperFactory().create(getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }
}
