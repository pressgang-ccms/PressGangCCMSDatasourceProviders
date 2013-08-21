package org.jboss.pressgang.ccms.utils;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public class RESTWrapperKey {
    private final Object o;
    private RESTBaseEntityV1<?, ?, ?> parent;
    private final Class<?> wrapperClass;

    public RESTWrapperKey(final RESTBaseEntityV1<?, ?, ?> o) {
        this(o, null, null);
    }

    public RESTWrapperKey(final RESTBaseEntityV1<?, ?, ?> o, final RESTBaseEntityV1<?, ?, ?> parent) {
        this(o, parent, null);
    }

    public RESTWrapperKey(final RESTBaseEntityV1<?, ?, ?> o, final Class<?> wrapperClass) {
        this(o, null, wrapperClass);
    }

    public RESTWrapperKey(final RESTBaseEntityV1<?, ?, ?> o, final RESTBaseEntityV1<?, ?, ?> parent, final Class<?> wrapperClass) {
        this.o = o;
        this.parent = parent;
        this.wrapperClass = wrapperClass;
    }

    public RESTWrapperKey(final RESTBaseCollectionV1<?, ?, ?> o) {
        this(o, null, null);
    }

    public RESTWrapperKey(final RESTBaseCollectionV1<?, ?, ?> o, RESTBaseEntityV1<?, ? ,?> parent) {
        this(o, parent, null);
    }

    public RESTWrapperKey(final RESTBaseCollectionV1<?, ?, ?> o, final Class<?> wrapperClass) {
        this(o, null, wrapperClass);
    }

    public RESTWrapperKey(final RESTBaseCollectionV1<?, ?, ?> o, RESTBaseEntityV1<?, ? ,?> parent, final Class<?> wrapperClass) {
        this.o = o;
        this.parent = parent;
        this.wrapperClass = wrapperClass;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof RESTWrapperKey)) return false;

        final RESTWrapperKey other = (RESTWrapperKey) o;
        return this.o == o && new EqualsBuilder().append(wrapperClass, other.wrapperClass).append(parent,
                other.parent).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(o).append(wrapperClass).append(parent).toHashCode();
    }
}
