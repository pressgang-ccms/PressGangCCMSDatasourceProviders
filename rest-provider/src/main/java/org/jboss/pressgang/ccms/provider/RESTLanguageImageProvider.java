package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.proxy.RESTImageV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.items.RESTLanguageImageCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTLanguageImageProvider extends RESTDataProvider implements LanguageImageProvider {
    private static Logger log = LoggerFactory.getLogger(RESTLanguageImageProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTLanguageImageProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public LanguageImageWrapper getLanguageImage(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public RESTLanguageImageV1 getRESTLanguageImage(int id, final Integer revision, final RESTImageV1 parent) {
        try {
            if (entityCache.containsKeyValue(RESTLanguageImageV1.class, id, revision)) {
                return entityCache.get(RESTLanguageImageV1.class, id, revision);
            } else {
                final RESTLanguageImageCollectionV1 languageImages = parent.getLanguageImages_OTM();

                final List<RESTLanguageImageV1> languageImageItems = languageImages.returnItems();
                for (final RESTLanguageImageV1 image : languageImageItems) {
                    if (image.getId().equals(id) && (revision == null || image.getRevision().equals(revision))) {
                        return image;
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
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

        final RESTLanguageImageV1 languageImage = getRESTLanguageImage(id, revision, parent);
        if (languageImage == null) {
            return null;
        } else if (languageImage.getImageData() != null) {
            return languageImage.getImageData();
        } else {
            try {
                RESTImageV1 image = null;
                // Check the cache first
                if (entityCache.containsKeyValue(RESTImageV1.class, imageId, imageRevision)) {
                    image = entityCache.get(RESTImageV1.class, imageId, imageRevision);
                }

                /* We need to expand the all the items in the topic collection */
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandLanguageImages = new ExpandDataTrunk(new ExpandDataDetails(RESTImageV1.LANGUAGEIMAGES_NAME));
                final ExpandDataTrunk expandImageData = new ExpandDataTrunk(new ExpandDataDetails(RESTLanguageImageV1.IMAGEDATA_NAME));

                expandLanguageImages.setBranches(CollectionUtilities.toArrayList(expandImageData));
                expand.setBranches(CollectionUtilities.toArrayList(expandLanguageImages));

                final String expandString = mapper.writeValueAsString(expand);

                final RESTImageV1 tempImage;
                if (imageRevision == null) {
                    tempImage = client.getJSONImage(imageId, expandString);
                } else {
                    tempImage = client.getJSONImageRevision(imageId, imageRevision, expandString);
                }

                if (image == null) {
                    image = tempImage;
                    if (imageRevision == null) {
                        entityCache.add(image);
                    } else {
                        entityCache.add(image, imageRevision);
                    }
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

                for (final RESTLanguageImageCollectionItemV1 imageItem : image.getLanguageImages_OTM().getItems()) {
                    final RESTLanguageImageV1 langImage = imageItem.getItem();

                    if (langImage.getId() == id && (revision == null || langImage.getRevision().equals(revision))) {
                        return langImage.getImageData();
                    }
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    @Override
    public byte[] getLanguageImageDataBase64(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public byte[] getLanguageImageDataBase64(int id, final Integer revision, final RESTImageV1 parent) {
        final RESTImageV1ProxyHandler imageProxy = (RESTImageV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer imageId = parent.getId();
        final Integer imageRevision = imageProxy.getEntityRevision();

        final RESTLanguageImageV1 languageImage = getRESTLanguageImage(id, revision, parent);
        if (languageImage == null) {
            return null;
        } else if (languageImage.getImageDataBase64() != null) {
            return languageImage.getImageDataBase64();
        } else {
            try {
                RESTImageV1 image = null;
                // Check the cache first
                if (entityCache.containsKeyValue(RESTImageV1.class, imageId, imageRevision)) {
                    image = entityCache.get(RESTImageV1.class, imageId, imageRevision);
                }

                /* We need to expand the all the items in the topic collection */
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandLanguageImages = new ExpandDataTrunk(new ExpandDataDetails(RESTImageV1.LANGUAGEIMAGES_NAME));
                final ExpandDataTrunk expandImageDataBase64 = new ExpandDataTrunk(
                        new ExpandDataDetails(RESTLanguageImageV1.IMAGEDATABASE64_NAME));

                expandLanguageImages.setBranches(CollectionUtilities.toArrayList(expandImageDataBase64));
                expand.setBranches(CollectionUtilities.toArrayList(expandLanguageImages));

                final String expandString = mapper.writeValueAsString(expand);

                final RESTImageV1 tempImage;
                if (revision == null) {
                    tempImage = client.getJSONImage(id, expandString);
                } else {
                    tempImage = client.getJSONImageRevision(id, revision, expandString);
                }

                if (image == null) {
                    image = tempImage;
                    if (revision == null) {
                        entityCache.add(image);
                    } else {
                        entityCache.add(image, revision);
                    }
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
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    @Override
    public byte[] getLanguageImageThumbnail(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public byte[] getLanguageImageThumbnail(int id, final Integer revision, final RESTImageV1 parent) {
        final RESTImageV1ProxyHandler imageProxy = (RESTImageV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer imageId = parent.getId();
        final Integer imageRevision = imageProxy.getEntityRevision();

        final RESTLanguageImageV1 languageImage = getRESTLanguageImage(id, revision, parent);
        if (languageImage == null) {
            return null;
        } else if (languageImage.getThumbnail() != null) {
            return languageImage.getThumbnail();
        } else {
            try {
                RESTImageV1 image = null;
                // Check the cache first
                if (entityCache.containsKeyValue(RESTImageV1.class, imageId, imageRevision)) {
                    image = entityCache.get(RESTImageV1.class, imageId, imageRevision);
                }

                /* We need to expand the all the items in the topic collection */
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandLanguageImages = new ExpandDataTrunk(new ExpandDataDetails(RESTImageV1.LANGUAGEIMAGES_NAME));
                final ExpandDataTrunk expandThumbnail = new ExpandDataTrunk(new ExpandDataDetails(RESTLanguageImageV1.THUMBNAIL_NAME));

                expandLanguageImages.setBranches(CollectionUtilities.toArrayList(expandThumbnail));
                expand.setBranches(CollectionUtilities.toArrayList(expandLanguageImages));

                final String expandString = mapper.writeValueAsString(expand);

                final RESTImageV1 tempImage;
                if (revision == null) {
                    tempImage = client.getJSONImage(id, expandString);
                } else {
                    tempImage = client.getJSONImageRevision(id, revision, expandString);
                }

                if (image == null) {
                    image = tempImage;
                    if (revision == null) {
                        entityCache.add(image);
                    } else {
                        entityCache.add(image, revision);
                    }
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
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getLanguageImageRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public RESTLanguageImageCollectionV1 getRESTLanguageImageRevisions(int id, final Integer revision, final RESTImageV1 parent) {
        final RESTImageV1ProxyHandler imageProxy = (RESTImageV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer imageId = parent.getId();
        final Integer imageRevision = imageProxy.getEntityRevision();

        final RESTLanguageImageV1 languageImage = getRESTLanguageImage(id, revision, parent);
        if (languageImage == null) {
            return null;
        } else if (languageImage.getRevisions() != null) {
            return languageImage.getRevisions();
        } else {
            try {
                RESTImageV1 image = null;
                // Check the cache first
                if (entityCache.containsKeyValue(RESTImageV1.class, imageId, imageRevision)) {
                    image = entityCache.get(RESTImageV1.class, imageId, imageRevision);
                }

                // We need to expand the all the items in the language image collection
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandLanguageImages = new ExpandDataTrunk(new ExpandDataDetails(RESTImageV1.LANGUAGEIMAGES_NAME));
                final ExpandDataTrunk expandImageData = new ExpandDataTrunk(new ExpandDataDetails(RESTLanguageImageV1.REVISIONS_NAME));

                expandLanguageImages.setBranches(CollectionUtilities.toArrayList(expandImageData));
                expand.setBranches(CollectionUtilities.toArrayList(expandLanguageImages));

                final String expandString = mapper.writeValueAsString(expand);

                final RESTImageV1 tempImage;
                if (imageRevision == null) {
                    tempImage = client.getJSONImage(imageId, expandString);
                } else {
                    tempImage = client.getJSONImageRevision(imageId, imageRevision, expandString);
                }

                if (image == null) {
                    image = tempImage;
                    if (imageRevision == null) {
                        entityCache.add(image);
                    } else {
                        entityCache.add(image, imageRevision);
                    }
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
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    public CollectionWrapper<LanguageImageWrapper> getLanguageImageRevisions(int id, final Integer revision, final RESTImageV1 parent) {
        return getWrapperFactory().createCollection(getRESTLanguageImageRevisions(id, revision, parent), RESTLanguageImageV1.class,
                revision != null, parent);
    }
}
