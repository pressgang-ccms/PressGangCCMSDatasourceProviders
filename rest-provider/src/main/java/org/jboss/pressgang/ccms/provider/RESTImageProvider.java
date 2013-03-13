package org.jboss.pressgang.ccms.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTImageProvider extends RESTDataProvider implements ImageProvider {
    private static Logger log = LoggerFactory.getLogger(RESTImageProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTImageProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public ImageWrapper getImage(int id) {
        return getImage(id, null);
    }

    @Override
    public ImageWrapper getImage(int id, Integer revision) {
        try {
            final RESTImageV1 image;
            if (entityCache.containsKeyValue(RESTImageV1.class, id, revision)) {
                image = entityCache.get(RESTImageV1.class, id, revision);
            } else {
                if (revision == null) {
                    image = client.getJSONImage(id, "");
                    entityCache.add(image);
                } else {
                    image = client.getJSONImageRevision(id, revision, "");
                    entityCache.add(image, revision);
                }
            }

            return getWrapperFactory().create(image, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve Image " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getImageLanguageImages(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
    }

    public CollectionWrapper<LanguageImageWrapper> getImageLanguageImages(int id, final Integer revision, final RESTImageV1 parent) {
        try {
            RESTImageV1 image = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTImageV1.class, id, revision)) {
                image = entityCache.get(RESTImageV1.class, id, revision);

                if (image.getLanguageImages_OTM() != null) {
                    return getWrapperFactory().createCollection(image.getLanguageImages_OTM(), RESTLanguageImageV1.class, revision != null,
                            parent);
                }
            }

            // We need to expand the language images in the image collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandLanguageImages = new ExpandDataTrunk(new ExpandDataDetails(RESTImageV1.LANGUAGEIMAGES_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandLanguageImages));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the image from the REST Interface
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
            } else {
                image.setLanguageImages_OTM(tempImage.getLanguageImages_OTM());
            }

            return getWrapperFactory().createCollection(image.getLanguageImages_OTM(), RESTLanguageImageV1.class, revision != null, parent);
        } catch (Exception e) {
            log.error("Failed to retrieve the Language Images for Image " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<ImageWrapper> getImageRevisions(int id, final Integer revision) {
        try {
            RESTImageV1 image = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTImageV1.class, id, revision)) {
                image = entityCache.get(RESTImageV1.class, id, revision);

                if (image.getRevisions() != null) {
                    return getWrapperFactory().createCollection(image.getRevisions(), RESTImageV1.class, true);
                }
            }

            // We need to expand the revisions in the image collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandLanguageImages = new ExpandDataTrunk(new ExpandDataDetails(RESTImageV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandLanguageImages));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the image from the REST Interface
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
            } else {
                image.setRevisions(tempImage.getRevisions());
            }

            return getWrapperFactory().createCollection(image.getRevisions(), RESTImageV1.class, true);
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Image " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }
}
