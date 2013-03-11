package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface UpdateableCollectionWrapper<T extends EntityWrapper<T>> extends CollectionWrapper<T> {
    public void addUpdateItem(T entity);

    public List<T> getUpdateItems();
}