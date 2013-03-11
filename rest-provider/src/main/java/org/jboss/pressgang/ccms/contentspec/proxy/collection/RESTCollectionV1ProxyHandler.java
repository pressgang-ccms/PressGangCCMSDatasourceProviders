package org.jboss.pressgang.ccms.contentspec.proxy.collection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public class RESTCollectionV1ProxyHandler<T extends RESTBaseEntityV1<T, U, V>, U extends RESTBaseCollectionV1<T, U, V>,
        V extends RESTBaseCollectionItemV1<T, U, V>> implements MethodHandler {

    private final RESTProviderFactory providerFactory;
    private U proxyCollection;
    private final U collection;
    private final boolean isRevision;
    private final RESTBaseEntityV1<?, ?, ?> parent;

    public RESTCollectionV1ProxyHandler(final RESTProviderFactory providerFactory, final U collection, boolean isRevisionCollection) {
        this(providerFactory, collection, isRevisionCollection, null);
    }

    public RESTCollectionV1ProxyHandler(final RESTProviderFactory providerFactory, final U collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent) {
        this.collection = collection;
        isRevision = isRevisionCollection;
        this.providerFactory = providerFactory;
        this.parent = parent;
    }

    public U getCollection() {
        return collection;
    }

    protected RESTProviderFactory getProviderFactory() {
        return providerFactory;
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final U object = getCollection();
        if (thisMethod.getName().equals("getItems")) {
            // Change the list of items into a list of proxy objects
            final List<V> items = object.getItems();
            if (items == null) return null;

            final List<V> newItems = new ArrayList<V>();
            for (final V item : items) {
                final V newItem = item.clone(false);
                newItem.setItem(RESTEntityProxyFactory.createProxy(getProviderFactory(), item.getItem(), isRevision, parent));
                newItems.add(newItem);
            }
            return newItems;
        } else if (thisMethod.getName().equals("returnItems")) {
            // Change the list of items into a list of proxy objects
            final List<T> items = object.returnItems();
            if (items == null) return null;

            final List<T> newItems = new ArrayList<T>();
            for (final T item : items) {
                newItems.add(RESTEntityProxyFactory.createProxy(getProviderFactory(), item, isRevision, parent));
            }
            return newItems;
        } else if (thisMethod.getName().equals("addItem") || thisMethod.getName().equals("addNewItem") || thisMethod.getName().equals(
                "addRemoveItem") || thisMethod.getName().equals("addUpdateItem")) {
            // Ensure that we add the normal item and not the proxy
            final T item = (T) args[0];
            if (item instanceof ProxyObject) {
                args[0] = ((RESTBaseEntityV1ProxyHandler<T>) ((ProxyObject) item).getHandler()).getEntity();
            }
            return thisMethod.invoke(object, args);
        } else {
            return thisMethod.invoke(object, args);
        }
    }

    public U getProxyCollection() {
        return proxyCollection;
    }

    public void setProxyCollection(U proxyCollection) {
        this.proxyCollection = proxyCollection;
    }
}