package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface ImageWrapper extends EntityWrapper<ImageWrapper> {
    String getDescription();

    CollectionWrapper<LanguageImageWrapper> getLanguageImages();
}
