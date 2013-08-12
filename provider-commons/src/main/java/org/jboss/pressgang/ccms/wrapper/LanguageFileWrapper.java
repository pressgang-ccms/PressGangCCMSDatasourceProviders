package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface LanguageFileWrapper extends EntityWrapper<LanguageFileWrapper> {
    String getOriginalFilename();

    void setOriginalFilename(String filename);

    String getLocale();

    void setLocale(String locale);

    byte[] getFileData();

    void setFileData(byte[] fileData);
}
