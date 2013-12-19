package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityUpdateCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTUpdateCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseObjectV1;
import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;

public abstract class RESTUpdateableCollectionWrapper<T extends BaseWrapper<T>, U extends RESTBaseObjectV1<U>,
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
