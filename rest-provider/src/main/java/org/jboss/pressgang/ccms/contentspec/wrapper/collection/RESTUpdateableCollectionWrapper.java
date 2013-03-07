package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseUpdateCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseUpdateCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public abstract class RESTUpdateableCollectionWrapper<T extends EntityWrapper<T>, U extends RESTBaseEntityV1<U, V, ?>,
        V extends RESTBaseUpdateCollectionV1<U, V, ?>> extends RESTCollectionWrapper<T, U, V> implements UpdateableCollectionWrapper<T> {

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final Class<T> wrapperClass) {
        super(providerFactory, collection, isRevisionCollection, wrapperClass);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTUpdateableCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent, final Class<T> wrapperClass) {
        super(providerFactory, collection, isRevisionCollection, parent, wrapperClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addUpdateItem(T entity) {
        getCollection().addUpdateItem(getEntity(entity));
        getEntities().put(entity, RESTBaseUpdateCollectionItemV1.UPDATE_STATE);
    }

    @Override
    public List<T> getUpdateItems() {
        final List<T> updateItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getEntities().entrySet()) {
            if (entity.getValue() == RESTBaseUpdateCollectionItemV1.UPDATE_STATE) {
                updateItems.add(entity.getKey());
            }
        }

        return updateItems;
    }
}
