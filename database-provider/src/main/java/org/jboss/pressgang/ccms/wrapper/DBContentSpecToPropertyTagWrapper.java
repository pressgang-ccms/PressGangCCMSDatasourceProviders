package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseToPropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBContentSpecToPropertyTagWrapper extends DBBaseToPropertyTagWrapper<PropertyTagInContentSpecWrapper, ContentSpecToPropertyTag> implements
        PropertyTagInContentSpecWrapper {

    private final ContentSpecToPropertyTag propertyTag;

    public DBContentSpecToPropertyTagWrapper(final DBProviderFactory providerFactory, final ContentSpecToPropertyTag propertyTag,
            boolean isRevision) {
        super(providerFactory, isRevision, ContentSpecToPropertyTag.class);
        this.propertyTag = propertyTag;
    }

    @Override
    protected ContentSpecToPropertyTag getEntity() {
        return propertyTag;
    }

    @Override
    public ContentSpecToPropertyTag unwrap() {
        return propertyTag;
    }

    @Override
    public String getValue() {
        return getEntity().getValue();
    }

    @Override
    public Integer getRelationshipId() {
        return getEntity().getId();
    }

    @Override
    public void setValue(String value) {
        getEntity().setValue(value);
    }

    @Override
    public Boolean isValid() {
        return getEntity().isValid(getEntityManager(), getEntity().getRevision());
    }

    @Override
    public CollectionWrapper<PropertyTagInContentSpecWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getEntity()),
                ContentSpecToPropertyTag.class, true);
    }
}
