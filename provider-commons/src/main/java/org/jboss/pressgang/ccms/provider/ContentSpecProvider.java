package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ContentSpecProvider {
    ContentSpecWrapper getContentSpec(int id);

    ContentSpecWrapper getContentSpec(int id, Integer revision);

    CollectionWrapper<ContentSpecWrapper> getContentSpecsWithQuery(String query);

    CollectionWrapper<TagWrapper> getContentSpecTags(int id, Integer revision);

    UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getContentSpecProperties(int id, Integer revision,
            ContentSpecWrapper parent);

    UpdateableCollectionWrapper<CSNodeWrapper> getContentSpecNodes(int id, Integer revision);

    CollectionWrapper<TranslatedContentSpecWrapper> getContentSpecTranslations(int id, Integer revision);

    CollectionWrapper<ContentSpecWrapper> getContentSpecRevisions(int id, Integer revision);

    String getContentSpecAsString(int id);

    String getContentSpecAsString(int id, Integer revision);

    ContentSpecWrapper createContentSpec(ContentSpecWrapper contentSpec);

    ContentSpecWrapper createContentSpec(ContentSpecWrapper contentSpec, LogMessageWrapper logMessage);

    ContentSpecWrapper updateContentSpec(ContentSpecWrapper contentSpec);

    ContentSpecWrapper updateContentSpec(ContentSpecWrapper contentSpec, LogMessageWrapper logMessage);

    boolean deleteContentSpec(Integer id);

    boolean deleteContentSpec(Integer id, LogMessageWrapper logMessage);

    ContentSpecWrapper newContentSpec();

    CollectionWrapper<ContentSpecWrapper> newContentSpecCollection();
}
