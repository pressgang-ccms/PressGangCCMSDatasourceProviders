package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.DBBaseToPropertyTagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBContentSpecToPropertyTagWrapper extends DBBaseToPropertyTagWrapper<PropertyTagInContentSpecWrapper> implements
        PropertyTagInContentSpecWrapper {

    private final ContentSpecToPropertyTag propertyTag;

    public DBContentSpecToPropertyTagWrapper(final DBProviderFactory providerFactory, final ContentSpecToPropertyTag propertyTag,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.propertyTag = propertyTag;
    }

    @Override
    protected ContentSpecToPropertyTag getToPropertyTag() {
        return propertyTag;
    }

    @Override
    public ContentSpecToPropertyTag unwrap() {
        return propertyTag;
    }

    @Override
    public String getValue() {
        return getToPropertyTag().getValue();
    }

    @Override
    public Integer getRelationshipId() {
        return getToPropertyTag().getId();
    }

    @Override
    public void setValue(String value) {
        getToPropertyTag().setValue(value);
    }

    @Override
    public Boolean isValid() {
        return getToPropertyTag().isValid(getEntityManager(), getToPropertyTag().getRevision());
    }

    @Override
    public CollectionWrapper<PropertyTagInContentSpecWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getToPropertyTag()),
                ContentSpecToPropertyTag.class, true);
    }
}
