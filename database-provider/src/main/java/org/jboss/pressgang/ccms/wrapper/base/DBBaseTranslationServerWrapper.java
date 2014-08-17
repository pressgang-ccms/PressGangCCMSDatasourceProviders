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

package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.model.TranslationServer;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;

public abstract class DBBaseTranslationServerWrapper<T extends BaseTranslationServerWrapper<T>> extends
        DBBaseEntityWrapper<T, TranslationServer> implements BaseTranslationServerWrapper<T> {
    private final TranslationServer translationServer;

    public DBBaseTranslationServerWrapper(final DBProviderFactory providerFactory, final TranslationServer translationServer) {
        super(providerFactory, TranslationServer.class);
        this.translationServer = translationServer;
    }

    @Override
    protected TranslationServer getEntity() {
        return translationServer;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setId(id);
    }

    @Override
    public String getName() {
        return getEntity().getName();
    }

    @Override
    public void setName(String name) {
        getEntity().setName(name);
    }

    @Override
    public String getUrl() {
        return getEntity().getUrl();
    }

    @Override
    public void setUrl(String url) {
        getEntity().setUrl(url);
    }
}
