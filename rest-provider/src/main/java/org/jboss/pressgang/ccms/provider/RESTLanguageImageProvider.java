package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTImageV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.items.RESTLanguageImageCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTLanguageImageProvider extends RESTDataProvider implements LanguageImageProvider {
    private static Logger log = LoggerFactory.getLogger(RESTLanguageImageProvider.class);

    protected RESTLanguageImageProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    protected RESTImageV1 loadImage(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONImage(id, expandString);
        } else {
            return getRESTClient().getJSONImageRevision(id, revision, expandString);
        }
    }

    @Override
    public LanguageImageWrapper getLanguageImage(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public RESTLanguageImageV1 getRESTLanguageImage(int id, final Integer revision, final RESTImageV1 parent) {
        try {
            if (getRESTEntityCache().containsKeyValue(RESTLanguageImageV1.class, id, revision)) {
                return getRESTEntityCache().get(RESTLanguageImageV1.class, id, revision);
            } else {
                final RESTLanguageImageCollectionV1 languageImages = parent.getLanguageImages_OTM();

                final List<RESTLanguageImageV1> languageImageItems = languageImages.returnItems();
                for (final RESTLanguageImageV1 image : languageImageItems) {
                    if (image.getId().equals(id) && (revision == null || image.getRevision().equals(revision))) {
                        return image;
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve Language Image " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public LanguageImageWrapper getLanguageImage(int id, final Integer revision, final RESTImageV1 parent) {
        return getWrapperFactory().create(getRESTLanguageImage(id, revision, parent), revision != null, parent);
    }

    @Override
    public byte[] getLanguageImageData(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public byte[] getLanguageImageData(int id, final Integer revision, final RESTImageV1 parent) {
        final RESTImageV1ProxyHandler imageProxy = (RESTImageV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer imageId = parent.getId();
        final Integer imageRevision = imageProxy.getEntityRevision();

        try {
            final RESTLanguageImageV1 languageImage = getRESTLanguageImage(id, revision, parent);
            if (languageImage == null) {
                throw new NotFoundException();
            } else if (languageImage.getImageData() != null) {
                return languageImage.getImageData();
            } else {
                RESTImageV1 image = null;
                // Check the cache first
                if (getRESTEntityCache().containsKeyValue(RESTImageV1.class, imageId, imageRevision)) {
                    image = getRESTEntityCache().get(RESTImageV1.class, imageId, imageRevision);
                }

                // We need to expand the all the language images with their data in the image
                final String expandString = getExpansionString(RESTImageV1.LANGUAGEIMAGES_NAME, RESTLanguageImageV1.IMAGEDATA_NAME);

                // Load the image from the REST Interface
                final RESTImageV1 tempImage = loadImage(imageId, imageRevision, expandString);

                if (image == null) {
                    image = tempImage;
                    getRESTEntityCache().add(image, imageRevision);
                } else if (image.getLanguageImages_OTM() == null) {
                    image.setLanguageImages_OTM(tempImage.getLanguageImages_OTM());
                } else {
                    /*
                     * Iterate over the current and old language images and add any missing language images. Also add the image
                     * data to any language images that don't have it set.
                     */
                    final List<RESTLanguageImageV1> langImages = image.getLanguageImages_OTM().returnItems();
                    final List<RESTLanguageImageV1> newLangImages = tempImage.getLanguageImages_OTM().returnItems();
                    for (final RESTLanguageImageV1 newLangImage : newLangImages) {
                        boolean found = false;

                        for (final RESTLanguageImageV1 langImage : langImages) {
                            if (langImage.getId().equals(newLangImage.getId())) {
                                langImage.setImageData(newLangImage.getImageData());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            image.getLanguageImages_OTM().addItem(newLangImage);
                        }
                    }
                }

                // Find the matching language image and return the image data
                for (final RESTLanguageImageCollectionItemV1 imageItem : image.getLanguageImages_OTM().getItems()) {
                    final RESTLanguageImageV1 langImage = imageItem.getItem();

                    if (langImage.getId() == id && (revision == null || langImage.getRevision().equals(revision))) {
                        return langImage.getImageData();
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve the Data for Language Image " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public byte[] getLanguageImageDataBase64(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public byte[] getLanguageImageDataBase64(int id, final Integer revision, final RESTImageV1 parent) {
        final RESTImageV1ProxyHandler imageProxy = (RESTImageV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer imageId = parent.getId();
        final Integer imageRevision = imageProxy.getEntityRevision();

        try {
            final RESTLanguageImageV1 languageImage = getRESTLanguageImage(id, revision, parent);
            if (languageImage == null) {
                throw new NotFoundException();
            } else if (languageImage.getImageDataBase64() != null) {
                return languageImage.getImageDataBase64();
            } else {
                RESTImageV1 image = null;
                // Check the cache first
                if (getRESTEntityCache().containsKeyValue(RESTImageV1.class, imageId, imageRevision)) {
                    image = getRESTEntityCache().get(RESTImageV1.class, imageId, imageRevision);
                }

                // We need to expand the all the language images with their BASE64 data in the image
                final String expandString = getExpansionString(RESTImageV1.LANGUAGEIMAGES_NAME, RESTLanguageImageV1.IMAGEDATABASE64_NAME);

                // Load the image from the REST Interface
                final RESTImageV1 tempImage = loadImage(id, revision, expandString);

                if (image == null) {
                    image = tempImage;
                    getRESTEntityCache().add(image, revision);
                } else if (image.getLanguageImages_OTM() == null) {
                    image.setLanguageImages_OTM(tempImage.getLanguageImages_OTM());
                } else {
                    /*
                     * Iterate over the current and old language images and add any missing language images. Also add the image
                     * data to any language images that don't have it set.
                     */
                    final List<RESTLanguageImageV1> langImages = image.getLanguageImages_OTM().returnItems();
                    final List<RESTLanguageImageV1> newLangImages = tempImage.getLanguageImages_OTM().returnItems();
                    for (final RESTLanguageImageV1 newLangImage : newLangImages) {
                        boolean found = false;

                        for (final RESTLanguageImageV1 langImage : langImages) {
                            if (langImage.getId().equals(newLangImage.getId())) {
                                langImage.setImageDataBase64(newLangImage.getImageDataBase64());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            image.getLanguageImages_OTM().addItem(newLangImage);
                        }
                    }
                }

                for (final RESTLanguageImageCollectionItemV1 imageItem : image.getLanguageImages_OTM().getItems()) {
                    final RESTLanguageImageV1 langImage = imageItem.getItem();

                    if (langImage.getId() == id && (revision == null || langImage.getRevision().equals(revision))) {
                        return langImage.getImageDataBase64();
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve the Base64 Data for Language Image " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public byte[] getLanguageImageThumbnail(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public byte[] getLanguageImageThumbnail(int id, final Integer revision, final RESTImageV1 parent) {
        final RESTImageV1ProxyHandler imageProxy = (RESTImageV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer imageId = parent.getId();
        final Integer imageRevision = imageProxy.getEntityRevision();

        try {
            final RESTLanguageImageV1 languageImage = getRESTLanguageImage(id, revision, parent);
            if (languageImage == null) {
                throw new NotFoundException();
            } else if (languageImage.getThumbnail() != null) {
                return languageImage.getThumbnail();
            } else {
                RESTImageV1 image = null;
                // Check the cache first
                if (getRESTEntityCache().containsKeyValue(RESTImageV1.class, imageId, imageRevision)) {
                    image = getRESTEntityCache().get(RESTImageV1.class, imageId, imageRevision);
                }

                // We need to expand the all the language image thumbnails in the image
                final String expandString = getExpansionString(RESTImageV1.LANGUAGEIMAGES_NAME, RESTLanguageImageV1.THUMBNAIL_NAME);

                // Load the image from the REST Interface
                final RESTImageV1 tempImage = loadImage(id, revision, expandString);

                if (image == null) {
                    image = tempImage;
                    getRESTEntityCache().add(image, revision);
                } else if (image.getLanguageImages_OTM() == null) {
                    image.setLanguageImages_OTM(tempImage.getLanguageImages_OTM());
                } else {
                    /*
                     * Iterate over the current and old language images and add any missing language images. Also add the image
                     * data to any language images that don't have it set.
                     */
                    final List<RESTLanguageImageV1> langImages = image.getLanguageImages_OTM().returnItems();
                    final List<RESTLanguageImageV1> newLangImages = tempImage.getLanguageImages_OTM().returnItems();
                    for (final RESTLanguageImageV1 newLangImage : newLangImages) {
                        boolean found = false;

                        for (final RESTLanguageImageV1 langImage : langImages) {
                            if (langImage.getId().equals(newLangImage.getId())) {
                                langImage.setThumbnail(newLangImage.getThumbnail());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            image.getLanguageImages_OTM().addItem(newLangImage);
                        }
                    }
                }

                for (final RESTLanguageImageCollectionItemV1 imageItem : image.getLanguageImages_OTM().getItems()) {
                    final RESTLanguageImageV1 langImage = imageItem.getItem();

                    if (langImage.getId() == id && (revision == null || langImage.getRevision().equals(revision))) {
                        return langImage.getThumbnail();
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve the Thumbnail for Language Image " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getLanguageImageRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public RESTLanguageImageCollectionV1 getRESTLanguageImageRevisions(int id, final Integer revision, final RESTImageV1 parent) {
        final RESTImageV1ProxyHandler imageProxy = (RESTImageV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer imageId = parent.getId();
        final Integer imageRevision = imageProxy.getEntityRevision();

        try {
            final RESTLanguageImageV1 languageImage = getRESTLanguageImage(id, revision, parent);
            if (languageImage == null) {
                throw new NotFoundException();
            } else if (languageImage.getRevisions() != null) {
                return languageImage.getRevisions();
            } else {
                RESTImageV1 image = null;
                // Check the cache first
                if (getRESTEntityCache().containsKeyValue(RESTImageV1.class, imageId, imageRevision)) {
                    image = getRESTEntityCache().get(RESTImageV1.class, imageId, imageRevision);
                }

                // We need to expand the all the language image revisions in the image
                final String expandString = getExpansionString(RESTImageV1.LANGUAGEIMAGES_NAME, RESTLanguageImageV1.REVISIONS_NAME);

                // Load the image from the REST Interface
                final RESTImageV1 tempImage = loadImage(imageId, imageRevision, expandString);

                if (image == null) {
                    image = tempImage;
                    getRESTEntityCache().add(image, imageRevision);
                } else if (image.getLanguageImages_OTM() == null) {
                    image.setLanguageImages_OTM(tempImage.getLanguageImages_OTM());
                } else {
                    /*
                     * Iterate over the current and old language images and add any missing language images. Also add the image
                     * data to any language images that don't have it set.
                     */
                    final List<RESTLanguageImageV1> langImages = image.getLanguageImages_OTM().returnItems();
                    final List<RESTLanguageImageV1> newLangImages = tempImage.getLanguageImages_OTM().returnItems();
                    for (final RESTLanguageImageV1 newLangImage : newLangImages) {
                        boolean found = false;

                        for (final RESTLanguageImageV1 langImage : langImages) {
                            if (langImage.getId().equals(newLangImage.getId())) {
                                langImage.setRevisions(newLangImage.getRevisions());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            image.getLanguageImages_OTM().addItem(newLangImage);
                        }
                    }
                }

                for (final RESTLanguageImageCollectionItemV1 imageItem : image.getLanguageImages_OTM().getItems()) {
                    final RESTLanguageImageV1 langImage = imageItem.getItem();

                    if (langImage.getId() == id && (revision == null || langImage.getRevision().equals(revision))) {
                        return languageImage.getRevisions();
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Language Image " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<LanguageImageWrapper> getLanguageImageRevisions(int id, final Integer revision, final RESTImageV1 parent) {
        return getWrapperFactory().createCollection(getRESTLanguageImageRevisions(id, revision, parent), RESTLanguageImageV1.class,
                revision != null, parent);
    }
}
