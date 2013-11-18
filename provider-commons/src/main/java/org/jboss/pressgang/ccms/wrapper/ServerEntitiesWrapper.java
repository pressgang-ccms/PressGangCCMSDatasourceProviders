package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ServerEntitiesWrapper extends BaseWrapper<ServerEntitiesWrapper> {
    Integer getAbstractTagId();
    Integer getAuthorGroupTagId();
    Integer getContentSpecTagId();
    Integer getInternalOnlyTagId();
    Integer getLegalNoticeTagId();
    Integer getReviewTagId();
    Integer getRevisionHistoryTagId();
    Integer getTaskTagId();

    Integer getAddedByPropertyTagId();
    Integer getBugLinksLastValidatedPropertyTagId();
    Integer getCspIdPropertyTagId();
    Integer getEmailPropertyTagId();
    Integer getFirstNamePropertyTagId();
    Integer getFixedUrlPropertyTagId();
    Integer getOrganizationPropertyTagId();
    Integer getOrganizationDivisionPropertyTagId();
    Integer getOriginalFileNamePropertyTagId();
    Integer getPressGangWebsitePropertyTagId();
    Integer getReadOnlyPropertyTagId();
    Integer getSurnamePropertyTagId();
    Integer getTagStylePropertyTagId();

    Integer getTypeCategoryId();
    Integer getWriterCategoryId();

    Integer getFailPenguinBlobConstantId();
    Integer getRocBookDTDBlobConstantId();

    Integer getXmlFormattingStringConstantId();
    Integer getDocBookElementsStringConstantId();
    Integer getTopicTemplateStringConstantId();
    Integer getContentSpecTemplateStringConstantId();
    Integer getArticleStringConstantId();
    Integer getArticleInfoStringConstantId();
    Integer getAuthorGroupStringConstantId();
    Integer getBookStringConstantId();
    Integer getBookInfoStringConstantId();
    Integer getPOMStringConstantId();
    Integer getPrefaceStringConstantId();
    Integer getPublicanCfgStringConstantId();
    Integer getRevisionHistoryStringConstantId();
    Integer getEmptyTopicStringConstantId();
    Integer getInvalidInjectionStringConstantId();
    Integer getInvalidTopicStringConstantId();

    Integer getUnknownUserId();

    UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> getUndefinedEntities();
    void setUndefinedEntities(UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> undefinedEntities);
}
