package org.jboss.pressgang.ccms.wrapper.structures;

import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DBWrapperKey {
    private final Object o;
    private final Class<?> entityClass;
    private final Class<?> wrapperClass;

    public DBWrapperKey(final Object o) {
        this(o, null);
    }

    public DBWrapperKey(final Collection<?> o, final Class<?> entityClass) {
        this(o, entityClass, null);
    }

    public DBWrapperKey(final Collection<?> o, final Class<?> entityClass, final Class<?> wrapperClass) {
        this.o = o;
        this.entityClass = entityClass;
        this.wrapperClass = wrapperClass;
    }

    public DBWrapperKey(final Object o, final Class<?> wrapperClass) {
        this.o = o;
        this.wrapperClass = wrapperClass;
        entityClass = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof DBWrapperKey)) return false;

        final DBWrapperKey other = (DBWrapperKey) o;
        // Empty collections are always equal so do a reference comparison instead
        if (this.o instanceof Collection && ((Collection) this.o).isEmpty()) {
            return this.o == other.o && new EqualsBuilder().append(wrapperClass, other.wrapperClass).append(entityClass,
                    other.entityClass).isEquals();
        } else {
            return new EqualsBuilder().append(this.o, other.o).append(wrapperClass, other.wrapperClass).append(entityClass,
                    other.entityClass).isEquals();
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(o).append(wrapperClass).toHashCode();
    }
}
