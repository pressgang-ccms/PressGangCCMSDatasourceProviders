package org.jboss.pressgang.ccms.wrapper.collection.handler;

import javax.persistence.EntityManager;

import java.util.Collection;
import java.util.List;

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
    public void addItem(Collection<TranslatedTopicData> items, TranslatedTopicData entity) {
        parent.addRelationshipTo(entityManager, entity.getTranslatedTopic().getTopicId(), 1);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<TranslatedTopicData> items, TranslatedTopicData entity) {
        parent.removeRelationshipTo(entity.getTranslatedTopic().getTopicId(), 1);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
