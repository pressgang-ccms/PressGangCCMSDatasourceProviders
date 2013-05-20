package org.jboss.pressgang.ccms.wrapper;

public interface LogMessageWrapper {
    String getMessage();
    void setMessage(String message);
    Integer getFlags();
    void setFlags(Integer flags);
    String getUser();
    void setUser(String user);
    Object unwrap();
}
