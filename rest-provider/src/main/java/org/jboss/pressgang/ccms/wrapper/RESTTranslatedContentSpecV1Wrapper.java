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

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class RESTTranslatedContentSpecV1Wrapper extends RESTBaseEntityWrapper<TranslatedContentSpecWrapper,
        RESTTranslatedContentSpecV1> implements TranslatedContentSpecWrapper {

    protected RESTTranslatedContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedContentSpecV1 translatedContentSpec,
            boolean isRevision, boolean isNewEntity) {
        super(providerFactory, translatedContentSpec, isRevision, isNewEntity);
    }

    protected RESTTranslatedContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedContentSpecV1 translatedContentSpec,
            boolean isRevision, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, translatedContentSpec, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public TranslatedContentSpecWrapper clone(boolean deepCopy) {
        return new RESTTranslatedContentSpecV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public Integer getContentSpecId() {
        return getProxyEntity().getContentSpecId();
    }

    @Override
    public void setContentSpecId(Integer id) {
        getEntity().explicitSetContentSpecId(id);
    }

    @Override
    public Integer getContentSpecRevision() {
        return getProxyEntity().getContentSpecRevision();
    }

    @Override
    public void setContentSpecRevision(Integer revision) {
        getEntity().explicitSetContentSpecRevision(revision);
    }

    @Override
    public String getZanataId() {
        return ComponentTranslatedContentSpecV1.returnZanataId(getProxyEntity());
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes() {
        return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) RESTCollectionWrapperBuilder.<TranslatedCSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTranslatedNodes_OTM())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public void setTranslatedNodes(UpdateableCollectionWrapper<TranslatedCSNodeWrapper> translatedNodes) {
        getEntity().explicitSetTranslatedNodes_OTM(
                translatedNodes == null ? null : (RESTTranslatedCSNodeCollectionV1) translatedNodes.unwrap());
    }

    @Override
    public ContentSpecWrapper getContentSpec() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getContentSpec())
                .isRevision()
                .build();
    }

    @Override
    public void setContentSpec(ContentSpecWrapper contentSpec) {
        getEntity().setContentSpec(contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap());
    }

    @Override
    public String getEditorURL(ZanataDetails zanataDetails, String locale) {
        return ComponentTranslatedContentSpecV1.returnEditorURL(getProxyEntity(), zanataDetails, locale);
    }
}
