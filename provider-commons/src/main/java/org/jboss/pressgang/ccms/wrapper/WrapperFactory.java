package org.jboss.pressgang.ccms.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.provider.DataProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public abstract class WrapperFactory {
    private DataProviderFactory providerFactory;

    protected WrapperFactory() {
    }

    protected WrapperFactory(final DataProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
    }

    protected DataProviderFactory getProviderFactory() {
        if (providerFactory == null) {
            throw new IllegalStateException("The Provider Factory has not been registered.");
        }
        return providerFactory;
    }

    public void setProviderFactory(final DataProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
    }

    /**
     * Create a wrapper around a specific entity.
     *
     * @param entity     The entity to be wrapped.
     * @param isRevision Whether the entity is a revision or not.
     * @param <T>        The wrapper class that is returned.
     * @return The Wrapper around the entity.
     */
    public abstract <T extends EntityWrapper<T>> T create(final Object entity, boolean isRevision);

    /**
     * Create a list of wrapped entities.
     *
     * @param entities       The collection of entities to wrap.
     * @param isRevisionList Whether or not the collection is a collection of revision entities.
     * @param <T>            The wrapper class that is returned.
     * @return An ArrayList of wrapped entities.
     */
    @SuppressWarnings("unchecked")
    public <T extends EntityWrapper<T>> List<T> createList(final Collection<?> entities, boolean isRevisionList) {
        final List<T> retValue = new ArrayList<T>();
        for (final Object object : entities) {
            retValue.add((T) create(object, isRevisionList));
        }

        return retValue;
    }

    /**
     * Create a wrapper around a collection of entities.
     *
     * @param collection           The collection to be wrapped.
     * @param entityClass          The class of the entity that the collection contains.
     * @param isRevisionCollection Whether or not the collection is a collection of revision entities.
     * @param <T>                  The wrapper class that is returned.
     * @return The Wrapper around the collection of entities.
     */
    public abstract <T extends EntityWrapper<T>> CollectionWrapper<T> createCollection(final Object collection, final Class<?> entityClass,
            boolean isRevisionCollection);
}
