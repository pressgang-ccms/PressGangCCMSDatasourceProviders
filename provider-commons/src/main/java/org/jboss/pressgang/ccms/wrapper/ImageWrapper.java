package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ImageWrapper extends EntityWrapper<ImageWrapper> {
    String getDescription();

    UpdateableCollectionWrapper<LanguageImageWrapper> getLanguageImages();

    void setLanguageImages(UpdateableCollectionWrapper<LanguageImageWrapper> languageImages);
}
