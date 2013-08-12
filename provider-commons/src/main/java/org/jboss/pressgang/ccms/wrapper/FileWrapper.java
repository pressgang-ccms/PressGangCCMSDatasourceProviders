package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface FileWrapper extends EntityWrapper<FileWrapper> {
    String getDescription();

    void setDescription(String description);

    String getFilePath();

    void setFilePath(String filePath);

    String getFilename();

    void setFilename(String filename);

    boolean isExplodeArchive();

    void setExplodeArchive(boolean explodeArchive);

    UpdateableCollectionWrapper<LanguageFileWrapper> getLanguageFiles();

    void setLanguageFiles(UpdateableCollectionWrapper<LanguageFileWrapper> languageFiles);
}
