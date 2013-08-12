package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.FileWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface FileProvider {
    FileWrapper getFile(int id);

    FileWrapper getFile(int id, Integer revision);

    CollectionWrapper<FileWrapper> getFileRevisions(int id, Integer revision);
}
