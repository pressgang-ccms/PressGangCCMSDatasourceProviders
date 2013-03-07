package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.DBBaseToPropertyTagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.TagToPropertyTag;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBTagToPropertyTagWrapper extends DBBaseToPropertyTagWrapper<PropertyTagInTagWrapper> implements PropertyTagInTagWrapper {

    private final TagToPropertyTag propertyTag;
    
    public DBTagToPropertyTagWrapper(final DBProviderFactory providerFactory, final TagToPropertyTag propertyTag, boolean isRevision) {
        super(providerFactory, isRevision);
        this.propertyTag = propertyTag;
    }
    
    @Override
    protected TagToPropertyTag getToPropertyTag() {
        return propertyTag;
    }

    @Override
    public TagToPropertyTag unwrap() {
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
    public CollectionWrapper<PropertyTagInTagWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getToPropertyTag()),
                TagToPropertyTag.class, true);
    }
}
