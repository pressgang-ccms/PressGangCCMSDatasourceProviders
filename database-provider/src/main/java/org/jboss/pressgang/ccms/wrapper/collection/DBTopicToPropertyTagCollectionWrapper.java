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

package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;

public class DBTopicToPropertyTagCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInTopicWrapper, TopicToPropertyTag> {
    public DBTopicToPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TopicToPropertyTag> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInTopicWrapper.class);
    }
}
