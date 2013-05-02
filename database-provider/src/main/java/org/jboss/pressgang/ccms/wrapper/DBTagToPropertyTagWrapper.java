package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.TagToPropertyTag;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseToPropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBTagToPropertyTagWrapper extends DBBaseToPropertyTagWrapper<PropertyTagInTagWrapper,
        TagToPropertyTag> implements PropertyTagInTagWrapper {

    private final TagToPropertyTag propertyTag;

    public DBTagToPropertyTagWrapper(final DBProviderFactory providerFactory, final TagToPropertyTag propertyTag, boolean isRevision) {
        super(providerFactory, isRevision, TagToPropertyTag.class);
        this.propertyTag = propertyTag;
    }

    @Override
    protected TagToPropertyTag getEntity() {
        return propertyTag;
    }

    @Override
    public TagToPropertyTag unwrap() {
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
    public CollectionWrapper<PropertyTagInTagWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getEntity()),
                TagToPropertyTag.class, true);
    }
}
