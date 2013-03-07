package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.DBBasePropertyTagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBPropertyTagWrapper extends DBBasePropertyTagWrapper<PropertyTagWrapper> implements PropertyTagWrapper {

    private final PropertyTag propertyTag;

    public DBPropertyTagWrapper(final DBProviderFactory providerFactory, final PropertyTag propertyTag, boolean isRevision) {
        super(providerFactory, isRevision);
        this.propertyTag = propertyTag;
    }

    protected PropertyTag getPropertyTag() {
        return propertyTag;
    }

    @Override
    public CollectionWrapper<PropertyTagWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getPropertyTag()),
                PropertyTag.class, true);
    }

    @Override
    public PropertyTag unwrap() {
        return propertyTag;
    }
}
