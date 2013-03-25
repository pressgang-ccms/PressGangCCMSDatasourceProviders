package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.filter.TopicFieldFilter;
import org.jboss.pressgang.ccms.filter.builder.TranslatedTopicDataFilterQueryBuilder;
import org.jboss.pressgang.ccms.filter.utils.EntityUtilities;
import org.jboss.pressgang.ccms.filter.utils.FilterUtilities;
import org.jboss.pressgang.ccms.model.Filter;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.TranslatedTopicData;
import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.utils.constants.CommonFilterConstants;
import org.jboss.pressgang.ccms.wrapper.DBTranslatedTopicDataWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedTopicProvider extends DBDataProvider implements TranslatedTopicProvider {
    protected DBTranslatedTopicProvider(final EntityManager entityManager, final DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public TranslatedTopicWrapper getTranslatedTopic(int id) {
        final TranslatedTopicData topic = getEntityManager().find(TranslatedTopicData.class, id);
        return getWrapperFactory().create(topic, false);
    }

    @Override
    public TranslatedTopicWrapper getTranslatedTopic(int id, Integer revision) {
        if (revision == null) {
            return getTranslatedTopic(id);
        } else {
            final TranslatedTopicData dummyTopic = new TranslatedTopicData();
            dummyTopic.setTranslatedTopicDataId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyTopic, revision), true);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTranslatedTopicTags(int id, Integer revision) {
        final DBTranslatedTopicDataWrapper topic = (DBTranslatedTopicDataWrapper) getTranslatedTopic(id, revision);
        if (topic == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(topic.unwrap().getTranslatedTopic().getEnversTopic(getEntityManager()).getTags(),
                    Tag.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<PropertyTagInTopicWrapper> getTranslatedTopicProperties(int id, Integer revision) {
        final DBTranslatedTopicDataWrapper topic = (DBTranslatedTopicDataWrapper) getTranslatedTopic(id, revision);
        if (topic == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(
                    topic.unwrap().getTranslatedTopic().getEnversTopic(getEntityManager()).getTopicToPropertyTags(),
                    TopicToPropertyTag.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicOutgoingRelationships(int id, Integer revision) {
        final DBTranslatedTopicDataWrapper topic = (DBTranslatedTopicDataWrapper) getTranslatedTopic(id, revision);
        if (topic == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(topic.unwrap().getOutgoingRelatedTranslatedTopicData(getEntityManager()),
                    TranslatedTopicData.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicIncomingRelationships(int id, Integer revision) {
        final DBTranslatedTopicDataWrapper topic = (DBTranslatedTopicDataWrapper) getTranslatedTopic(id, revision);
        if (topic == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(topic.unwrap().getIncomingRelatedTranslatedTopicData(getEntityManager()),
                    TranslatedTopicData.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTranslatedTopicSourceUrls(int id, Integer revision) {
        final DBTranslatedTopicDataWrapper topic = (DBTranslatedTopicDataWrapper) getTranslatedTopic(id, revision);
        if (topic == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(
                    topic.unwrap().getTranslatedTopic().getEnversTopic(getEntityManager()).getTopicSourceUrls(), TopicSourceUrl.class,
                    revision != null);
        }
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings(int id, Integer revision) {
        final DBTranslatedTopicDataWrapper topic = (DBTranslatedTopicDataWrapper) getTranslatedTopic(id, revision);
        if (topic == null) {
            return null;
        } else {
            final CollectionWrapper<TranslatedTopicStringWrapper> collection = getWrapperFactory().createCollection(
                    topic.unwrap().getTranslatedTopicStrings(), TranslatedTopicString.class, revision != null);
            return (UpdateableCollectionWrapper<TranslatedTopicStringWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicRevisions(int id, Integer revision) {
        final TranslatedTopicData topic = new TranslatedTopicData();
        topic.setTranslatedTopicDataId(id);
        final Map<Number, TranslatedTopicData> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), topic);

        final List<TranslatedTopicData> revisions = new ArrayList<TranslatedTopicData>();
        for (final Map.Entry<Number, TranslatedTopicData> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, TranslatedTopicData.class, revision != null);
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicsWithQuery(String query) {
        final String fixedQuery = query.replace("query;", "");
        final String[] queryValues = fixedQuery.split(";");
        final Map<String, String> queryParameters = new HashMap<String, String>();
        for (final String value : queryValues) {
            if (value.trim().isEmpty()) continue;
            String[] keyValue = value.split("=", 2);
            try {
                queryParameters.put(keyValue[0], URLDecoder.decode(keyValue[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // Should support UTF-8, if not throw a runtime error.
                throw new RuntimeException(e);
            }
        }

        final Filter filter = EntityUtilities.populateFilter(getEntityManager(), queryParameters, CommonFilterConstants.FILTER_ID,
                CommonFilterConstants.MATCH_TAG, CommonFilterConstants.GROUP_TAG, CommonFilterConstants.CATEORY_INTERNAL_LOGIC,
                CommonFilterConstants.CATEORY_EXTERNAL_LOGIC, CommonFilterConstants.MATCH_LOCALE, new TopicFieldFilter());

        final TranslatedTopicDataFilterQueryBuilder queryBuilder = new TranslatedTopicDataFilterQueryBuilder(getEntityManager());
        final CriteriaQuery<TranslatedTopicData> criteriaQuery = FilterUtilities.buildQuery(filter, queryBuilder);

        final TypedQuery<TranslatedTopicData> typedQuery = getEntityManager().createQuery(criteriaQuery);
        final List<TranslatedTopicData> translatedTopics = typedQuery.getResultList();

        return getWrapperFactory().createCollection(translatedTopics, TranslatedTopicData.class, false);
    }

    @Override
    public TranslatedTopicWrapper createTranslatedTopic(TranslatedTopicWrapper translatedTopic) throws Exception {
        getEntityManager().persist(translatedTopic.unwrap());

        // Flush the changes to the database
        getEntityManager().flush();

        return translatedTopic;
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> createTranslatedTopics(
            CollectionWrapper<TranslatedTopicWrapper> translatedTopics) throws Exception {
        for (final TranslatedTopicWrapper topic : translatedTopics.getItems()) {
            getEntityManager().persist(topic.unwrap());
        }

        // Flush the changes to the database
        getEntityManager().flush();

        return translatedTopics;
    }

    @Override
    public TranslatedTopicWrapper updateTranslatedTopic(TranslatedTopicWrapper translatedTopic) throws Exception {
        getEntityManager().persist(translatedTopic.unwrap());

        // Flush the changes to the database
        getEntityManager().flush();

        return translatedTopic;
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> updateTranslatedTopics(
            CollectionWrapper<TranslatedTopicWrapper> translatedTopics) throws Exception {
        for (final TranslatedTopicWrapper topic : translatedTopics.getItems()) {
            getEntityManager().persist(topic.unwrap());
        }

        // Flush the changes to the database
        getEntityManager().flush();

        return translatedTopics;
    }

    @Override
    public boolean deleteTranslatedTopic(Integer id) throws Exception {
        final TranslatedTopicData topic = getEntityManager().find(TranslatedTopicData.class, id);
        getEntityManager().remove(topic);

        // Flush the changes to the database
        getEntityManager().flush();

        return true;
    }

    @Override
    public boolean deleteTranslatedTopics(List<Integer> ids) throws Exception {
        for (final Integer id : ids) {
            final TranslatedTopicData topic = getEntityManager().find(TranslatedTopicData.class, id);
            getEntityManager().remove(topic);
        }

        // Flush the changes to the database
        getEntityManager().flush();

        return true;
    }

    @Override
    public TranslatedTopicWrapper newTranslatedTopic() {
        return getWrapperFactory().create(new TranslatedTopicData(), false);
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> newTranslatedTopicCollection() {
        return getWrapperFactory().createCollection(new ArrayList<TranslatedTopicData>(), TranslatedTopicData.class, false);
    }
}
