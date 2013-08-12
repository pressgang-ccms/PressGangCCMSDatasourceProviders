package org.jboss.pressgang.ccms.wrapper.collection.handler;

import javax.persistence.EntityManager;

import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TranslatedTopicData;

public class DBRelatedToTranslatedTopicCollectionHandler implements DBCollectionHandler<TranslatedTopicData> {
    private final EntityManager entityManager;
    private Topic parent;

    public DBRelatedToTranslatedTopicCollectionHandler(final Topic parent, final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.parent = parent;
    }

    @Override
    public void addItem(TranslatedTopicData entity) {
        parent.addRelationshipTo(entityManager, entity.getTranslatedTopic().getTopicId(), 1);
    }

    @Override
    public void removeItem(TranslatedTopicData entity) {
        parent.removeRelationshipTo(entity.getTranslatedTopic().getTopicId(), 1);
    }
}
