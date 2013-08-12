package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;

public class DBTopicSourceUrlCollectionHandler implements DBUpdateableCollectionHandler<TopicSourceUrl> {
    private Topic parent;

    public DBTopicSourceUrlCollectionHandler(final Topic parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(TopicSourceUrl entity) {
    }

    @Override
    public void addItem(final TopicSourceUrl entity) {
        parent.addTopicSourceUrl(entity);
    }

    @Override
    public void removeItem(final TopicSourceUrl entity) {
        parent.removeTopicSourceUrl(entity);
    }
}
