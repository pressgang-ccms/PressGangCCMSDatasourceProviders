package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.TextCSProcessingOptionsWrapper;
import org.jboss.pressgang.ccms.wrapper.TextContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface TextContentSpecProvider {
    TextContentSpecWrapper getTextContentSpec(int id);

    TextContentSpecWrapper getTextContentSpec(int id, Integer revision);

    CollectionWrapper<TextContentSpecWrapper> getTextContentSpecsWithQuery(String query);

    CollectionWrapper<TextContentSpecWrapper> getTextContentSpecRevisions(int id, Integer revision);

    TextContentSpecWrapper createTextContentSpec(TextContentSpecWrapper contentSpec);

    TextContentSpecWrapper createTextContentSpec(TextContentSpecWrapper contentSpec, TextCSProcessingOptionsWrapper processingOptions);

    TextContentSpecWrapper createTextContentSpec(TextContentSpecWrapper contentSpec, TextCSProcessingOptionsWrapper processingOptions, LogMessageWrapper logMessage);

    TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpec);

    TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpec, TextCSProcessingOptionsWrapper processingOptions);

    TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpec, TextCSProcessingOptionsWrapper processingOptions, LogMessageWrapper logMessage);

    TextContentSpecWrapper newTextContentSpec();

    TextCSProcessingOptionsWrapper newTextProcessingOptions();
}
