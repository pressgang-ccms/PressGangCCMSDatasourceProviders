package org.jboss.pressgang.ccms.wrapper.base;

public interface BaseCSNodeWrapper<T extends BaseCSNodeWrapper<T>> extends EntityWrapper<T> {
    String getTitle();

    void setTitle(String title);

    String getTargetId();

    void setTargetId(String targetId);

    String getAdditionalText();

    void setAdditionalText(String additionalText);

    String getCondition();

    void setCondition(String condition);

    Integer getEntityId();

    void setEntityId(Integer id);

    Integer getEntityRevision();

    void setEntityRevision(Integer revision);

    Integer getNodeType();

    void setNodeType(Integer typeId);
}
