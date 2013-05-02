package org.jboss.pressgang.ccms.wrapper;

import java.util.Date;
import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBContentSpecWrapper extends DBBaseWrapper<ContentSpecWrapper, ContentSpec> implements ContentSpecWrapper {
    private final ContentSpec contentSpec;

    public DBContentSpecWrapper(final DBProviderFactory providerFactory, final ContentSpec contentSpec, boolean isRevision) {
        super(providerFactory, isRevision, ContentSpec.class);
        this.contentSpec = contentSpec;
    }

    @Override
    protected ContentSpec getEntity() {
        return contentSpec;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getEntity().getTags(), Tag.class, isRevisionEntity());
    }

    @Override
    public void setTags(CollectionWrapper<TagWrapper> tags) {
        if (tags == null) return;

        final List<TagWrapper> addTags = tags.getAddItems();
        final List<TagWrapper> removeTags = tags.getRemoveItems();
        /*
         * There is no need to do update tags as when the original entities are alter they will automatically be updated when using
         * database entities.
         */
        //final List<TagWrapper> updateTags = tags.getUpdateItems();

        // Remove Tags
        for (final TagWrapper removeTag : removeTags) {
            getEntity().removeTag((Tag) removeTag.unwrap());
        }

        // Add Tags
        for (final TagWrapper addTag : addTags) {
            getEntity().addTag((Tag) addTag.unwrap());
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getChildren() {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getTopCSNodes(),
                CSNode.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    @Override
    public void setChildren(CollectionWrapper<CSNodeWrapper> nodes) {
        if (nodes == null) return;

        final List<CSNodeWrapper> addNodes = nodes.getAddItems();
        final List<CSNodeWrapper> removeNodes = nodes.getRemoveItems();
        /*
         * There is no need to do update nodes as when the original entities are altered they will automatically be updated when using
         * database entities.
         */
        //final List<CSNodeWrapper> updateMetaDatas = nodes.getUpdateItems();

        // Add Nodes
        for (final CSNodeWrapper addNode : addNodes) {
            getEntity().addChild((CSNode) addNode.unwrap());
        }

        // Remove Nodes
        for (final CSNodeWrapper removeNode : removeNodes) {
            getEntity().removeChild((CSNode) removeNode.unwrap());
        }
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInContentSpecWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getContentSpecToPropertyTags(), ContentSpecToPropertyTag.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) collection;
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> properties) {
        if (properties == null) return;

        final List<PropertyTagInContentSpecWrapper> addProperties = properties.getAddItems();
        final List<PropertyTagInContentSpecWrapper> removeProperties = properties.getRemoveItems();
        /*
         * There is no need to do update properties as when the original entities are altered they will automatically be updated when using
         * database entities.
         */
        //final List<PropertyTagInContentSpecWrapper> updateProperties = properties.getUpdateItems();

        // Add Properties
        for (final PropertyTagInContentSpecWrapper addProperty : addProperties) {
            getEntity().addPropertyTag((ContentSpecToPropertyTag) addProperty.unwrap());
        }

        // Remove Properties
        for (final PropertyTagInContentSpecWrapper removeProperty : removeProperties) {
            getEntity().removePropertyTag((ContentSpecToPropertyTag) removeProperty.unwrap());
        }
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecs() {
        return getWrapperFactory().createCollection(getEntity().getTranslatedContentSpecs(getEntityManager(), getRevision()),
                TranslatedContentSpec.class, isRevisionEntity());
    }

    @Override
    public String getTitle() {
        final CSNode titleNode = getEntity().getContentSpecTitle();
        return titleNode == null ? null : titleNode.getAdditionalText();
    }

    @Override
    public String getProduct() {
        final CSNode productNode = getEntity().getContentSpecProduct();
        return productNode == null ? null : productNode.getAdditionalText();
    }

    @Override
    public String getVersion() {
        final CSNode versionNode = getEntity().getContentSpecVersion();
        return versionNode == null ? null : versionNode.getAdditionalText();
    }

    @Override
    public String getLocale() {
        return getEntity().getLocale();
    }

    @Override
    public void setLocale(String locale) {
        getEntity().setLocale(locale);
    }

    @Override
    public Integer getType() {
        return getEntity().getContentSpecType();
    }

    @Override
    public void setType(Integer typeId) {
        getEntity().setContentSpecType(typeId);
    }

    @Override
    public String getCondition() {
        return getEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().setCondition(condition);
    }

    @Override
    public Date getLastModified() {
        return EnversUtilities.getFixedLastModifiedDate(getEntityManager(), getEntity());
    }

    @Override
    public PropertyTagInContentSpecWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getEntity().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public CSNodeWrapper getMetaData(String metaDataTitle) {
        return getWrapperFactory().create(getEntity().getMetaData(metaDataTitle), isRevisionEntity());
    }

    @Override
    public void setId(Integer id) {
        getEntity().setContentSpecId(id);
    }

    @Override
    public ContentSpec unwrap() {
        return contentSpec;
    }
}
