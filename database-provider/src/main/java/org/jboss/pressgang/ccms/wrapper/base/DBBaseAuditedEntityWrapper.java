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

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Map;

import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public abstract class DBBaseAuditedEntityWrapper<T extends AuditedEntityWrapper<T>, U extends AuditedEntity> extends DBBaseEntityWrapper<T,
        U> implements AuditedEntityWrapper<T> {

    private final boolean isRevision;

    protected DBBaseAuditedEntityWrapper(final DBProviderFactory providerFactory, boolean isRevision, Class<U> clazz) {
        this(providerFactory, isRevision, null, clazz);
    }

    protected DBBaseAuditedEntityWrapper(final DBProviderFactory providerFactory, boolean isRevision, Class<T> wrapperClazz, Class<U> clazz) {
        super(providerFactory, wrapperClazz, clazz);
        this.isRevision = isRevision;
    }

    @Override
    public boolean isRevisionEntity() {
        return isRevision;
    }

    @Override
    public Integer getRevision() {
        return getEntity().getRevision() == null ? EnversUtilities.getLatestRevision(getDatabaseProvider().getEntityManager(),
                getEntity()).intValue() : getEntity().getRevision().intValue();
    }

    @Override
    public CollectionWrapper<T> getRevisions() {
        final Map<Number, U> entities = EnversUtilities.getRevisionEntities(getEntityManager(), getEntity());
        if (wrapperClazz != null) {
            return getWrapperFactory().createCollection(entities.values(), clazz, true, wrapperClazz);
        } else {
            return getWrapperFactory().createCollection(entities.values(), clazz, true);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof DBBaseAuditedEntityWrapper && getEntity() != null) {
            return o == null ? false : getEntity().equals(((DBBaseAuditedEntityWrapper) o).getEntity());
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        if (getEntity() != null) {
            return getEntity().hashCode();
        } else {
            return super.hashCode();
        }
    }
}
