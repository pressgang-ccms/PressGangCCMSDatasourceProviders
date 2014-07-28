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

package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.StringConstants;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBStringConstantProvider extends DBDataProvider implements StringConstantProvider {
    protected DBStringConstantProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public StringConstantWrapper getStringConstant(int id) {
        return getWrapperFactory().create(getEntity(StringConstants.class, id), false);
    }

    @Override
    public StringConstantWrapper getStringConstant(int id, Integer revision) {
        if (revision == null) {
            return getStringConstant(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(StringConstants.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<StringConstantWrapper> getStringConstantRevisions(int id, Integer revision) {
        final List<StringConstants> revisions = getRevisionList(StringConstants.class, id);
        return getWrapperFactory().createCollection(revisions, StringConstants.class, revision != null);
    }
}
