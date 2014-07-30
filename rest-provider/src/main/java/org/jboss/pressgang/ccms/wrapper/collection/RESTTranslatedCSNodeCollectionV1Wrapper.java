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
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;

public class RESTTranslatedCSNodeCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<TranslatedCSNodeWrapper, RESTTranslatedCSNodeV1, RESTTranslatedCSNodeCollectionV1> implements CollectionWrapper<TranslatedCSNodeWrapper> {

    public RESTTranslatedCSNodeCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedCSNodeCollectionV1 collection, boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTTranslatedCSNodeCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedCSNodeCollectionV1 collection, boolean isRevisionCollection,
            final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, expandedEntityMethods);
    }
}
