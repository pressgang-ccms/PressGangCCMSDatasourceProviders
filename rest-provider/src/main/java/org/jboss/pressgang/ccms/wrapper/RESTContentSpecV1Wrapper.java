package org.jboss.pressgang.ccms.wrapper;

import java.util.Date;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentBaseRESTEntityWithPropertiesV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTContentSpecTypeV1;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTContentSpecV1Wrapper extends RESTBaseWrapper<ContentSpecWrapper, RESTContentSpecV1> implements ContentSpecWrapper {
    private final RESTContentSpecV1 contentSpec;

    protected RESTContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTContentSpecV1 entity, boolean isRevision) {
        super(providerFactory, isRevision);
        contentSpec = RESTEntityProxyFactory.createProxy(providerFactory, entity, isRevision);
    }

    @Override
    protected RESTContentSpecV1 getProxyEntity() {
        return contentSpec;
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
    public CollectionWrapper<CSNodeWrapper> getChildren() {
        return getWrapperFactory().createCollection(getProxyEntity().getChildren_OTM(), RESTCSNodeV1.class, isRevisionEntity());
    }

    @Override
    public void setChildren(CollectionWrapper<CSNodeWrapper> nodes) {
        getEntity().explicitSetChildren_OTM(nodes == null ? null : (RESTCSNodeCollectionV1) nodes.unwrap());
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
    public String getTitle() {
        final CSNodeWrapper node = getWrapperFactory().create(ComponentContentSpecV1.returnMetaData(getProxyEntity(), "Title"),
                isRevisionEntity(), CSNodeWrapper.class);
        return node == null ? null : node.getAdditionalText();
    }

    @Override
    public String getProduct() {
        final CSNodeWrapper node = getWrapperFactory().create(ComponentContentSpecV1.returnMetaData(getProxyEntity(), "Product"),
                isRevisionEntity(), CSNodeWrapper.class);
        return node == null ? null : node.getAdditionalText();
    }

    @Override
    public String getVersion() {
        final CSNodeWrapper node = getWrapperFactory().create(ComponentContentSpecV1.returnMetaData(getProxyEntity(), "Version"),
                isRevisionEntity(), CSNodeWrapper.class);
        return node == null ? null : node.getAdditionalText();
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
    public void setType(Integer typeId) {
        getEntity().explicitSetType(RESTContentSpecTypeV1.getContentSpecType(typeId));
    }

    @Override
    public String getCondition() {
        return getProxyEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().explicitSetCondition(condition);
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
    public CSNodeWrapper getMetaData(String metaDataTitle) {
        return getWrapperFactory().create(ComponentContentSpecV1.returnMetaData(getProxyEntity(), metaDataTitle), isRevisionEntity(),
                CSNodeWrapper.class);
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTContentSpecV1.class, true);
    }

    @Override
    public ContentSpecWrapper clone(boolean deepCopy) {
        return getWrapperFactory().create(getEntity().clone(deepCopy), isRevisionEntity());
    }
}