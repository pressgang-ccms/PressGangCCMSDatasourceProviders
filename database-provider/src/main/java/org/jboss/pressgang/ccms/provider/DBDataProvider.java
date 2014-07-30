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
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.envers.exception.RevisionDoesNotExistException;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;
import org.jboss.pressgang.ccms.model.exceptions.CustomConstraintViolationException;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.exception.BadRequestException;
import org.jboss.pressgang.ccms.provider.exception.InternalServerErrorException;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.provider.exception.ProviderException;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBDataProvider extends DataProvider {
    private final EntityManager entityManager;

    protected DBDataProvider(final EntityManager entityManager, final DBProviderFactory providerFactory,
            final List<ProviderListener> listeners) {
        super(providerFactory, listeners);
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected DBProviderFactory getProviderFactory() {
        return (DBProviderFactory) super.getProviderFactory();
    }

    protected DBWrapperFactory getWrapperFactory() {
        return getProviderFactory().getWrapperFactory();
    }

    protected <T> T getEntity(Class<T> clazz, Integer id) {
        try {
            final T entity = getEntityManager().find(clazz, id);
            if (entity == null) {
                throw new NotFoundException("Unable to find " + clazz.getSimpleName()  + " with id " + id);
            } else {
                return entity;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    protected <T extends AuditedEntity> T getRevisionEntity(T entity, Integer revision) {
        return getRevisionEntity((Class<T>) entity.getClass(), entity.getId(), revision);
    }

    protected <T extends AuditedEntity> T getRevisionEntity(Class<T> entityClass, Integer id, Integer revision) {
        try {
            final T entity =  EnversUtilities.getRevision(getEntityManager(), entityClass, id, revision);
            if (entity == null) {
                throw new NotFoundException();
            } else {
                return entity;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    protected <T extends AuditedEntity> List<T> getRevisionList(Class<T> entityClass, Integer id) {
        try {
            final Map<Number, T> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), entityClass, id);

            final List<T> revisions = new ArrayList<T>();
            for (final Map.Entry<Number, T> entry : revisionMapping.entrySet()) {
                revisions.add(entry.getValue());
            }

            return revisions;
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    protected <T> List<T> executeQuery(final CriteriaQuery<T> query) {
        try {
            return getEntityManager().createQuery(query).getResultList();
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    protected RuntimeException handleException(final Exception e) {
        if (e instanceof EntityNotFoundException || e instanceof RevisionDoesNotExistException) {
            return new NotFoundException(e);
        } else if (e instanceof ProviderException) {
            return (ProviderException) e;
        } else if (e instanceof PersistenceException || e instanceof ValidationException || e instanceof
                CustomConstraintViolationException) {
            return new BadRequestException(e);
        } else {
            return new InternalServerErrorException(e);
        }
    }
}
