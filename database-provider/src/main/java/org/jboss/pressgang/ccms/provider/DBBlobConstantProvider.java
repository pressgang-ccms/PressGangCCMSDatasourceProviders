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
import java.util.List;

import org.jboss.pressgang.ccms.model.BlobConstants;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBBlobConstantProvider extends DBDataProvider implements BlobConstantProvider {
    protected DBBlobConstantProvider(EntityManager entityManager, DBProviderFactory providerFactory, final List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public BlobConstantWrapper getBlobConstant(int id) {
        return getWrapperFactory().create(getEntity(BlobConstants.class, id), false);
    }

    @Override
    public BlobConstantWrapper getBlobConstant(int id, Integer revision) {
        if (revision == null) {
            return getBlobConstant(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(BlobConstants.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<BlobConstantWrapper> getBlobConstantRevisions(int id, Integer revision) {
        final List<BlobConstants> revisions = getRevisionList(BlobConstants.class, id);
        return getWrapperFactory().createCollection(revisions, BlobConstants.class, revision != null);
    }
}
