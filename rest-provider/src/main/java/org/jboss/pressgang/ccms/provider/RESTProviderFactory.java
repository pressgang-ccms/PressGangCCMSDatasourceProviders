package org.jboss.pressgang.ccms.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.pressgang.ccms.rest.RESTErrorInterceptor;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;

public class RESTProviderFactory extends DataProviderFactory {
    private final RESTManager restManager;
    private Map<Class<?>, RESTDataProvider> providerMap = new HashMap<Class<?>, RESTDataProvider>();

    public static RESTProviderFactory create(final String serverUrl) {
        return new RESTProviderFactory(serverUrl);
    }

    public RESTProviderFactory(final String serverUrl) {
        super(new RESTWrapperFactory());
        restManager = new RESTManager(serverUrl);
        restManager.getProxyFactory().getProviderFactory().addClientErrorInterceptor(new RESTErrorInterceptor());
        initialiseProviders();
    }

    @Override
    public RESTWrapperFactory getWrapperFactory() {
        return (RESTWrapperFactory) super.getWrapperFactory();
    }

    public RESTManager getRESTManager() {
        return restManager;
    }

    /**
     * Initialise all the entity data providers available.
     */
    private void initialiseProviders() {
        // Topic Provider
        final RESTTopicProvider topicProvider = new RESTTopicProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTTopicProvider.class, topicProvider);
        providerMap.put(TopicProvider.class, topicProvider);

        // Tag Provider
        final RESTTagProvider tagProvider = new RESTTagProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTTagProvider.class, tagProvider);
        providerMap.put(TagProvider.class, tagProvider);

        // Tag In Category Provider
        final RESTTagInCategoryProvider tagInCategoryProvider = new RESTTagInCategoryProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTTagInCategoryProvider.class, tagInCategoryProvider);

        // Translated Topic Provider
        final RESTTranslatedTopicProvider translatedTopicProvider = new RESTTranslatedTopicProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTTranslatedTopicProvider.class, translatedTopicProvider);
        providerMap.put(TranslatedTopicProvider.class, translatedTopicProvider);

        // Translated Topic String Provider
        final RESTTranslatedTopicStringProvider translatedTopicStringProvider = new RESTTranslatedTopicStringProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTTranslatedTopicStringProvider.class, translatedTopicStringProvider);
        providerMap.put(TranslatedTopicStringProvider.class, translatedTopicStringProvider);

        // User Provider
        final RESTUserProvider userProvider = new RESTUserProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTUserProvider.class, userProvider);
        providerMap.put(UserProvider.class, userProvider);

        // String Constant Provider
        final RESTStringConstantProvider stringConstantProvider = new RESTStringConstantProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTStringConstantProvider.class, stringConstantProvider);
        providerMap.put(StringConstantProvider.class, stringConstantProvider);

        // Blob Constant Provider
        final RESTBlobConstantProvider blobConstantProvider = new RESTBlobConstantProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTBlobConstantProvider.class, blobConstantProvider);
        providerMap.put(BlobConstantProvider.class, blobConstantProvider);

        // File Provider
        final RESTFileProvider fileProvider = new RESTFileProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTFileProvider.class, fileProvider);
        providerMap.put(FileProvider.class, fileProvider);

        // Language File Provider
        final RESTLanguageFileProvider languageFileProvider = new RESTLanguageFileProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTLanguageFileProvider.class, languageFileProvider);
        providerMap.put(LanguageFileProvider.class, languageFileProvider);

        // Image Provider
        final RESTImageProvider imageProvider = new RESTImageProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTImageProvider.class, imageProvider);
        providerMap.put(ImageProvider.class, imageProvider);

        // Language Image Provider
        final RESTLanguageImageProvider languageImageProvider = new RESTLanguageImageProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTLanguageImageProvider.class, languageImageProvider);
        providerMap.put(LanguageImageProvider.class, languageImageProvider);

        // Category Provider
        final RESTCategoryProvider categoryProvider = new RESTCategoryProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTCategoryProvider.class, categoryProvider);
        providerMap.put(CategoryProvider.class, categoryProvider);

        // Category In Tag Provider
        final RESTCategoryInTagProvider categoryInTagProvider = new RESTCategoryInTagProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTCategoryInTagProvider.class, categoryInTagProvider);

        // Topic Source URL Provider
        final RESTTopicSourceURLProvider topicSourceURLProvider = new RESTTopicSourceURLProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTTopicSourceURLProvider.class, topicSourceURLProvider);
        providerMap.put(TopicSourceURLProvider.class, topicSourceURLProvider);

        // PropertyTag Provider
        final RESTPropertyTagProvider propertyTagProvider = new RESTPropertyTagProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTPropertyTagProvider.class, propertyTagProvider);
        providerMap.put(PropertyTagProvider.class, propertyTagProvider);

        // PropertyTag In Topic Provider
        final RESTPropertyTagInTopicProvider propertyTagInTopicProvider = new RESTPropertyTagInTopicProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTPropertyTagInTopicProvider.class, propertyTagInTopicProvider);

        // PropertyTag In Tag Provider
        final RESTPropertyTagInTagProvider propertyTagInTagProvider = new RESTPropertyTagInTagProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTPropertyTagInTagProvider.class, propertyTagInTagProvider);

        // PropertyTag In ContentSpec Provider
        final RESTPropertyTagInContentSpecProvider propertyTagInContentSpecProvider = new RESTPropertyTagInContentSpecProvider(
                getRESTManager(), getWrapperFactory());
        providerMap.put(RESTPropertyTagInContentSpecProvider.class, propertyTagInContentSpecProvider);

        // PropertyTag In PropertyCategory Provider
        final RESTPropertyTagInPropertyCategoryProvider propertyTagInPropertyCategoryProvider = new
                RESTPropertyTagInPropertyCategoryProvider(
                getRESTManager(), getWrapperFactory());
        providerMap.put(RESTPropertyTagInPropertyCategoryProvider.class, propertyTagInPropertyCategoryProvider);

        // Content Spec Provider
        final RESTContentSpecProvider contentSpecProvider = new RESTContentSpecProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTContentSpecProvider.class, contentSpecProvider);
        providerMap.put(ContentSpecProvider.class, contentSpecProvider);

        // Text Content Spec Provider
        final RESTTextContentSpecProvider textContentSpecProvider = new RESTTextContentSpecProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTTextContentSpecProvider.class, textContentSpecProvider);
        providerMap.put(TextContentSpecProvider.class, textContentSpecProvider);

        // Content Spec Node Provider
        final RESTCSNodeProvider csNodeProvider = new RESTCSNodeProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTCSNodeProvider.class, csNodeProvider);
        providerMap.put(CSNodeProvider.class, csNodeProvider);

        // Content Spec Related Node Provider
        final RESTCSRelatedNodeProvider csRelatedNodeProvider = new RESTCSRelatedNodeProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTCSRelatedNodeProvider.class, csRelatedNodeProvider);

        // Translated Content Spec Provider
        final RESTTranslatedContentSpecProvider translatedContentSpecProvider = new RESTTranslatedContentSpecProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTTranslatedContentSpecProvider.class, translatedContentSpecProvider);
        providerMap.put(TranslatedContentSpecProvider.class, translatedContentSpecProvider);

        // Translated Content Spec Node Provider
        final RESTTranslatedCSNodeProvider translatedCSNodeProvider = new RESTTranslatedCSNodeProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTTranslatedCSNodeProvider.class, translatedCSNodeProvider);
        providerMap.put(TranslatedCSNodeProvider.class, translatedCSNodeProvider);

        // Translated Content Spec Node String Provider
        final RESTTranslatedCSNodeStringProvider translatedCSNodeStringProvider = new RESTTranslatedCSNodeStringProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTTranslatedCSNodeStringProvider.class, translatedCSNodeStringProvider);
        providerMap.put(TranslatedCSNodeStringProvider.class, translatedCSNodeStringProvider);

        // Log Message Provider
        final RESTLogMessageProvider logMessageProvider = new RESTLogMessageProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTLogMessageProvider.class, logMessageProvider);
        providerMap.put(LogMessageProvider.class, logMessageProvider);

        // Server Settings Provider
        final RESTServerSettingsProvider serverSettingsProvider = new RESTServerSettingsProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTServerSettingsProvider.class, serverSettingsProvider);
        providerMap.put(ServerSettingsProvider.class, serverSettingsProvider);
    }

    /**
     * Register an external provider with the provider factory.
     *
     * @param providerClass The Class of the Provider.
     * @param <T>           The Provider class type.
     */
    public <T extends RESTDataProvider> void registerProvider(Class<T> providerClass) {
        registerProvider(providerClass, null);
    }

    /**
     * Register an external provider with the provider factory.
     *
     * @param providerClass     The Class of the Provider.
     * @param providerInterface The Class that the Provider implements.
     * @param <T>               The Provider class type.
     */
    public <T extends RESTDataProvider> void registerProvider(Class<T> providerClass, Class<?> providerInterface) {
        try {
            final Constructor<T> constructor = providerClass.getConstructor(RESTManager.class, RESTWrapperFactory.class);
            final T instance = constructor.newInstance(getRESTManager(), getWrapperFactory());

            providerMap.put(providerClass, instance);
            if (providerInterface != null) {
                providerMap.put(providerInterface, instance);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T loadProvider(final Class<T> clazz) {
        if (providerMap.containsKey(clazz)) {
            return (T) providerMap.get(clazz);
        } else {
            throw new NoClassDefFoundError("No implementation was found for the " + clazz.getName() + " class.");
        }
    }

    @Override
    public boolean isRollbackSupported() {
        return false;
    }

    @Override
    public void rollback() {
        throw new UnsupportedOperationException("Rollback isn't supported using the REST API.");
    }

    @Override
    public boolean isNotificationsSupported() {
        return false;
    }
}
