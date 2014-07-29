/*
  Copyright 2011-2014 Red Hat

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Date;

import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface BaseContentSpecWrapper<T extends BaseContentSpecWrapper<T>> extends EntityWrapper<T> {
    CollectionWrapper<TagWrapper> getTags();

    void setTags(CollectionWrapper<TagWrapper> tags);

    UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getProperties();

    void setProperties(UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> properties);

    String getTitle();

    String getProduct();

    String getVersion();

    String getLocale();

    void setLocale(String locale);

    Integer getType();

    Date getLastModified();

    String getErrors();

    void setErrors(String errors);

    String getFailed();

    void setFailed(String failed);

    PropertyTagInContentSpecWrapper getProperty(final int propertyId);

    boolean hasTag(final int tagId);
}
