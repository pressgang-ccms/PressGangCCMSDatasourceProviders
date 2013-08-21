package org.jboss.pressgang.ccms.wrapper.structures;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DBWrapperKey {
    private final Object o;
    private final Class<?> wrapperClass;

    public DBWrapperKey(final Object o) {
        this(o, null);
    }

    public DBWrapperKey(final Object o, final Class<?> wrapperClass) {
        this.o = o;
        this.wrapperClass = wrapperClass;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof DBWrapperKey)) return false;

        final DBWrapperKey other = (DBWrapperKey) o;
        return new EqualsBuilder().append(this.o, other.o).append(wrapperClass, other.wrapperClass).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(o).append(wrapperClass).toHashCode();
    }
}
