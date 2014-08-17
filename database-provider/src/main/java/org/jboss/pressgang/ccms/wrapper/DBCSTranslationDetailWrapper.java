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
import java.util.List;

import org.jboss.pressgang.ccms.model.Locale;
import org.jboss.pressgang.ccms.model.TranslationServer;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslationDetail;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLocaleCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBLocaleCollectionHandler;

public class DBCSTranslationDetailWrapper extends DBBaseEntityWrapper<CSTranslationDetailWrapper, CSTranslationDetail> implements CSTranslationDetailWrapper {
    private final DBLocaleCollectionHandler localeCollectionHandler;
    private final CSTranslationDetail entity;

    public DBCSTranslationDetailWrapper(final DBProviderFactory providerFactory, final CSTranslationDetail entity) {
        super(providerFactory, CSTranslationDetail.class);
        this.entity = entity;
        localeCollectionHandler = new DBLocaleCollectionHandler(entity);
    }

    @Override
    protected CSTranslationDetail getEntity() {
        return entity;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setCSTranslationDetailId(id);
    }

    @Override
    public Boolean getEnabled() {
        return getEntity().getEnabled();
    }

    @Override
    public void setEnabled(Boolean enabled) {
        getEntity().setEnabled(enabled);
    }

    @Override
    public TranslationServerWrapper getTranslationServer() {
        return getWrapperFactory().create(getEntity().getTranslationServer(), false, TranslationServerWrapper.class);
    }

    @Override
    public void setTranslationServer(TranslationServerWrapper translationServer) {
        getEntity().setTranslationServer(translationServer == null ? null : (TranslationServer) translationServer.unwrap());
    }

    @Override
    public CollectionWrapper<LocaleWrapper> getLocales() {
        return getWrapperFactory().createCollection(getEntity().getLocales(), Locale.class, false, localeCollectionHandler);
    }

    @Override
    public void setLocales(CollectionWrapper<LocaleWrapper> locales) {
        if (locales == null) return;
        final DBLocaleCollectionWrapper dbNodes = (DBLocaleCollectionWrapper) locales;
        dbNodes.setHandler(localeCollectionHandler);

        // Since locales in a cs translation detail are generated from a set and not cached, there is no way to see if this collection is
        // the same as the collection passed. So just process all the locales anyway.

        // Add new locales and skip any existing locales
        final List<Locale> currentLocales = getEntity().getLocales();
        final Collection<Locale> newLocales = dbNodes.unwrap();
        for (final Locale locale : newLocales) {
            if (currentLocales.contains(locale)) {
                currentLocales.remove(locale);
                continue;
            } else {
                getEntity().addLocale(locale);
            }
        }

        // Remove locales that should no longer exist in the collection
        for (final Locale removeLocale : currentLocales) {
            getEntity().removeLocale(removeLocale);
        }
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
