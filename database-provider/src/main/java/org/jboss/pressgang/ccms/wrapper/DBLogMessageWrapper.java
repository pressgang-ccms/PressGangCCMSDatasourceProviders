package org.jboss.pressgang.ccms.wrapper;

public class DBLogMessageWrapper implements LogMessageWrapper {
    String message = null;
    Integer flags = 0;
    String user = null;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getFlags() {
        return flags;
    }

    @Override
    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public Object unwrap() {
        return null;
    }
}
