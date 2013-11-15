package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.jboss.pressgang.ccms.model.config.EntitiesConfig;
import org.jboss.pressgang.ccms.model.config.UndefinedEntity;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBServerUndefinedEntityCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBApplicationUndefinedEntityCollectionHandler;

public class DBServerEntitiesWrapper extends DBBaseWrapper<ServerEntitiesWrapper,
        EntitiesConfig> implements ServerEntitiesWrapper {
    private final DBApplicationUndefinedEntityCollectionHandler undefinedEntityCollectionHandler;
    private final EntitiesConfig entitiesConfig;

    protected DBServerEntitiesWrapper(DBProviderFactory providerFactory, EntitiesConfig entitiesConfig) {
        super(providerFactory);
        this.entitiesConfig = entitiesConfig;
        undefinedEntityCollectionHandler = new DBApplicationUndefinedEntityCollectionHandler(entitiesConfig);
    }

    @Override
    protected EntitiesConfig getEntity() {
        return entitiesConfig;
    }

    @Override
    public Integer getAbstractTagId() {
        return getEntity().getAbstractTagId();
    }

    @Override
    public Integer getAuthorGroupTagId() {
        return getEntity().getAuthorGroupTagId();
    }

    @Override
    public Integer getContentSpecTagId() {
        return getEntity().getContentSpecTagId();
    }

    @Override
    public Integer getLegalNoticeTagId() {
        return getEntity().getLegalNoticeTagId();
    }

    @Override
    public Integer getReviewTagId() {
        return getEntity().getReviewTagId();
    }

    @Override
    public Integer getRevisionHistoryTagId() {
        return getEntity().getRevisionHistoryTagId();
    }

    @Override
    public Integer getFixedUrlPropertyTagId() {
        return getEntity().getFixedUrlPropertyTagId();
    }

    @Override
    public Integer getOriginalFileNamePropertyTagId() {
        return getEntity().getOriginalFileNamePropertyTagId();
    }

    @Override
    public Integer getTagStylePropertyTagId() {
        return getEntity().getTagStylePropertyTagId();
    }

    @Override
    public Integer getTypeCategoryId() {
        return getEntity().getTypeCategoryId();
    }

    @Override
    public Integer getWriterCategoryId() {
        return getEntity().getWriterCategoryId();
    }

    @Override
    public Integer getRocBookDTDBlobConstantId() {
        return getEntity().getRocBookDTDBlobConstantId();
    }

    @Override
    public Integer getXmlFormattingStringConstantId() {
        return getEntity().getXMLFormattingElementsStringConstantId();
    }

    @Override
    public Integer getDocbookElementsStringConstantId() {
        return getEntity().getDocBookElementsStringConstantId();
    }

    @Override
    public Integer getTopicTemplateStringConstantId() {
        return getEntity().getTopicTemplateStringConstantId();
    }

    @Override
    public Integer getContentSpecTemplateStringConstantId() {
        return getEntity().getContentSpecTemplateStringConstantId();
    }

    @Override
    public Integer getUnknownUserId() {
        return getEntity().getUnknownUserId();
    }

    @Override
    public UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> getUndefinedEntities() {
        final CollectionWrapper<ServerUndefinedEntityWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getUndefinedProperties(), UndefinedEntity.class, false, ServerUndefinedEntityWrapper.class,
                undefinedEntityCollectionHandler);
        return (UpdateableCollectionWrapper<ServerUndefinedEntityWrapper>) collection;
    }

    @Override
    public void setUndefinedEntities(UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> undefinedEntities) {
        if (undefinedEntities == null) return;
        final DBServerUndefinedEntityCollectionWrapper dbEntities = (DBServerUndefinedEntityCollectionWrapper) undefinedEntities;
        dbEntities.setHandler(undefinedEntityCollectionHandler);

        // Add new undefined entities and skip any existing entities
        final Set<UndefinedEntity> currentUndefinedEntities = new HashSet<UndefinedEntity>(getEntity().getUndefinedProperties());
        final Collection<UndefinedEntity> newTags = dbEntities.unwrap();
        for (final UndefinedEntity entity : newTags) {
            if (currentUndefinedEntities.contains(entity)) {
                currentUndefinedEntities.remove(entity);
                continue;
            } else {
                try {
                    getEntity().addUndefinedProperty(entity.getKey(), entity.getValue());
                } catch (ConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Remove entities that should no longer exist in the collection
        for (final UndefinedEntity removeEntity : currentUndefinedEntities) {
            getEntity().removeProperty(removeEntity.getKey());
        }
    }
}
