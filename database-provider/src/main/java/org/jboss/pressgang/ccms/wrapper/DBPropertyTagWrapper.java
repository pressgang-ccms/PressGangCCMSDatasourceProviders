package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBasePropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

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
