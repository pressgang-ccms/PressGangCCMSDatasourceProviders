package org.jboss.pressgang.ccms.contentspec.provider;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;

public class DBProviderFactory extends DataProviderFactory {
    private Map<Class<?>, DBDataProvider> providerMap = new HashMap<Class<?>, DBDataProvider>();
    private final EntityManager entityManager;

    public static DBProviderFactory create(final EntityManager entityManager) {
        return (DBProviderFactory) DataProviderFactory.create(entityManager);
    }

    public DBProviderFactory(final EntityManager entityManager) {
        this.entityManager = entityManager;
        initialiseProviders();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public DBWrapperFactory getWrapperFactory() {
        return (DBWrapperFactory) super.getWrapperFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T loadProvider(Class<T> clazz) {
        if (providerMap.containsKey(clazz)) {
            return (T) providerMap.get(clazz);
        } else {
            throw new NoClassDefFoundError("No implementation was found for the " + clazz.getName() + " class.");
        }
    }

    @Override
    public boolean isRollbackSupported() {
        return true;
    }

    @Override
    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    /**
     * Initialise all the entity data providers available.
     */
    private void initialiseProviders() {
        // Topic Provider
        final DBTopicProvider topicProvider = new DBTopicProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBTopicProvider.class, topicProvider);
        providerMap.put(TopicProvider.class, topicProvider);

        // Tag Provider
        final DBTagProvider tagProvider = new DBTagProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBTagProvider.class, tagProvider);
        providerMap.put(TagProvider.class, tagProvider);

        // Translated Topic Provider
        final DBTranslatedTopicProvider translatedTopicProvider = new DBTranslatedTopicProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBTranslatedTopicProvider.class, translatedTopicProvider);
        providerMap.put(TranslatedTopicProvider.class, translatedTopicProvider);

        // Translated Topic String Provider
        final DBTranslatedTopicStringProvider translatedTopicStringProvider = new DBTranslatedTopicStringProvider(getEntityManager(),
                getWrapperFactory());
        providerMap.put(DBTranslatedTopicStringProvider.class, translatedTopicStringProvider);
        providerMap.put(TranslatedTopicStringProvider.class, translatedTopicStringProvider);

        // User Provider
        final DBUserProvider userProvider = new DBUserProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBUserProvider.class, userProvider);
        providerMap.put(UserProvider.class, userProvider);

        // String Constant Provider
        final DBStringConstantProvider stringConstantProvider = new DBStringConstantProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBStringConstantProvider.class, stringConstantProvider);
        providerMap.put(StringConstantProvider.class, stringConstantProvider);

        // Blob Constant Provider
        final DBBlobConstantProvider blobConstantProvider = new DBBlobConstantProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBBlobConstantProvider.class, blobConstantProvider);
        providerMap.put(BlobConstantProvider.class, blobConstantProvider);

        // Image Provider
        final DBImageProvider imageProvider = new DBImageProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBImageProvider.class, imageProvider);
        providerMap.put(ImageProvider.class, imageProvider);

        // Language Image Provider
        final DBLanguageImageProvider languageImageProvider = new DBLanguageImageProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBLanguageImageProvider.class, languageImageProvider);
        providerMap.put(LanguageImageProvider.class, languageImageProvider);

        // Category Provider
        final DBCategoryProvider categoryProvider = new DBCategoryProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBCategoryProvider.class, categoryProvider);
        providerMap.put(CategoryProvider.class, categoryProvider);

        // Topic Source URL Provider
        final DBTopicSourceURLProvider topicSourceURLProvider = new DBTopicSourceURLProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBTopicSourceURLProvider.class, topicSourceURLProvider);
        providerMap.put(TopicSourceURLProvider.class, topicSourceURLProvider);

        // PropertyTag Provider
        final DBPropertyTagProvider propertyTagProvider = new DBPropertyTagProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBPropertyTagProvider.class, propertyTagProvider);
        providerMap.put(PropertyTagProvider.class, propertyTagProvider);

        // Content Spec Provider
        final DBContentSpecProvider contentSpecProvider = new DBContentSpecProvider(this, getEntityManager(), getWrapperFactory());
        providerMap.put(DBContentSpecProvider.class, contentSpecProvider);
        providerMap.put(ContentSpecProvider.class, contentSpecProvider);

        // Content Spec Node Provider
        final DBCSNodeProvider csNodeProvider = new DBCSNodeProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBCSNodeProvider.class, csNodeProvider);
        providerMap.put(CSNodeProvider.class, csNodeProvider);

        // Content Spec Translated Node Provider
        final DBCSTranslatedNodeProvider csTranslatedNodeProvider = new DBCSTranslatedNodeProvider(getEntityManager(), getWrapperFactory());
        providerMap.put(DBCSTranslatedNodeProvider.class, csTranslatedNodeProvider);
        providerMap.put(CSTranslatedNodeProvider.class, csTranslatedNodeProvider);

        // Content Spec Translated Node String Provider
        final DBCSTranslatedNodeStringProvider csTranslatedNodeStringProvider = new DBCSTranslatedNodeStringProvider(getEntityManager(),
                getWrapperFactory());
        providerMap.put(DBCSTranslatedNodeStringProvider.class, csTranslatedNodeStringProvider);
        providerMap.put(CSTranslatedNodeStringProvider.class, csTranslatedNodeStringProvider);
    }
}
