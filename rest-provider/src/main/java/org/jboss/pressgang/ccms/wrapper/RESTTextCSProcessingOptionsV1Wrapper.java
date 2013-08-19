package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextCSProcessingOptionsV1;

public class RESTTextCSProcessingOptionsV1Wrapper implements TextCSProcessingOptionsWrapper {
    final RESTTextCSProcessingOptionsV1 processingOptions;

    public RESTTextCSProcessingOptionsV1Wrapper(final RESTTextCSProcessingOptionsV1 processingOptions) {
        this.processingOptions = processingOptions;
    }

    @Override
    public Boolean getPermissive() {
        return processingOptions.getPermissive();
    }

    @Override
    public void setPermissive(boolean permissive) {
        processingOptions.setPermissive(permissive);
    }

    @Override
    public RESTTextCSProcessingOptionsV1 unwrap() {
        return processingOptions;
    }
}
