/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTPropertyTagInPropertyCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;

public class RESTPropertyTagInPropertyCategoryCollectionV1Wrapper extends
        RESTUpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper, RESTPropertyTagInPropertyCategoryV1,
                RESTPropertyTagInPropertyCategoryCollectionV1> implements
        UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper> {

    public RESTPropertyTagInPropertyCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTPropertyTagInPropertyCategoryCollectionV1 collection, boolean isRevisionCollection,
            final RESTPropertyCategoryV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTPropertyTagInPropertyCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTPropertyTagInPropertyCategoryCollectionV1 collection, boolean isRevisionCollection,
            final RESTPropertyCategoryV1 parent, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, expandedEntityMethods);
    }
}
