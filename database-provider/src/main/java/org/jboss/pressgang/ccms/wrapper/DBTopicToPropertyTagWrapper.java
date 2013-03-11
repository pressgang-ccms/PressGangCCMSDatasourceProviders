package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseToPropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBTopicToPropertyTagWrapper extends DBBaseToPropertyTagWrapper<PropertyTagInTopicWrapper> implements
        PropertyTagInTopicWrapper {

    private final TopicToPropertyTag propertyTag;

    public DBTopicToPropertyTagWrapper(final DBProviderFactory providerFactory, final TopicToPropertyTag propertyTag, boolean isRevision) {
        super(providerFactory, isRevision);
        this.propertyTag = propertyTag;
    }

    @Override
    protected TopicToPropertyTag getToPropertyTag() {
        return propertyTag;
    }

    @Override
    public TopicToPropertyTag unwrap() {
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
    public CollectionWrapper<PropertyTagInTopicWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getToPropertyTag()),
                TopicToPropertyTag.class, true);
    }
}
