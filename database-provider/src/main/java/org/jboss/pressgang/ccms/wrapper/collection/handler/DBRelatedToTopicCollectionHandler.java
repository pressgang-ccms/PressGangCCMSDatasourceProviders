package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.RelationshipTag;
import org.jboss.pressgang.ccms.model.Topic;

public class DBRelatedToTopicCollectionHandler implements DBCollectionHandler<Topic> {
    private final static RelationshipTag dummyRelationshipTag = new RelationshipTag();
    static {
        dummyRelationshipTag.setRelationshipTagId(1);
    }

    private Topic parent;

    public DBRelatedToTopicCollectionHandler(final Topic parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(Collection<Topic> items, Topic entity) {
        parent.addRelationshipTo(entity, dummyRelationshipTag);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<Topic> items, Topic entity) {
        parent.removeRelationshipTo(entity, dummyRelationshipTag);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
