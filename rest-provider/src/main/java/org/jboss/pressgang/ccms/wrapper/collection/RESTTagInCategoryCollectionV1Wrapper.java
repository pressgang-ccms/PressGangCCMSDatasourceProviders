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
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;

public class RESTTagInCategoryCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<TagInCategoryWrapper, RESTTagInCategoryV1,
        RESTTagInCategoryCollectionV1> {

    public RESTTagInCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagInCategoryCollectionV1 collection,
            boolean isRevisionCollection, final RESTCategoryV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTTagInCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagInCategoryCollectionV1 collection,
            boolean isRevisionCollection, final RESTCategoryV1 parent, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, expandedEntityMethods);
    }
}
