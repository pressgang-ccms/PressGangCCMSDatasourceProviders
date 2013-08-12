package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface LanguageFileProvider {
    LanguageFileWrapper getLanguageFile(int id, Integer revision);

    byte[] getLanguageFileData(int id, Integer revision);

    CollectionWrapper<LanguageFileWrapper> getLanguageFileRevisions(int id, Integer revision);
}
