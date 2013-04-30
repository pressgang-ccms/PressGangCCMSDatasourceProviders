package org.jboss.pressgang.ccms.wrapper.base;

import java.util.List;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public abstract class RESTBaseTopicV1Wrapper<T extends BaseTopicWrapper<T>, U extends RESTBaseTopicV1<U, ?,
        ?>> extends RESTBaseWrapper<T, U> implements BaseTopicWrapper<T> {

    protected RESTBaseTopicV1Wrapper(final RESTProviderFactory providerFactory, boolean isRevision) {
        super(providerFactory, isRevision);
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
    public String getHtml() {
        return getProxyEntity().getHtml();
    }

    @Override
    public boolean hasTag(final int tagId) {
        return ComponentBaseTopicV1.hasTag(getProxyEntity(), tagId);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getProxyEntity().getTags(), RESTTagV1.class, isRevisionEntity());
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(
                getProxyEntity().getProperties(), RESTAssignedPropertyTagV1.class, isRevisionEntity(), getProxyEntity(),
                PropertyTagInTopicWrapper.class);
        return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) collection;
    }

    @Override
    public PropertyTagInTopicWrapper getProperty(final int propertyId) {
        return getWrapperFactory().create(ComponentBaseTopicV1.returnProperty(getProxyEntity(), propertyId), isRevisionEntity(),
                getProxyEntity(), PropertyTagInTopicWrapper.class);
    }

    @Override
    public List<TagWrapper> getTagsInCategories(final List<Integer> categoryIds) {
        return getWrapperFactory().createList(ComponentBaseTopicV1.returnTagsInCategoriesByID(getProxyEntity(), categoryIds),
                isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getSourceURLs() {
        return getWrapperFactory().createCollection(getProxyEntity().getSourceUrls_OTM(), RESTTopicSourceUrlV1.class, isRevisionEntity(),
                getProxyEntity());
    }
}
