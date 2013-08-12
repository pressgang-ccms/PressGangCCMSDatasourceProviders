package org.jboss.pressgang.ccms.wrapper.collection.handler;

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
    public void addItem(Topic entity) {
        parent.addRelationshipTo(entity, dummyRelationshipTag);
    }

    @Override
    public void removeItem(Topic entity) {
        parent.removeRelationshipTo(entity, dummyRelationshipTag);
    }
}
