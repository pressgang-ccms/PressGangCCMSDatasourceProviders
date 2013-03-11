package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTImageProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTImageV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTImageV1> {
    public RESTImageV1ProxyHandler(RESTProviderFactory providerFactory, RESTImageV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTImageProvider getProvider() {
        return getProviderFactory().getProvider(RESTImageProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTImageV1 image = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (image.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(image, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getLanguageImages_OTM")) {
                    final CollectionWrapper<LanguageImageWrapper> languageImages = getProvider().getImageLanguageImages(image.getId(),
                            getEntityRevision(), getProxyEntity());
                    retValue = languageImages == null ? null : languageImages.unwrap();
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<ImageWrapper> revisions = getProvider().getImageRevisions(image.getId(), getEntityRevision());
                    retValue = revisions == null ? null : revisions.unwrap();
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
                return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue, getEntityRevision() != null,
                        getProxyEntity());
            } else {
                return retValue;
            }
        }

        return super.invoke(self, thisMethod, proceed, args);
    }
}
