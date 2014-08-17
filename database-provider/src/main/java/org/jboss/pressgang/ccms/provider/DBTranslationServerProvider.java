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

import org.jboss.pressgang.ccms.model.TranslationServer;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.TranslationServerExtendedWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslationServerWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBTranslationServerProvider extends DBDataProvider {
    protected DBTranslationServerProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    public TranslationServerWrapper getTranslationServer(int id) {
        return getWrapperFactory().create(getEntity(TranslationServer.class, id), false);
    }

    public CollectionWrapper<TranslationServerWrapper> getTranslationServers() {
        // Create the select all query
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<TranslationServer> criteriaQuery = criteriaBuilder.createQuery(TranslationServer.class);
        criteriaQuery.from(TranslationServer.class);

        // Execute the query
        final List<TranslationServer> locales = executeQuery(criteriaQuery);
        return getWrapperFactory().createCollection(locales, TranslationServer.class, false, TranslationServerWrapper.class);
    }

    public CollectionWrapper<TranslationServerExtendedWrapper> getTranslationServersExtended() {
        // Create the select all query
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<TranslationServer> criteriaQuery = criteriaBuilder.createQuery(TranslationServer.class);
        criteriaQuery.from(TranslationServer.class);

        // Execute the query
        final List<TranslationServer> locales = executeQuery(criteriaQuery);
        return getWrapperFactory().createCollection(locales, TranslationServer.class, false, TranslationServerExtendedWrapper.class);
    }
}
