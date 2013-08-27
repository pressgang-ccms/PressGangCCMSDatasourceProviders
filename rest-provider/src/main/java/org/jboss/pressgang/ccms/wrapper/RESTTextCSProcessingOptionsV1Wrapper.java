package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextCSProcessingOptionsV1;

public class RESTTextCSProcessingOptionsV1Wrapper implements TextCSProcessingOptionsWrapper {
    final RESTTextCSProcessingOptionsV1 processingOptions;

    public RESTTextCSProcessingOptionsV1Wrapper(final RESTTextCSProcessingOptionsV1 processingOptions) {
        this.processingOptions = processingOptions;
    }

    @Override
    public Boolean getStrictTitles() {
        return processingOptions.getStrictTitles();
    }

    @Override
    public void setStrictTitles(boolean strictTitles) {
        processingOptions.setStrictTitles(strictTitles);
    }

    @Override
    public RESTTextCSProcessingOptionsV1 unwrap() {
        return processingOptions;
    }
}
