package org.jboss.pressgang.ccms.contentspec.wrapper;

import java.util.Date;
import java.util.List;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.model.exceptions.CustomConstraintViolationException;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBContentSpecWrapper extends DBBaseWrapper<ContentSpecWrapper> implements ContentSpecWrapper {
    private final ContentSpec contentSpec;

    public DBContentSpecWrapper(final DBProviderFactory providerFactory, final ContentSpec contentSpec, boolean isRevision) {
        super(providerFactory, isRevision);
        this.contentSpec = contentSpec;
    }

    protected ContentSpec getContentSpec() {
        return contentSpec;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getContentSpec().getTags(), Tag.class, isRevisionEntity());
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

        // Add Tags
        for (final TagWrapper addTag : addTags) {
            try {
                getContentSpec().addTag((Tag) addTag.unwrap());
            } catch (CustomConstraintViolationException e) {
                // TODO
            }
        }

        // Remove Tags
        for (final TagWrapper removeTag : removeTags) {
            getContentSpec().removeTag((Tag) removeTag.unwrap());
        }
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> getChildren() {
        return getWrapperFactory().createCollection(getContentSpec().getTopCSNodes(), CSNode.class, isRevisionEntity());
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
            getContentSpec().addChild((CSNode) addNode.unwrap());
        }

        // Remove Nodes
        for (final CSNodeWrapper removeNode : removeNodes) {
            getContentSpec().removeChild((CSNode) removeNode.unwrap());
        }
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInContentSpecWrapper> collection = getWrapperFactory().createCollection(
                getContentSpec().getContentSpecToPropertyTags(), ContentSpecToPropertyTag.class, isRevisionEntity());
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
        //final List<PropertyTagInContentSpecWrapper> updateMetaDatas = properties.getUpdateItems();

        // Add Properties
        for (final PropertyTagInContentSpecWrapper addProperty : addProperties) {
            getContentSpec().addPropertyTag((ContentSpecToPropertyTag) addProperty.unwrap());
        }

        // Remove Properties
        for (final PropertyTagInContentSpecWrapper removeProperty : removeProperties) {
            getContentSpec().removePropertyTag((ContentSpecToPropertyTag) removeProperty.unwrap());
        }
    }

    @Override
    public String getTitle() {
        final CSNode titleNode = getContentSpec().getContentSpecTitle();
        return titleNode == null ? null : titleNode.getAdditionalText();
    }

    @Override
    public String getProduct() {
        final CSNode productNode = getContentSpec().getContentSpecProduct();
        return productNode == null ? null : productNode.getAdditionalText();
    }

    @Override
    public String getVersion() {
        final CSNode versionNode = getContentSpec().getContentSpecVersion();
        return versionNode == null ? null : versionNode.getAdditionalText();
    }

    @Override
    public String getLocale() {
        return getContentSpec().getLocale();
    }

    @Override
    public void setLocale(String locale) {
        getContentSpec().setLocale(locale);
    }

    @Override
    public Integer getType() {
        return getContentSpec().getContentSpecType();
    }

    @Override
    public void setType(Integer typeId) {
        getContentSpec().setContentSpecType(typeId);
    }

    @Override
    public String getCondition() {
        return getContentSpec().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getContentSpec().setCondition(condition);
    }
    
    @Override
    public Date getLastModified() {
        return EnversUtilities.getFixedLastModifiedDate(getEntityManager(), getContentSpec());
    }

    @Override
    public PropertyTagInContentSpecWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getContentSpec().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public CSNodeWrapper getMetaData(String metaDataTitle) {
        return getWrapperFactory().create(getContentSpec().getMetaData(metaDataTitle), isRevisionEntity());
    }

    @Override
    public Integer getId() {
        return getContentSpec().getId();
    }

    @Override
    public void setId(Integer id) {
        getContentSpec().setContentSpecId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getContentSpec().getRevision();
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getContentSpec()),
                ContentSpec.class, true);
    }

    @Override
    public ContentSpec unwrap() {
        return contentSpec;
    }
}
