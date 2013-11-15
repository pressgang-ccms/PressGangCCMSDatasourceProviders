package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBasePropertyTagWrapper;

public class DBPropertyTagWrapper extends DBBasePropertyTagWrapper<PropertyTagWrapper, PropertyTag> implements PropertyTagWrapper {

    private final PropertyTag propertyTag;

    public DBPropertyTagWrapper(final DBProviderFactory providerFactory, final PropertyTag propertyTag, boolean isRevision) {
        super(providerFactory, isRevision, PropertyTag.class);
        this.propertyTag = propertyTag;
    }

    @Override
    protected PropertyTag getEntity() {
        return propertyTag;
    }

    @Override
    protected PropertyTag getPropertyTag() {
        return getEntity();
    }
}
