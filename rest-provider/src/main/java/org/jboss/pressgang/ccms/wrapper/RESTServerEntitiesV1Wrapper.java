package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTServerUndefinedEntityCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTServerEntitiesV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTServerUndefinedEntityV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTServerEntitiesV1Wrapper extends RESTBaseWrapper<ServerEntitiesWrapper, RESTServerEntitiesV1> implements ServerEntitiesWrapper {
    protected RESTServerEntitiesV1Wrapper(final RESTProviderFactory providerFactory, final RESTServerEntitiesV1 entity) {
        super(providerFactory, entity);
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
        return getEntity().getXmlFormattingStringConstantId();
    }

    @Override
    public Integer getDocbookElementsStringConstantId() {
        return getEntity().getDocbookElementsStringConstantId();
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
                getEntity().getUndefinedEntities(), RESTServerUndefinedEntityV1.class, false);
        return (UpdateableCollectionWrapper<ServerUndefinedEntityWrapper>) collection;
    }

    @Override
    public void setUndefinedEntities(UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> undefinedEntities) {
        getEntity().explicitSetUndefinedEntities(
                undefinedEntities == null ? null : (RESTServerUndefinedEntityCollectionV1) undefinedEntities.unwrap());
    }
}
