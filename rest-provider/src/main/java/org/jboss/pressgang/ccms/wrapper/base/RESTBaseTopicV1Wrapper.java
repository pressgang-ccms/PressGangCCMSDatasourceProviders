package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.RESTListWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public abstract class RESTBaseTopicV1Wrapper<T extends BaseTopicWrapper<T>, U extends RESTBaseTopicV1<U, ?,
        ?>> extends RESTBaseEntityWrapper<T, U> implements BaseTopicWrapper<T> {

    protected RESTBaseTopicV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTBaseTopicV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity,
            final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public String getTitle() {
        return getProxyEntity().getTitle();
    }

    @Override
    public String getXml() {
        return getProxyEntity().getXml();
    }

    @Override
    public String getLocale() {
        return getProxyEntity().getLocale();
    }

    @Override
    public boolean hasTag(final int tagId) {
        return ComponentBaseTopicV1.hasTag(getProxyEntity(), tagId);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTags())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getProperties() {
        return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) RESTCollectionWrapperBuilder.<PropertyTagInTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getProperties())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .entityWrapperInterface(PropertyTagInTopicWrapper.class)
                .build();
    }

    @Override
    public PropertyTagInTopicWrapper getProperty(final int propertyId) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(ComponentBaseTopicV1.returnProperty(getProxyEntity(), propertyId))
                .isRevision(isRevisionEntity())
                .parent(getProxyEntity())
                .wrapperInterface(PropertyTagInTopicWrapper.class)
                .build();
    }

    @Override
    public List<PropertyTagInTopicWrapper> getProperties(final int propertyId) {
        return RESTListWrapperBuilder.<PropertyTagInTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .entities(ComponentBaseTopicV1.returnProperties(getProxyEntity(), propertyId))
                .isRevisionList(isRevisionEntity())
                .parent(getProxyEntity())
                .entityWrapperInterface(PropertyTagInTopicWrapper.class)
                .build();
    }

    @Override
    public List<TagWrapper> getTagsInCategories(final List<Integer> categoryIds) {
        return RESTListWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .entities(ComponentBaseTopicV1.returnTagsInCategoriesByID(getProxyEntity(), categoryIds))
                .isRevisionList(isRevisionEntity())
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<TopicSourceURLWrapper> getSourceURLs() {
        return (UpdateableCollectionWrapper<TopicSourceURLWrapper>) RESTCollectionWrapperBuilder.<TopicSourceURLWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getSourceUrls_OTM())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .build();
    }

    @Override
    public CollectionWrapper<T> getOutgoingRelationships() {
        return RESTCollectionWrapperBuilder.<T>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getOutgoingRelationships())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public CollectionWrapper<T> getIncomingRelationships() {
        return RESTCollectionWrapperBuilder.<T>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getIncomingRelationships())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }
}
