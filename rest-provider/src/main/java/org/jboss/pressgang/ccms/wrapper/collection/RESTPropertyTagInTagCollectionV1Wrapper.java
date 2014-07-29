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

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;

public class RESTPropertyTagInTagCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<PropertyTagInTagWrapper,
        RESTAssignedPropertyTagV1, RESTAssignedPropertyTagCollectionV1> implements UpdateableCollectionWrapper<PropertyTagInTagWrapper> {

    public RESTPropertyTagInTagCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTAssignedPropertyTagCollectionV1 collection, boolean isRevisionCollection, final RESTBaseTagV1<?, ?, ?> parent) {
        super(providerFactory, collection, isRevisionCollection, parent, PropertyTagInTagWrapper.class);
    }

    public RESTPropertyTagInTagCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTAssignedPropertyTagCollectionV1 collection, boolean isRevisionCollection, final RESTBaseTagV1<?, ?, ?> parent,
            final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, PropertyTagInTagWrapper.class, expandedEntityMethods);
    }
}
