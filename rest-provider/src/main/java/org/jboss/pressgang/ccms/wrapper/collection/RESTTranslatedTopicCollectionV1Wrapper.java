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
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;

public class RESTTranslatedTopicCollectionV1Wrapper extends RESTCollectionWrapper<TranslatedTopicWrapper, RESTTranslatedTopicV1,
        RESTTranslatedTopicCollectionV1> implements CollectionWrapper<TranslatedTopicWrapper> {

    public RESTTranslatedTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedTopicCollectionV1 collection, boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTTranslatedTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedTopicCollectionV1 collection, boolean isRevisionCollection, final Collection<String> entityIgnoreMethods) {
        super(providerFactory, collection, isRevisionCollection, entityIgnoreMethods);
    }
}
