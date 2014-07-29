/*
  Copyright 2011-2014 Red Hat

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
import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedCSNodeStringProvider extends DBDataProvider implements TranslatedCSNodeStringProvider {
    protected DBTranslatedCSNodeStringProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStringRevisions(int id, Integer revision) {
        final List<TranslatedCSNodeString> revisions = getRevisionList(TranslatedCSNodeString.class, id);
        return getWrapperFactory().createCollection(revisions, TranslatedCSNodeString.class, revision != null);
    }

    @Override
    public TranslatedCSNodeStringWrapper newTranslatedCSNodeString(TranslatedCSNodeWrapper parent) {
        return getWrapperFactory().create(new TranslatedCSNodeString(), false);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> newTranslatedCSNodeStringCollection(TranslatedCSNodeWrapper parent) {
        final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<TranslatedCSNodeString>(), TranslatedCSNodeString.class, false);

        return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
    }
}
