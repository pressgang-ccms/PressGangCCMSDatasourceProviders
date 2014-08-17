/*
 * Copyright 2011-2014 Red Hat, Inc.
 *
 * This file is part of PressGang CCMS.
 *
 * PressGang CCMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PressGang CCMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with PressGang CCMS. If not, see <http://www.gnu.org/licenses/>.
 */

package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLocaleCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslationServerV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslationDetailV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.base.RESTBaseContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;

public class RESTCSTranslationDetailV1Wrapper extends RESTBaseEntityWrapper<CSTranslationDetailWrapper,
        RESTCSTranslationDetailV1> implements CSTranslationDetailWrapper {

    protected RESTCSTranslationDetailV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSTranslationDetailV1 entity, final
    RESTBaseContentSpecV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, entity, parent, isNewEntity);
    }

    protected RESTCSTranslationDetailV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSTranslationDetailV1 entity, final
    RESTBaseContentSpecV1<?, ?, ?> parent, boolean isNewEntity,
            final Collection<String> expandedMethods) {
        super(providerFactory, entity, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTBaseContentSpecV1<?, ?, ?> getParentEntity() {
        return (RESTBaseContentSpecV1<?, ?, ?>) super.getParentEntity();
    }

    @Override
    public CSTranslationDetailWrapper clone(boolean deepCopy) {
        return new RESTCSTranslationDetailV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), getParentEntity(), isNewEntity());
    }

    @Override
    public Boolean getEnabled() {
        return getEntity().isEnabled();
    }

    @Override
    public void setEnabled(Boolean enabled) {
        getEntity().setEnabled(enabled);
    }

    @Override
    public TranslationServerWrapper getTranslationServer() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getTranslationServer())
                .build();
    }

    @Override
    public void setTranslationServer(TranslationServerWrapper translationServer) {
        getEntity().setTranslationServer(translationServer == null ? null : (RESTTranslationServerV1) translationServer.unwrap());
    }

    @Override
    public CollectionWrapper<LocaleWrapper> getLocales() {
        return RESTCollectionWrapperBuilder.<LocaleWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getLocales())
                .build();
    }

    @Override
    public void setLocales(CollectionWrapper<LocaleWrapper> locales) {
        getEntity().setLocales(locales == null ? null : (RESTLocaleCollectionV1) locales.unwrap());
    }

    @Override
    public String getProject() {
        return getEntity().getProject();
    }

    @Override
    public void setProject(String project) {
        getEntity().setProject(project);
    }

    @Override
    public String getProjectVersion() {
        return getEntity().getProjectVersion();
    }

    @Override
    public void setProjectVersion(String projectVersion) {
        getEntity().setProjectVersion(projectVersion);
    }
}
