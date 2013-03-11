package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface LanguageImageWrapper extends EntityWrapper<LanguageImageWrapper> {
    String getFilename();

    String getLocale();

    byte[] getImageData();

    byte[] getImageDataBase64();

    byte[] getThumbnail();
}
