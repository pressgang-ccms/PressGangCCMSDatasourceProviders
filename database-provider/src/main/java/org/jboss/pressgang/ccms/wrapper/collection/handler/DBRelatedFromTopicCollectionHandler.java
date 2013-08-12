package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.RelationshipTag;
import org.jboss.pressgang.ccms.model.Topic;

public class DBRelatedFromTopicCollectionHandler implements DBCollectionHandler<Topic> {
    private final static RelationshipTag dummyRelationshipTag = new RelationshipTag();
    static {
        dummyRelationshipTag.setRelationshipTagId(1);
    }

    private Topic parent;

    public DBRelatedFromTopicCollectionHandler(final Topic parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(Collection<Topic> items, Topic entity) {
        parent.addRelationshipFrom(entity, dummyRelationshipTag);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<Topic> items, Topic entity) {
        parent.removeRelationshipFrom(entity, dummyRelationshipTag);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
