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
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBServerUndefinedEntityCollectionHandler;

public class DBServerEntitiesWrapper extends DBBaseWrapper<ServerEntitiesWrapper,
        EntitiesConfig> implements ServerEntitiesWrapper {
    private final DBServerUndefinedEntityCollectionHandler undefinedEntityCollectionHandler;
    private final EntitiesConfig entitiesConfig;

    protected DBServerEntitiesWrapper(DBProviderFactory providerFactory, EntitiesConfig entitiesConfig) {
        super(providerFactory);
        this.entitiesConfig = entitiesConfig;
        undefinedEntityCollectionHandler = new DBServerUndefinedEntityCollectionHandler(entitiesConfig);
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
    public Integer getFrozenTagId() {
        return getEntity().getFrozenTagId();
    }

    @Override
    public Integer getInternalOnlyTagId() {
        return getEntity().getInternalOnlyTagId();
    }

    @Override
    public Integer getLegalNoticeTagId() {
        return getEntity().getLegalNoticeTagId();
    }

    @Override
    public Integer getObsoleteTagId() {
        return getEntity().getObsoleteTagId();
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
    public Integer getTaskTagId() {
        return getEntity().getTaskTagId();
    }

    @Override
    public Integer getAddedByPropertyTagId() {
        return getEntity().getAddedByPropertyTagId();
    }

    @Override
    public Integer getBugLinksLastValidatedPropertyTagId() {
        return getEntity().getBugLinksLastValidatedPropertyTagId();
    }

    @Override
    public Integer getCspIdPropertyTagId() {
        return getEntity().getCSPIDPropertyTagId();
    }

    @Override
    public Integer getEmailPropertyTagId() {
        return getEntity().getEmailPropertyTagId();
    }

    @Override
    public Integer getFirstNamePropertyTagId() {
        return getEntity().getFirstNamePropertyTagId();
    }

    @Override
    public Integer getFixedUrlPropertyTagId() {
        return getEntity().getFixedUrlPropertyTagId();
    }

    @Override
    public Integer getOrganizationPropertyTagId() {
        return getEntity().getOrganizationPropertyTagId();
    }

    @Override
    public Integer getOrganizationDivisionPropertyTagId() {
        return getEntity().getOrganizationDivisionPropertyTagId();
    }

    @Override
    public Integer getOriginalFileNamePropertyTagId() {
        return getEntity().getOriginalFileNamePropertyTagId();
    }

    @Override
    public Integer getPressGangWebsitePropertyTagId() {
        return getEntity().getPressGangWebsitePropertyTagId();
    }

    @Override
    public Integer getReadOnlyPropertyTagId() {
        return getEntity().getReadOnlyPropertyTagId();
    }

    @Override
    public Integer getSurnamePropertyTagId() {
        return getEntity().getSurnamePropertyTagId();
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
    public Integer getFailPenguinBlobConstantId() {
        return getEntity().getFailPenguinBlobConstantId();
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
    public Integer getDocBookElementsStringConstantId() {
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
    public Integer getArticleStringConstantId() {
        return getEntity().getArticleStringConstantId();
    }

    @Override
    public Integer getArticleInfoStringConstantId() {
        return getEntity().getArticleInfoStringConstantId();
    }

    @Override
    public Integer getAuthorGroupStringConstantId() {
        return getEntity().getAuthorGroupStringConstantId();
    }

    @Override
    public Integer getBookStringConstantId() {
        return getEntity().getBookStringConstantId();
    }

    @Override
    public Integer getBookInfoStringConstantId() {
        return getEntity().getBookInfoStringConstantId();
    }

    @Override
    public Integer getPOMStringConstantId() {
        return getEntity().getPOMStringConstantId();
    }

    @Override
    public Integer getPrefaceStringConstantId() {
        return getEntity().getPrefaceStringConstantId();
    }

    @Override
    public Integer getPublicanCfgStringConstantId() {
        return getEntity().getPublicanCfgStringConstantId();
    }

    @Override
    public Integer getRevisionHistoryStringConstantId() {
        return getEntity().getRevisionHistoryStringConstantId();
    }

    @Override
    public Integer getEmptyTopicStringConstantId() {
        return getEntity().getEmptyTopicStringConstantId();
    }

    @Override
    public Integer getInvalidInjectionStringConstantId() {
        return getEntity().getInvalidInjectionStringConstantId();
    }

    @Override
    public Integer getInvalidTopicStringConstantId() {
        return getEntity().getInvalidTopicStringConstantId();
    }

    @Override
    public Integer getUnknownUserId() {
        return getEntity().getUnknownUserId();
    }

    @Override
    public UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> getUndefinedEntities() {
        final CollectionWrapper<ServerUndefinedEntityWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getUndefinedEntities(), UndefinedEntity.class, false, ServerUndefinedEntityWrapper.class,
                undefinedEntityCollectionHandler);
        return (UpdateableCollectionWrapper<ServerUndefinedEntityWrapper>) collection;
    }

    @Override
    public void setUndefinedEntities(UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> undefinedEntities) {
        if (undefinedEntities == null) return;
        final DBServerUndefinedEntityCollectionWrapper dbEntities = (DBServerUndefinedEntityCollectionWrapper) undefinedEntities;
        dbEntities.setHandler(undefinedEntityCollectionHandler);

        // Add new undefined entities and skip any existing entities
        final Set<UndefinedEntity> currentUndefinedEntities = new HashSet<UndefinedEntity>(getEntity().getUndefinedEntities());
        final Collection<UndefinedEntity> newTags = dbEntities.unwrap();
        for (final UndefinedEntity entity : newTags) {
            if (currentUndefinedEntities.contains(entity)) {
                currentUndefinedEntities.remove(entity);
                continue;
            } else {
                try {
                    getEntity().addUndefinedEntity(entity.getKey(), entity.getValue());
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
