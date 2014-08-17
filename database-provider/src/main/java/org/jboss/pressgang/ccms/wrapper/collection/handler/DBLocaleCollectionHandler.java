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

package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.Locale;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslationDetail;

public class DBLocaleCollectionHandler implements DBCollectionHandler<Locale> {
    private CSTranslationDetail parent;

    public DBLocaleCollectionHandler(final CSTranslationDetail parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(Collection<Locale> items, final Locale entity) {
        parent.addLocale(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<Locale> items, final Locale entity) {
        parent.removeLocale(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
