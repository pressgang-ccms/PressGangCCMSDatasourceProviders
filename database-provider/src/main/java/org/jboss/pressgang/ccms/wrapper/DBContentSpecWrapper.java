package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBContentSpecToPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCSBookTagCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCSNodeCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBPropertyTagCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBTagCollectionHandler;

public class DBContentSpecWrapper extends DBBaseWrapper<ContentSpecWrapper, ContentSpec> implements ContentSpecWrapper {
    private final DBCSNodeCollectionHandler csNodeCollectionHandler;
    private final DBPropertyTagCollectionHandler<ContentSpecToPropertyTag> propertyCollectionHandler;
    private final DBTagCollectionHandler tagCollectionHandler;
    private final DBCSBookTagCollectionHandler bookTagCollectionHandler;

    private final ContentSpec contentSpec;

    public DBContentSpecWrapper(final DBProviderFactory providerFactory, final ContentSpec contentSpec, boolean isRevision) {
        super(providerFactory, isRevision, ContentSpec.class);
        this.contentSpec = contentSpec;
        csNodeCollectionHandler = new DBCSNodeCollectionHandler(contentSpec);
        propertyCollectionHandler = new DBPropertyTagCollectionHandler<ContentSpecToPropertyTag>(contentSpec);
        tagCollectionHandler = new DBTagCollectionHandler(contentSpec);
        bookTagCollectionHandler = new DBCSBookTagCollectionHandler(contentSpec);
    }

    @Override
    protected ContentSpec getEntity() {
        return contentSpec;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getEntity().getTags(), Tag.class, isRevisionEntity(), tagCollectionHandler);
    }

    @Override
    public void setTags(CollectionWrapper<TagWrapper> tags) {
        if (tags == null) return;
        final DBTagCollectionWrapper dbTags = (DBTagCollectionWrapper) tags;
        dbTags.setHandler(tagCollectionHandler);

        // Since tags in a content spec are generated from a set and not cached, there is no way to see if this collection is the same as
        // the collection passed. So just process all the tags anyway.

        // Add new tags and skip any existing tags
        final List<Tag> currentTags = getEntity().getTags();
        final Collection<Tag> newTags = dbTags.unwrap();
        for (final Tag tag : newTags) {
            if (currentTags.contains(tag)) {
                currentTags.remove(tag);
                continue;
            } else {
                getEntity().addTag(tag);
            }
        }

        // Remove tags that should no longer exist in the collection
        for (final Tag removeTag : currentTags) {
            getEntity().removeTag(removeTag);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getBookTags() {
        return getWrapperFactory().createCollection(getEntity().getBookTags(), Tag.class, isRevisionEntity(), bookTagCollectionHandler);
    }

    @Override
    public void setBookTags(CollectionWrapper<TagWrapper> bookTags) {
        if (bookTags == null) return;
        final DBTagCollectionWrapper dbTags = (DBTagCollectionWrapper) bookTags;
        dbTags.setHandler(bookTagCollectionHandler);

        // Since book tags in a content spec are generated from a set and not cached, there is no way to see if this collection is the same
        // as the collection passed. So just process all the tags anyway.

        // Add new tags and skip any existing tags
        final List<Tag> currentBookTags = getEntity().getBookTags();
        final Collection<Tag> newBookTags = dbTags.unwrap();
        for (final Tag bookTag : newBookTags) {
            if (currentBookTags.contains(bookTag)) {
                currentBookTags.remove(bookTag);
                continue;
            } else {
                getEntity().addBookTag(bookTag);
            }
        }

        // Remove tags that should no longer exist in the collection
        for (final Tag removeBookTag : currentBookTags) {
            getEntity().removeBookTag(removeBookTag);
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getChildren() {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getChildrenList(), CSNode.class,
                isRevisionEntity(), csNodeCollectionHandler);
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    @Override
    public void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes) {
        if (nodes == null) return;
        final DBCSNodeCollectionWrapper dbNodes = (DBCSNodeCollectionWrapper) nodes;
        dbNodes.setHandler(csNodeCollectionHandler);

        // Since children nodes in a content spec are generated from a set and not cached, there is no way to see if this collection is
        // the same as the collection passed. So just process all the nodes anyway.

        // Add new tags and skip any existing tags
        final Set<CSNode> currentChildren = getEntity().getChildren();
        final Collection<CSNode> newChildren = dbNodes.unwrap();
        for (final CSNode child : newChildren) {
            if (currentChildren.contains(child)) {
                currentChildren.remove(child);
                continue;
            } else {
                getEntity().addChild(child);
            }
        }

        // Remove tags that should no longer exist in the collection
        for (final CSNode removeChild : currentChildren) {
            getEntity().removeChild(removeChild);
        }
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInContentSpecWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getContentSpecToPropertyTags(), ContentSpecToPropertyTag.class, isRevisionEntity(), propertyCollectionHandler);
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) collection;
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> properties) {
        if (properties == null) return;
        final DBContentSpecToPropertyTagCollectionWrapper dbProperties = (DBContentSpecToPropertyTagCollectionWrapper) properties;
        dbProperties.setHandler(propertyCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbProperties.unwrap() != getEntity().getPropertyTags()) {
            // Add new property tags and skip any existing tags
            final Set<ContentSpecToPropertyTag> currentProperties = new HashSet<ContentSpecToPropertyTag>(getEntity().getPropertyTags());
            final Collection<ContentSpecToPropertyTag> newProperties = dbProperties.unwrap();
            for (final ContentSpecToPropertyTag property : newProperties) {
                if (currentProperties.contains(property)) {
                    currentProperties.remove(property);
                    continue;
                } else {
                    property.setContentSpec(getEntity());
                    getEntity().addPropertyTag(property);
                }
            }

            // Remove property tags that should no longer exist in the collection
            for (final ContentSpecToPropertyTag removeProperty : currentProperties) {
                getEntity().removePropertyTag(removeProperty);
            }
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

    /**
     *
     */
    private class CSNodeCollectionEventListener implements UpdateableCollectionEventListener<CSNode> {
        @Override
        public void onAddItem(final CSNode entity) {
            getEntity().addChild(entity);
        }

        @Override
        public void onRemoveItem(final CSNode entity) {
            getEntity().removeChild(entity);
        }

        @Override
        public void onUpdateItem(final CSNode entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CSNodeCollectionEventListener;
        }
    }
}
