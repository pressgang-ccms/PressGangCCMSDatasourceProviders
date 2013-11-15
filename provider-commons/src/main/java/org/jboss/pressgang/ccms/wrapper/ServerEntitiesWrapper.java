package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ServerEntitiesWrapper extends BaseWrapper<ServerEntitiesWrapper> {
    Integer getAbstractTagId();
    Integer getAuthorGroupTagId();
    Integer getContentSpecTagId();
    Integer getLegalNoticeTagId();
    Integer getReviewTagId();
    Integer getRevisionHistoryTagId();
    Integer getFixedUrlPropertyTagId();
    Integer getOriginalFileNamePropertyTagId();
    Integer getTagStylePropertyTagId();
    Integer getTypeCategoryId();
    Integer getWriterCategoryId();
    Integer getRocBookDTDBlobConstantId();
    Integer getXmlFormattingStringConstantId();
    Integer getDocbookElementsStringConstantId();
    Integer getTopicTemplateStringConstantId();
    Integer getContentSpecTemplateStringConstantId();
    Integer getUnknownUserId();
    UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> getUndefinedEntities();
    void setUndefinedEntities(UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> undefinedEntities);
}
