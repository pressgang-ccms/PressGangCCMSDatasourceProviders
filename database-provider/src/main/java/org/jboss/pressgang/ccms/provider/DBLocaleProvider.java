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

package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import org.jboss.pressgang.ccms.model.Locale;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.LocaleWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBLocaleProvider extends DBDataProvider implements LocaleProvider {
    protected DBLocaleProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public LocaleWrapper getLocale(int id) {
        return getWrapperFactory().create(getEntity(Locale.class, id), false);
    }

    @Override
    public LocaleWrapper getLocale(int id, Integer revision) {
        if (revision == null) {
            return getLocale(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(Locale.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<LocaleWrapper> getLocales() {
        // Create the select all query
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Locale> criteriaQuery = criteriaBuilder.createQuery(Locale.class);
        criteriaQuery.from(Locale.class);

        // Execute the query
        final List<Locale> locales = executeQuery(criteriaQuery);
        return getWrapperFactory().createCollection(locales, Locale.class, false, LocaleWrapper.class);
    }
}
