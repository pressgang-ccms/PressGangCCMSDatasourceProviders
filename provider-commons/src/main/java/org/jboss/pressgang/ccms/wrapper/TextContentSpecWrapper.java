package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseContentSpecWrapper;

public interface TextContentSpecWrapper extends BaseContentSpecWrapper<TextContentSpecWrapper> {
    String getText();

    void setText(String text);

    String getErrors();

    void setErrors(String errors);

    String getFailed();
}
