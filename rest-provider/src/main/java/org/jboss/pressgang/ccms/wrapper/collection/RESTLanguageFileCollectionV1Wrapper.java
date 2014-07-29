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
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;
import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;

public class RESTLanguageFileCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<LanguageFileWrapper, RESTLanguageFileV1,
        RESTLanguageFileCollectionV1> implements UpdateableCollectionWrapper<LanguageFileWrapper> {

    public RESTLanguageFileCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileCollectionV1 collection,
            boolean isRevisionCollection, final RESTFileV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTLanguageFileCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileCollectionV1 collection,
            boolean isRevisionCollection, final RESTFileV1 parent, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, expandedEntityMethods);
    }
}
