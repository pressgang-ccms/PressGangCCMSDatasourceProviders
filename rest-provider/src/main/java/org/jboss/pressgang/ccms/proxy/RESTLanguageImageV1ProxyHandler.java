package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTLanguageImageProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTLanguageImageV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTLanguageImageV1> {
    private final RESTImageV1 parent;

    public RESTLanguageImageV1ProxyHandler(RESTProviderFactory providerFactory, RESTLanguageImageV1 entity, boolean isRevisionEntity,
            RESTImageV1 parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    protected RESTLanguageImageProvider getProvider() {
        return getProviderFactory().getProvider(RESTLanguageImageProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTLanguageImageV1 languageImage = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (languageImage.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(languageImage, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getImageData")) {
                    retValue = getProvider().getLanguageImageData(languageImage.getId(), getEntityRevision(), parent);
                } else if (methodName.equals("getImageDataBase64")) {
                    retValue = getProvider().getLanguageImageDataBase64(languageImage.getId(), getEntityRevision(), parent);
                } else if (methodName.equals("getThumbnail")) {
                    retValue = getProvider().getLanguageImageThumbnail(languageImage.getId(), getEntityRevision(), parent);
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<LanguageImageWrapper> revisions = getProvider().getLanguageImageRevisions(languageImage.getId(),
                            getEntityRevision(), parent);
                    retValue = revisions == null ? null : revisions.unwrap();
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
                return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue, getEntityRevision() != null,
                        parent);
            } else {
                return retValue;
            }
        }

        return super.invoke(self, thisMethod, proceed, args);
    }
}
