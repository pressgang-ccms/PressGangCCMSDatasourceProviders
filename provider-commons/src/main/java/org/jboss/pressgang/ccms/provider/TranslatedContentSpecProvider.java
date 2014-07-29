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

package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TranslatedContentSpecProvider {
    TranslatedContentSpecWrapper getTranslatedContentSpec(int id);

    TranslatedContentSpecWrapper getTranslatedContentSpec(int id, Integer revision);

    UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes(int id, Integer revision);

    CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecRevisions(int id, Integer revision);

    CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecsWithQuery(String query);

    TranslatedContentSpecWrapper createTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpec);

    TranslatedContentSpecWrapper updateTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpec);

    CollectionWrapper<TranslatedContentSpecWrapper> createTranslatedContentSpecs(
            CollectionWrapper<TranslatedContentSpecWrapper> translatedNodes);

    TranslatedContentSpecWrapper newTranslatedContentSpec();

    CollectionWrapper<TranslatedContentSpecWrapper> newTranslatedContentSpecCollection();
}
