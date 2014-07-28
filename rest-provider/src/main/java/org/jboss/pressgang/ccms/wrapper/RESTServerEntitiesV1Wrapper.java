/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTServerUndefinedEntityCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerEntitiesV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
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
    public Integer getFrozenTagId() {
        return getEntity().getFrozenTagId();
    }

    @Override
    public Integer getInfoTagId() {
        return getEntity().getInfoTagId();
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
        return getEntity().getCspIdPropertyTagId();
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
        return getEntity().getOrgPropertyTagId();
    }

    @Override
    public Integer getOrganizationDivisionPropertyTagId() {
        return getEntity().getOrgDivisionPropertyTagId();
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
    public Integer getRocBook45DTDBlobConstantId() {
        return getEntity().getRocBook45DTDBlobConstantId();
    }

    @Override
    public Integer getDocBook50RNGBlobConstantId() {
        return getEntity().getDocBook50RNGBlobConstantId();
    }

    @Override
    public Integer getXmlFormattingStringConstantId() {
        return getEntity().getXmlFormattingStringConstantId();
    }

    @Override
    public Integer getDocBookElementsStringConstantId() {
        return getEntity().getDocBookElementsStringConstantId();
    }

    @Override
    public Integer getTopicTemplateId() {
        return getEntity().getTopicTemplateId();
    }

    @Override
    public Integer getDocBook45AbstractTopicTemplateId() {
        return getEntity().getDocBook45AbstractTopicTemplateId();
    }

    @Override
    public Integer getDocBook45AuthorGroupTopicTemplateId() {
        return getEntity().getDocBook45AuthorGroupTopicTemplateId();
    }

    @Override
    public Integer getDocBook45InfoTopicTemplateId() {
        return getEntity().getDocBook45InfoTopicTemplateId();
    }

    @Override
    public Integer getDocBook45LegalNoticeTopicTemplateId() {
        return getEntity().getDocBook45LegalNoticeTopicTemplateId();
    }

    @Override
    public Integer getDocBook45RevisionHistoryTopicTemplateId() {
        return getEntity().getDocBook45RevisionHistoryTopicTemplateId();
    }

    @Override
    public Integer getDocBook50AbstractTopicTemplateId() {
        return getEntity().getDocBook50AbstractTopicTemplateId();
    }

    @Override
    public Integer getDocBook50AuthorGroupTopicTemplateId() {
        return getEntity().getDocBook50AuthorGroupTopicTemplateId();
    }

    @Override
    public Integer getDocBook50InfoTopicTemplateId() {
        return getEntity().getDocBook50InfoTopicTemplateId();
    }

    @Override
    public Integer getDocBook50LegalNoticeTopicTemplateId() {
        return getEntity().getDocBook50LegalNoticeTopicTemplateId();
    }

    @Override
    public Integer getDocBook50RevisionHistoryTopicTemplateId() {
        return getEntity().getDocBook50RevisionHistoryTopicTemplateId();
    }

    @Override
    public Integer getContentSpecTemplateId() {
        return getEntity().getContentSpecTemplateId();
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
        return getEntity().getPomStringConstantId();
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
        return (UpdateableCollectionWrapper<ServerUndefinedEntityWrapper>) RESTCollectionWrapperBuilder.<ServerUndefinedEntityWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getEntity().getUndefinedEntities())
                .build();
    }

    @Override
    public void setUndefinedEntities(UpdateableCollectionWrapper<ServerUndefinedEntityWrapper> undefinedEntities) {
        getEntity().explicitSetUndefinedEntities(
                undefinedEntities == null ? null : (RESTServerUndefinedEntityCollectionV1) undefinedEntities.unwrap());
    }
}
