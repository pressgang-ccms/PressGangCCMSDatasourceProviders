package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseToPropertyTagWrapper;

public class DBTopicToPropertyTagWrapper extends DBBaseToPropertyTagWrapper<PropertyTagInTopicWrapper, TopicToPropertyTag> implements
        PropertyTagInTopicWrapper {
    private final TopicToPropertyTag propertyTag;

    public DBTopicToPropertyTagWrapper(final DBProviderFactory providerFactory, final TopicToPropertyTag propertyTag, boolean isRevision) {
        super(providerFactory, isRevision, TopicToPropertyTag.class);
        this.propertyTag = propertyTag;
    }

    @Override
    protected TopicToPropertyTag getEntity() {
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
}
