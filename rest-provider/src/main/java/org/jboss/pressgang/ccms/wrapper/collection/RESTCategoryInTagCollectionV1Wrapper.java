/*
  Copyright 2011-2014 Red Hat, Inc

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
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;

public class RESTCategoryInTagCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<CategoryInTagWrapper, RESTCategoryInTagV1,
        RESTCategoryInTagCollectionV1> implements UpdateableCollectionWrapper<CategoryInTagWrapper> {

    public RESTCategoryInTagCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryInTagCollectionV1 collection,
            boolean isRevisionCollection, final RESTTagV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTCategoryInTagCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryInTagCollectionV1 collection,
            boolean isRevisionCollection, final RESTTagV1 parent, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, expandedEntityMethods);
    }
}
