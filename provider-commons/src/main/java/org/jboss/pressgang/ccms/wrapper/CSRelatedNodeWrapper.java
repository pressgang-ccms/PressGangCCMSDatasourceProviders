package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseCSNodeWrapper;

public interface CSRelatedNodeWrapper extends BaseCSNodeWrapper<CSRelatedNodeWrapper> {
    Integer getRelationshipType();

    void setRelationshipType(Integer typeId);

    Integer getRelationshipId();

    void setRelationshipId(Integer id);

    Integer getRelationshipSort();

    void setRelationshipSort(Integer sort);
}
