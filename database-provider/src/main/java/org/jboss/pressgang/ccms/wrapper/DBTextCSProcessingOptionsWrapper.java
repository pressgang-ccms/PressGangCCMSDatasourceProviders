package org.jboss.pressgang.ccms.wrapper;

public class DBTextCSProcessingOptionsWrapper implements TextCSProcessingOptionsWrapper {
    Boolean permissive = null;

    @Override
    public Boolean getStrictTitles() {
        return permissive;
    }

    @Override
    public void setStrictTitles(boolean permissive) {
        this.permissive = permissive;
    }

    @Override
    public Object unwrap() {
        return null;
    }
}
