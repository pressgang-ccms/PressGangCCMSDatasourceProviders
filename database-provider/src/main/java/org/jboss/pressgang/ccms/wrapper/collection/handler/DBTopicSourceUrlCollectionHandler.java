package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;

public class DBTopicSourceUrlCollectionHandler implements DBUpdateableCollectionHandler<TopicSourceUrl> {
    private Topic parent;

    public DBTopicSourceUrlCollectionHandler(final Topic parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<TopicSourceUrl> items, TopicSourceUrl entity) {
    }

    @Override
    public void addItem(Collection<TopicSourceUrl> items, final TopicSourceUrl entity) {
        parent.addTopicSourceUrl(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<TopicSourceUrl> items, final TopicSourceUrl entity) {
        parent.removeTopicSourceUrl(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
