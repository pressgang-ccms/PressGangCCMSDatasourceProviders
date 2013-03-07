package org.jboss.pressgang.ccms.contentspec.provider;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.contentspec.utils.CSTransformer;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBContentSpecWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.filter.TopicFieldFilter;
import org.jboss.pressgang.ccms.filter.builder.ContentSpecFilterQueryBuilder;
import org.jboss.pressgang.ccms.filter.utils.EntityUtilities;
import org.jboss.pressgang.ccms.filter.utils.FilterUtilities;
import org.jboss.pressgang.ccms.model.Filter;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.utils.constants.CommonFilterConstants;

public class DBContentSpecProvider extends DBDataProvider implements ContentSpecProvider {
    private final DBProviderFactory providerFactory;

    protected DBContentSpecProvider(DBProviderFactory providerFactory, EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
        this.providerFactory = providerFactory;
    }

    @Override
    public ContentSpecWrapper getContentSpec(int id) {
        final ContentSpec contentSpec = getEntityManager().find(ContentSpec.class, id);
        return getWrapperFactory().create(contentSpec, false);
    }

    @Override
    public ContentSpecWrapper getContentSpec(int id, Integer revision) {
        if (revision == null) {
            return getContentSpec(id);
        } else {
            final ContentSpec dummyContentSpec = new ContentSpec();
            dummyContentSpec.setContentSpecId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyContentSpec, revision), true);
        }
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> getContentSpecsWithQuery(final String query) {
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

        final ContentSpecFilterQueryBuilder queryBuilder = new ContentSpecFilterQueryBuilder(getEntityManager());
        final CriteriaQuery<ContentSpec> criteriaQuery = FilterUtilities.buildQuery(filter, queryBuilder);

        final TypedQuery<ContentSpec> typedQuery = getEntityManager().createQuery(criteriaQuery);
        final List<ContentSpec> contentSpecs = typedQuery.getResultList();

        return getWrapperFactory().createCollection(contentSpecs, ContentSpec.class, false);
    }

    @Override
    public CollectionWrapper<TagWrapper> getContentSpecTags(int id, Integer revision) {
        final DBContentSpecWrapper contentSpec = (DBContentSpecWrapper) getContentSpec(id, revision);
        if (contentSpec == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(contentSpec.unwrap().getTags(), Tag.class, revision != null);
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getContentSpecNodes(int id, Integer revision) {
        final DBContentSpecWrapper contentSpec = (DBContentSpecWrapper) getContentSpec(id, revision);
        if (contentSpec == null) {
            return null;
        } else {
            final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(contentSpec.unwrap().getTopCSNodes(),
                    CSNode.class, revision != null);
            return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> getContentSpecRevisions(int id, Integer revision) {
        final ContentSpec contentSpec = new ContentSpec();
        contentSpec.setContentSpecId(id);
        final Map<Number, ContentSpec> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), contentSpec);

        final List<ContentSpec> revisions = new ArrayList<ContentSpec>();
        for (final Map.Entry<Number, ContentSpec> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, ContentSpec.class, revision != null);
    }

    @Override
    public String getContentSpecAsString(int id) {
        return getContentSpecAsString(id, null);
    }

    @Override
    public String getContentSpecAsString(int id, Integer revision) {
        final ContentSpecWrapper contentSpecWrapper = getContentSpec(id, revision);
        if (contentSpecWrapper == null) return null;

        final CSTransformer transformer = new CSTransformer();
        final org.jboss.pressgang.ccms.contentspec.ContentSpec contentSpec = transformer.transform(contentSpecWrapper, providerFactory);
        return contentSpec.toString();
    }

    @Override
    public ContentSpecWrapper createContentSpec(ContentSpecWrapper contentSpec) throws Exception {
        getEntityManager().persist(contentSpec.unwrap());

        // Flush the changes to the database
        getEntityManager().flush();

        return contentSpec;
    }

    @Override
    public ContentSpecWrapper updateContentSpec(ContentSpecWrapper contentSpec) throws Exception {
        getEntityManager().persist(contentSpec.unwrap());

        // Flush the changes to the database
        getEntityManager().flush();

        return contentSpec;
    }

    @Override
    public boolean deleteContentSpec(Integer id) throws Exception {
        final ContentSpec contentSpec = getEntityManager().find(ContentSpec.class, id);
        getEntityManager().remove(contentSpec);

        // Flush the changes to the database
        getEntityManager().flush();

        return true;
    }

    @Override
    public ContentSpecWrapper newContentSpec() {
        return getWrapperFactory().create(new ContentSpec(), false, ContentSpecWrapper.class);
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> newContentSpecCollection() {
        return getWrapperFactory().createCollection(new ArrayList<ContentSpec>(), ContentSpec.class, false, ContentSpecWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getContentSpecProperties(int id, Integer revision) {
        final DBContentSpecWrapper contentSpec = (DBContentSpecWrapper) getContentSpec(id, revision);
        if (contentSpec == null) {
            return null;
        } else {
            final CollectionWrapper<PropertyTagInContentSpecWrapper> collection = getWrapperFactory().createCollection(
                    contentSpec.unwrap().getProperties(), ContentSpecToPropertyTag.class, revision != null);
            return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) collection;
        }
    }
}
