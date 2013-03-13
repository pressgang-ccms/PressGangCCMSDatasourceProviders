package org.jboss.pressgang.ccms.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;

public class RESTProviderFactory extends DataProviderFactory {
    private final RESTManager restManager;
    private Map<Class<?>, RESTDataProvider> providerMap = new HashMap<Class<?>, RESTDataProvider>();

    public static RESTProviderFactory create(final String serverUrl) {
        return (RESTProviderFactory) DataProviderFactory.create(serverUrl);
    }

    public RESTProviderFactory(final String serverUrl) {
        this.restManager = new RESTManager(serverUrl);
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
        providerMap.put(TagInCategoryProvider.class, tagInCategoryProvider);

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
        providerMap.put(CategoryInTagProvider.class, categoryInTagProvider);

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
        providerMap.put(PropertyTagInTopicProvider.class, propertyTagInTopicProvider);

        // PropertyTag In Tag Provider
        final RESTPropertyTagInTagProvider propertyTagInTagProvider = new RESTPropertyTagInTagProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTPropertyTagInTagProvider.class, propertyTagInTagProvider);
        providerMap.put(PropertyTagInTagProvider.class, propertyTagInTagProvider);

        // PropertyTag In ContentSpec Provider
        final RESTPropertyTagInContentSpecProvider propertyTagInContentSpecProvider = new RESTPropertyTagInContentSpecProvider(
                getRESTManager(), getWrapperFactory());
        providerMap.put(RESTPropertyTagInContentSpecProvider.class, propertyTagInContentSpecProvider);
        providerMap.put(PropertyTagInContentSpecProvider.class, propertyTagInContentSpecProvider);

        // PropertyTag In PropertyCategory Provider
        final RESTPropertyTagInPropertyCategoryProvider propertyTagInPropertyCategoryProvider = new
                RESTPropertyTagInPropertyCategoryProvider(
                getRESTManager(), getWrapperFactory());
        providerMap.put(RESTPropertyTagInPropertyCategoryProvider.class, propertyTagInPropertyCategoryProvider);
        providerMap.put(PropertyTagInPropertyCategoryProvider.class, propertyTagInPropertyCategoryProvider);

        // Content Spec Provider
        final RESTContentSpecProvider contentSpecProvider = new RESTContentSpecProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTContentSpecProvider.class, contentSpecProvider);
        providerMap.put(ContentSpecProvider.class, contentSpecProvider);

        // Content Spec Node Provider
        final RESTCSNodeProvider csNodeProvider = new RESTCSNodeProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTCSNodeProvider.class, csNodeProvider);
        providerMap.put(CSNodeProvider.class, csNodeProvider);

        // Content Spec Related Node Provider
        final RESTCSRelatedNodeProvider csRelatedNodeProvider = new RESTCSRelatedNodeProvider(getRESTManager(), getWrapperFactory());
        providerMap.put(RESTCSRelatedNodeProvider.class, csRelatedNodeProvider);
        providerMap.put(CSRelatedNodeProvider.class, csRelatedNodeProvider);

        // Translated Content Spec Node Provider
        final RESTTranslatedContentSpecProvider translatedContentSpecNodeProvider = new RESTTranslatedContentSpecProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTTranslatedContentSpecProvider.class, translatedContentSpecNodeProvider);
        providerMap.put(TranslatedContentSpecProvider.class, translatedContentSpecNodeProvider);

        // Translated Content Spec Node Provider
        final RESTTranslatedCSNodeProvider csTranslatedNodeProvider = new RESTTranslatedCSNodeProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTTranslatedCSNodeProvider.class, csTranslatedNodeProvider);
        providerMap.put(TranslatedCSNodeProvider.class, csTranslatedNodeProvider);

        // Translated Content Spec Node String Provider
        final RESTTranslatedCSNodeStringProvider csTranslatedNodeStringProvider = new RESTTranslatedCSNodeStringProvider(getRESTManager(),
                getWrapperFactory());
        providerMap.put(RESTTranslatedCSNodeStringProvider.class, csTranslatedNodeStringProvider);
        providerMap.put(TranslatedCSNodeStringProvider.class, csTranslatedNodeStringProvider);
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
        return;
    }
}
