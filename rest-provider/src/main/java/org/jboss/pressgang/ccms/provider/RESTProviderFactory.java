/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.pressgang.ccms.rest.RESTErrorInterceptor;
import org.jboss.pressgang.ccms.rest.RESTManager;

public class RESTProviderFactory extends DataProviderFactory {
    private final RESTManager restManager;
    private Map<Class<?>, RESTDataProvider> providerMap = new HashMap<Class<?>, RESTDataProvider>();

    public static RESTProviderFactory create(final String serverUrl) {
        return new RESTProviderFactory(serverUrl);
    }

    public RESTProviderFactory(final String serverUrl) {
        restManager = new RESTManager(serverUrl);
        restManager.getProxyFactory().getProviderFactory().addClientErrorInterceptor(new RESTErrorInterceptor());
        initialiseProviders();
    }

    public RESTManager getRESTManager() {
        return restManager;
    }

    /**
     * Initialise all the entity data providers available.
     */
    private void initialiseProviders() {
        // Topic Provider
        final RESTTopicProvider topicProvider = new RESTTopicProvider(this);
        providerMap.put(RESTTopicProvider.class, topicProvider);
        providerMap.put(TopicProvider.class, topicProvider);

        // Tag Provider
        final RESTTagProvider tagProvider = new RESTTagProvider(this);
        providerMap.put(RESTTagProvider.class, tagProvider);
        providerMap.put(TagProvider.class, tagProvider);

        // Tag In Category Provider
        final RESTTagInCategoryProvider tagInCategoryProvider = new RESTTagInCategoryProvider(this);
        providerMap.put(RESTTagInCategoryProvider.class, tagInCategoryProvider);

        // Translated Topic Provider
        final RESTTranslatedTopicProvider translatedTopicProvider = new RESTTranslatedTopicProvider(this);
        providerMap.put(RESTTranslatedTopicProvider.class, translatedTopicProvider);
        providerMap.put(TranslatedTopicProvider.class, translatedTopicProvider);

        // Translated Topic String Provider
        final RESTTranslatedTopicStringProvider translatedTopicStringProvider = new RESTTranslatedTopicStringProvider(this);
        providerMap.put(RESTTranslatedTopicStringProvider.class, translatedTopicStringProvider);
        providerMap.put(TranslatedTopicStringProvider.class, translatedTopicStringProvider);

        // User Provider
        final RESTUserProvider userProvider = new RESTUserProvider(this);
        providerMap.put(RESTUserProvider.class, userProvider);
        providerMap.put(UserProvider.class, userProvider);

        // String Constant Provider
        final RESTStringConstantProvider stringConstantProvider = new RESTStringConstantProvider(this);
        providerMap.put(RESTStringConstantProvider.class, stringConstantProvider);
        providerMap.put(StringConstantProvider.class, stringConstantProvider);

        // Blob Constant Provider
        final RESTBlobConstantProvider blobConstantProvider = new RESTBlobConstantProvider(this);
        providerMap.put(RESTBlobConstantProvider.class, blobConstantProvider);
        providerMap.put(BlobConstantProvider.class, blobConstantProvider);

        // File Provider
        final RESTFileProvider fileProvider = new RESTFileProvider(this);
        providerMap.put(RESTFileProvider.class, fileProvider);
        providerMap.put(FileProvider.class, fileProvider);

        // Language File Provider
        final RESTLanguageFileProvider languageFileProvider = new RESTLanguageFileProvider(this);
        providerMap.put(RESTLanguageFileProvider.class, languageFileProvider);
        providerMap.put(LanguageFileProvider.class, languageFileProvider);

        // Image Provider
        final RESTImageProvider imageProvider = new RESTImageProvider(this);
        providerMap.put(RESTImageProvider.class, imageProvider);
        providerMap.put(ImageProvider.class, imageProvider);

        // Language Image Provider
        final RESTLanguageImageProvider languageImageProvider = new RESTLanguageImageProvider(this);
        providerMap.put(RESTLanguageImageProvider.class, languageImageProvider);
        providerMap.put(LanguageImageProvider.class, languageImageProvider);

        // Category Provider
        final RESTCategoryProvider categoryProvider = new RESTCategoryProvider(this);
        providerMap.put(RESTCategoryProvider.class, categoryProvider);
        providerMap.put(CategoryProvider.class, categoryProvider);

        // Category In Tag Provider
        final RESTCategoryInTagProvider categoryInTagProvider = new RESTCategoryInTagProvider(this);
        providerMap.put(RESTCategoryInTagProvider.class, categoryInTagProvider);

        // Topic Source URL Provider
        final RESTTopicSourceURLProvider topicSourceURLProvider = new RESTTopicSourceURLProvider(this);
        providerMap.put(RESTTopicSourceURLProvider.class, topicSourceURLProvider);
        providerMap.put(TopicSourceURLProvider.class, topicSourceURLProvider);

        // PropertyTag Provider
        final RESTPropertyTagProvider propertyTagProvider = new RESTPropertyTagProvider(this);
        providerMap.put(RESTPropertyTagProvider.class, propertyTagProvider);
        providerMap.put(PropertyTagProvider.class, propertyTagProvider);

        // PropertyTag In Topic Provider
        final RESTPropertyTagInTopicProvider propertyTagInTopicProvider = new RESTPropertyTagInTopicProvider(this);
        providerMap.put(RESTPropertyTagInTopicProvider.class, propertyTagInTopicProvider);

        // PropertyTag In Tag Provider
        final RESTPropertyTagInTagProvider propertyTagInTagProvider = new RESTPropertyTagInTagProvider(this);
        providerMap.put(RESTPropertyTagInTagProvider.class, propertyTagInTagProvider);

        // PropertyTag In ContentSpec Provider
        final RESTPropertyTagInContentSpecProvider propertyTagInContentSpecProvider = new RESTPropertyTagInContentSpecProvider(this);
        providerMap.put(RESTPropertyTagInContentSpecProvider.class, propertyTagInContentSpecProvider);

        // PropertyTag In PropertyCategory Provider

        final RESTPropertyTagInPropertyCategoryProvider propertyTagInPropertyCategoryProvider = new
                RESTPropertyTagInPropertyCategoryProvider(this);
        providerMap.put(RESTPropertyTagInPropertyCategoryProvider.class, propertyTagInPropertyCategoryProvider);

        // Content Spec Provider
        final RESTContentSpecProvider contentSpecProvider = new RESTContentSpecProvider(this);
        providerMap.put(RESTContentSpecProvider.class, contentSpecProvider);
        providerMap.put(ContentSpecProvider.class, contentSpecProvider);

        // Text Content Spec Provider
        final RESTTextContentSpecProvider textContentSpecProvider = new RESTTextContentSpecProvider(this);
        providerMap.put(RESTTextContentSpecProvider.class, textContentSpecProvider);
        providerMap.put(TextContentSpecProvider.class, textContentSpecProvider);

        // Content Spec Node Provider
        final RESTCSNodeProvider csNodeProvider = new RESTCSNodeProvider(this);
        providerMap.put(RESTCSNodeProvider.class, csNodeProvider);
        providerMap.put(CSNodeProvider.class, csNodeProvider);

        // Content Spec Related Node Provider
        final RESTCSRelatedNodeProvider csRelatedNodeProvider = new RESTCSRelatedNodeProvider(this);
        providerMap.put(RESTCSRelatedNodeProvider.class, csRelatedNodeProvider);

        // Content Spec Node Info Provider
        final RESTCSInfoNodeProvider csNodeInfoProvider = new RESTCSInfoNodeProvider(this);
        providerMap.put(RESTCSInfoNodeProvider.class, csNodeInfoProvider);
        providerMap.put(CSInfoNodeProvider.class, csNodeInfoProvider);

        // Translated Content Spec Provider
        final RESTTranslatedContentSpecProvider translatedContentSpecProvider = new RESTTranslatedContentSpecProvider(this);
        providerMap.put(RESTTranslatedContentSpecProvider.class, translatedContentSpecProvider);
        providerMap.put(TranslatedContentSpecProvider.class, translatedContentSpecProvider);

        // Translated Content Spec Node Provider
        final RESTTranslatedCSNodeProvider translatedCSNodeProvider = new RESTTranslatedCSNodeProvider(this);
        providerMap.put(RESTTranslatedCSNodeProvider.class, translatedCSNodeProvider);
        providerMap.put(TranslatedCSNodeProvider.class, translatedCSNodeProvider);

        // Translated Content Spec Node String Provider
        final RESTTranslatedCSNodeStringProvider translatedCSNodeStringProvider = new RESTTranslatedCSNodeStringProvider(this);
        providerMap.put(RESTTranslatedCSNodeStringProvider.class, translatedCSNodeStringProvider);
        providerMap.put(TranslatedCSNodeStringProvider.class, translatedCSNodeStringProvider);

        // Log Message Provider
        final RESTLogMessageProvider logMessageProvider = new RESTLogMessageProvider(this);
        providerMap.put(RESTLogMessageProvider.class, logMessageProvider);
        providerMap.put(LogMessageProvider.class, logMessageProvider);

        // Server Settings Provider
        final RESTServerSettingsProvider serverSettingsProvider = new RESTServerSettingsProvider(this);
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
            final Constructor<T> constructor = providerClass.getConstructor(RESTProviderFactory.class);
            final T instance = constructor.newInstance(this);

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
    public boolean isTransactionsSupported() {
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
