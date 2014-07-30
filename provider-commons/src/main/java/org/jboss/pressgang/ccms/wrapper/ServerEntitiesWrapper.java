/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ServerEntitiesWrapper extends BaseWrapper<ServerEntitiesWrapper> {
    Integer getAbstractTagId();
    Integer getAuthorGroupTagId();
    Integer getContentSpecTagId();
    Integer getFrozenTagId();
    Integer getInfoTagId();
    Integer getInternalOnlyTagId();
    Integer getLegalNoticeTagId();
    Integer getObsoleteTagId();
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
    Integer getRocBook45DTDBlobConstantId();
    Integer getDocBook50RNGBlobConstantId();

    Integer getXmlFormattingStringConstantId();
    Integer getDocBookElementsStringConstantId();

    Integer getTopicTemplateId();
    Integer getDocBook45AbstractTopicTemplateId();
    Integer getDocBook45AuthorGroupTopicTemplateId();
    Integer getDocBook45InfoTopicTemplateId();
    Integer getDocBook45LegalNoticeTopicTemplateId();
    Integer getDocBook45RevisionHistoryTopicTemplateId();
    Integer getDocBook50AbstractTopicTemplateId();
    Integer getDocBook50AuthorGroupTopicTemplateId();
    Integer getDocBook50InfoTopicTemplateId();
    Integer getDocBook50LegalNoticeTopicTemplateId();
    Integer getDocBook50RevisionHistoryTopicTemplateId();
    Integer getContentSpecTemplateId();

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
