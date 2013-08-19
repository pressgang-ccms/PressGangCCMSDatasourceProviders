package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import java.util.HashMap;
import java.util.Map;

import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBProviderFactory extends DataProviderFactory {
    private Map<Class<?>, DBDataProvider> providerMap = new HashMap<Class<?>, DBDataProvider>();
    private final EntityManager entityManager;
    private final TransactionManager transactionManager;
    private boolean providersInitialised = false;

    public static DBProviderFactory create(final EntityManager entityManager) {
        return new DBProviderFactory(entityManager);
    }

    public static DBProviderFactory create(final EntityManager entityManager, final TransactionManager transactionManager) {
        return new DBProviderFactory(entityManager, transactionManager);
    }

    public DBProviderFactory(final EntityManager entityManager) {
        this(entityManager, null);
    }

    public DBProviderFactory(final EntityManager entityManager, final TransactionManager transactionManager) {
        super(new DBWrapperFactory());
        this.entityManager = entityManager;
        this.transactionManager = transactionManager;
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
        // Initialise the providers if they haven't been initialised
        if (!providersInitialised) {
            initialiseProviders();
        }

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
        if (transactionManager != null) {
            try {
                final int status = transactionManager.getStatus();
                if (status != Status.STATUS_ROLLING_BACK && status != Status.STATUS_ROLLEDBACK && status != Status.STATUS_NO_TRANSACTION) {
                    transactionManager.rollback();
                }
            } catch (SystemException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        }
    }

    @Override
    public boolean isNotificationsSupported() {
        return true;
    }

    /**
     * Initialise all the entity data providers available.
     */
    private void initialiseProviders() {
        // Topic Provider
        final DBTopicProvider topicProvider = new DBTopicProvider(getEntityManager(), getWrapperFactory(), getListeners());
        providerMap.put(DBTopicProvider.class, topicProvider);
        providerMap.put(TopicProvider.class, topicProvider);

        // Tag Provider
        final DBTagProvider tagProvider = new DBTagProvider(getEntityManager(), getWrapperFactory(), getListeners());
        providerMap.put(DBTagProvider.class, tagProvider);
        providerMap.put(TagProvider.class, tagProvider);

        // Translated Topic Provider
        final DBTranslatedTopicProvider translatedTopicProvider = new DBTranslatedTopicProvider(getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBTranslatedTopicProvider.class, translatedTopicProvider);
        providerMap.put(TranslatedTopicProvider.class, translatedTopicProvider);

        // Translated Topic String Provider
        final DBTranslatedTopicStringProvider translatedTopicStringProvider = new DBTranslatedTopicStringProvider(getEntityManager(),
                getWrapperFactory(), getListeners());
        providerMap.put(DBTranslatedTopicStringProvider.class, translatedTopicStringProvider);
        providerMap.put(TranslatedTopicStringProvider.class, translatedTopicStringProvider);

        // User Provider
        final DBUserProvider userProvider = new DBUserProvider(getEntityManager(), getWrapperFactory(), getListeners());
        providerMap.put(DBUserProvider.class, userProvider);
        providerMap.put(UserProvider.class, userProvider);

        // String Constant Provider
        final DBStringConstantProvider stringConstantProvider = new DBStringConstantProvider(getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBStringConstantProvider.class, stringConstantProvider);
        providerMap.put(StringConstantProvider.class, stringConstantProvider);

        // Blob Constant Provider
        final DBBlobConstantProvider blobConstantProvider = new DBBlobConstantProvider(getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBBlobConstantProvider.class, blobConstantProvider);
        providerMap.put(BlobConstantProvider.class, blobConstantProvider);

        // File Provider
        final DBFileProvider fileProvider = new DBFileProvider(getEntityManager(), getWrapperFactory(), getListeners());
        providerMap.put(DBFileProvider.class, fileProvider);
        providerMap.put(FileProvider.class, fileProvider);

        // Language File Provider
        final DBLanguageFileProvider languageFileProvider = new DBLanguageFileProvider(getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBLanguageFileProvider.class, languageFileProvider);
        providerMap.put(LanguageFileProvider.class, languageFileProvider);

        // Image Provider
        final DBImageProvider imageProvider = new DBImageProvider(getEntityManager(), getWrapperFactory(), getListeners());
        providerMap.put(DBImageProvider.class, imageProvider);
        providerMap.put(ImageProvider.class, imageProvider);

        // Language Image Provider
        final DBLanguageImageProvider languageImageProvider = new DBLanguageImageProvider(getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBLanguageImageProvider.class, languageImageProvider);
        providerMap.put(LanguageImageProvider.class, languageImageProvider);

        // Category Provider
        final DBCategoryProvider categoryProvider = new DBCategoryProvider(getEntityManager(), getWrapperFactory(), getListeners());
        providerMap.put(DBCategoryProvider.class, categoryProvider);
        providerMap.put(CategoryProvider.class, categoryProvider);

        // Topic Source URL Provider
        final DBTopicSourceURLProvider topicSourceURLProvider = new DBTopicSourceURLProvider(getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBTopicSourceURLProvider.class, topicSourceURLProvider);
        providerMap.put(TopicSourceURLProvider.class, topicSourceURLProvider);

        // PropertyTag Provider
        final DBPropertyTagProvider propertyTagProvider = new DBPropertyTagProvider(getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBPropertyTagProvider.class, propertyTagProvider);
        providerMap.put(PropertyTagProvider.class, propertyTagProvider);

        // Content Spec Provider
        final DBContentSpecProvider contentSpecProvider = new DBContentSpecProvider(this, getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBContentSpecProvider.class, contentSpecProvider);
        providerMap.put(ContentSpecProvider.class, contentSpecProvider);

        // Text Content Spec Provider
        final DBTextContentSpecProvider textContentSpecProvider = new DBTextContentSpecProvider(this, getEntityManager(),
                getWrapperFactory(), getListeners());
        providerMap.put(DBTextContentSpecProvider.class, textContentSpecProvider);
        providerMap.put(TextContentSpecProvider.class, textContentSpecProvider);

        // Content Spec Node Provider
        final DBCSNodeProvider csNodeProvider = new DBCSNodeProvider(getEntityManager(), getWrapperFactory(), getListeners());
        providerMap.put(DBCSNodeProvider.class, csNodeProvider);
        providerMap.put(CSNodeProvider.class, csNodeProvider);

        // Translated Content Spec Provider
        final DBTranslatedContentSpecProvider translatedContentSpecProvider = new DBTranslatedContentSpecProvider(getEntityManager(),
                getWrapperFactory(), getListeners());
        providerMap.put(DBTranslatedContentSpecProvider.class, translatedContentSpecProvider);
        providerMap.put(TranslatedContentSpecProvider.class, translatedContentSpecProvider);

        // Translated Content Spec Node Provider
        final DBTranslatedCSNodeProvider translatedCSNodeProvider = new DBTranslatedCSNodeProvider(getEntityManager(), getWrapperFactory(),
                getListeners());
        providerMap.put(DBTranslatedCSNodeProvider.class, translatedCSNodeProvider);
        providerMap.put(TranslatedCSNodeProvider.class, translatedCSNodeProvider);

        // Translated Content Spec Node String Provider
        final DBTranslatedCSNodeStringProvider translatedCSNodeStringProvider = new DBTranslatedCSNodeStringProvider(getEntityManager(),
                getWrapperFactory(), getListeners());
        providerMap.put(DBTranslatedCSNodeStringProvider.class, translatedCSNodeStringProvider);
        providerMap.put(TranslatedCSNodeStringProvider.class, translatedCSNodeStringProvider);

        // Log Message Provider
        final DBLogMessageProvider logMessageProvider = new DBLogMessageProvider(getEntityManager(), getWrapperFactory(), getListeners());
        providerMap.put(DBLogMessageProvider.class, logMessageProvider);
        providerMap.put(LogMessageProvider.class, logMessageProvider);

        providersInitialised = true;
    }
}
