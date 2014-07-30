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

package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityUpdateCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTUpdateCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.elements.base.RESTBaseElementV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;

public abstract class RESTUpdateableCollectionWrapper<T extends BaseWrapper<T>, U extends RESTBaseElementV1<U>,
        V extends RESTUpdateCollectionV1<U, ?>> extends RESTCollectionWrapper<T, U, V> implements UpdateableCollectionWrapper<T> {

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final Collection<String> entityIgnoreMethods) {
        super(providerFactory, collection, isRevisionCollection, entityIgnoreMethods);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final Class<T> wrapperClass) {
        super(providerFactory, collection, isRevisionCollection, wrapperClass);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final Class<T> wrapperClass, final Collection<String> entityIgnoreMethods) {
        super(providerFactory, collection, isRevisionCollection, wrapperClass, entityIgnoreMethods);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent, final Collection<String> entityIgnoreMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, entityIgnoreMethods);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent, final Class<T> wrapperClass) {
        super(providerFactory, collection, isRevisionCollection, parent, wrapperClass);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent, final Class<T> wrapperClass, final Collection<String> entityIgnoreMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, wrapperClass, entityIgnoreMethods);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addUpdateItem(T entity) {
        getCollection().addUpdateItem(getEntity(entity));
        getEntities().put(entity, RESTBaseEntityUpdateCollectionItemV1.UPDATE_STATE);
    }

    @Override
    public List<T> getUpdateItems() {
        final List<T> updateItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getEntities().entrySet()) {
            if (RESTBaseEntityUpdateCollectionItemV1.UPDATE_STATE.equals(entity.getValue())) {
                updateItems.add(entity.getKey());
            }
        }

        return updateItems;
    }
}
