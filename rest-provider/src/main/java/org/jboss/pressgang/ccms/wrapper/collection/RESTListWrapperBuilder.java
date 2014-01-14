package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.elements.base.RESTBaseElementV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;

public class RESTListWrapperBuilder<T extends BaseWrapper<T>> {
    private RESTProviderFactory providerFactory;
    private Collection<? extends RESTBaseElementV1<?>> entities;
    private boolean isRevisionList = false;
    private Class<T> wrapperClass;
    private RESTBaseEntityV1<?, ?, ?> parent;

    public static <T extends BaseWrapper<T>> RESTListWrapperBuilder<T> newBuilder() {
        return new RESTListWrapperBuilder<T>();
    }

    protected RESTListWrapperBuilder() {
    }

    public RESTListWrapperBuilder<T> providerFactory(final RESTProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
        return this;
    }

    public RESTListWrapperBuilder<T> isRevisionList() {
        isRevisionList = true;
        return this;
    }

    public RESTListWrapperBuilder<T> isRevisionList(boolean isRevisionList) {
        this.isRevisionList = isRevisionList;
        return this;
    }

    public RESTListWrapperBuilder<T> entities(final Collection<? extends RESTBaseElementV1<?>> entities) {
        this.entities = entities;
        return this;
    }

    public RESTListWrapperBuilder<T> entityWrapperInterface(final Class<T> wrapperClass) {
        this.wrapperClass = wrapperClass;
        return this;
    }

    public RESTListWrapperBuilder<T> parent(final RESTBaseEntityV1<?, ?, ?> parent) {
        this.parent = parent;
        return this;
    }

    /**
     * Create a list of wrapped entities.
     *
     * @param <T>            The wrapper class that is returned.
     * @return An ArrayList of wrapped entities.
     */
    @SuppressWarnings("unchecked")
    public List<T> build() {
        final List<T> retValue = new ArrayList<T>();
        for (final RESTBaseElementV1<?> element : entities) {
            final T wrapper = RESTEntityWrapperBuilder.newBuilder()
                    .providerFactory(providerFactory)
                    .entity(element)
                    .isRevision(isRevisionList)
                    .wrapperInterface(wrapperClass)
                    .parent(parent)
                    .build();
            retValue.add(wrapper);
        }

        return retValue;
    }
}
