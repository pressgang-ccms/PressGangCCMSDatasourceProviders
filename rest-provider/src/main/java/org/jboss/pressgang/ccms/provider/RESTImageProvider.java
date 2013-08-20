package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTImageProvider extends RESTDataProvider implements ImageProvider {
    private static Logger log = LoggerFactory.getLogger(RESTImageProvider.class);

    protected RESTImageProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    protected RESTImageV1 loadImage(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONImage(id, expandString);
        } else {
            return getRESTClient().getJSONImageRevision(id, revision, expandString);
        }
    }

    public RESTImageV1 getRESTImage(int id) {
        return getRESTImage(id, null);
    }

    @Override
    public ImageWrapper getImage(int id) {
        return getImage(id, null);
    }

    public RESTImageV1 getRESTImage(int id, Integer revision) {
        try {
            final RESTImageV1 image;
            if (getRESTEntityCache().containsKeyValue(RESTImageV1.class, id, revision)) {
                image = getRESTEntityCache().get(RESTImageV1.class, id, revision);
            } else {
                image = loadImage(id, revision, "");
                getRESTEntityCache().add(image, revision);
            }

            return image;
        } catch (Exception e) {
            log.debug("Failed to retrieve Image " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public ImageWrapper getImage(int id, Integer revision) {
        return getWrapperFactory().create(getRESTImage(id, revision), revision != null);
    }

    public RESTLanguageImageCollectionV1 getRESTImageLanguageImages(int id, final Integer revision) {
        try {
            RESTImageV1 image = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTImageV1.class, id, revision)) {
                image = getRESTEntityCache().get(RESTImageV1.class, id, revision);

                if (image.getLanguageImages_OTM() != null) {
                    return image.getLanguageImages_OTM();
                }
            }

            // We need to expand the language images in the image collection
            final String expandString = getExpansionString(RESTImageV1.LANGUAGEIMAGES_NAME);

            // Load the image from the REST Interface
            final RESTImageV1 tempImage = loadImage(id, revision, expandString);

            if (image == null) {
                image = tempImage;
                getRESTEntityCache().add(image, revision);
            } else {
                image.setLanguageImages_OTM(tempImage.getLanguageImages_OTM());
            }
            getRESTEntityCache().add(image.getLanguageImages_OTM(), revision != null);

            return image.getLanguageImages_OTM();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Language Images for Image " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<LanguageImageWrapper> getImageLanguageImages(int id, final Integer revision, final RESTImageV1 parent) {
        return getWrapperFactory().createCollection(getRESTImageLanguageImages(id, revision), RESTLanguageImageV1.class,
                revision != null, parent);
    }

    public RESTImageCollectionV1 getRESTImageRevisions(int id, final Integer revision) {
        try {
            RESTImageV1 image = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTImageV1.class, id, revision)) {
                image = getRESTEntityCache().get(RESTImageV1.class, id, revision);

                if (image.getRevisions() != null) {
                    return image.getRevisions();
                }
            }

            // We need to expand the revisions in the image collection
            final String expandString = getExpansionString(RESTImageV1.REVISIONS_NAME);

            // Load the image from the REST Interface
            final RESTImageV1 tempImage = loadImage(id, revision, expandString);

            if (image == null) {
                image = tempImage;
                getRESTEntityCache().add(image, revision);
            } else {
                image.setRevisions(tempImage.getRevisions());
            }

            return image.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Image " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<ImageWrapper> getImageRevisions(int id, final Integer revision) {
        return getWrapperFactory().createCollection(getRESTImageRevisions(id, revision), RESTImageV1.class, true);
    }
}
