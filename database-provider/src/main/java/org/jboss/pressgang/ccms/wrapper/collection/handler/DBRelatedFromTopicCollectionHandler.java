package org.jboss.pressgang.ccms.wrapper.collection.handler;

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
    public void addItem(Topic entity) {
        parent.addRelationshipFrom(entity, dummyRelationshipTag);
    }

    @Override
    public void removeItem(Topic entity) {
        parent.removeRelationshipFrom(entity, dummyRelationshipTag);
    }
}
