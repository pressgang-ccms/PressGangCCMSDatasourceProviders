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

package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ContentSpecProvider {
    ContentSpecWrapper getContentSpec(int id);

    ContentSpecWrapper getContentSpec(int id, Integer revision);

    CollectionWrapper<ContentSpecWrapper> getContentSpecsWithQuery(String query);

    CollectionWrapper<TagWrapper> getContentSpecTags(int id, Integer revision);

    UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getContentSpecProperties(int id, Integer revision,
            ContentSpecWrapper parent);

    UpdateableCollectionWrapper<CSNodeWrapper> getContentSpecNodes(int id, Integer revision);

    CollectionWrapper<TranslatedContentSpecWrapper> getContentSpecTranslations(int id, Integer revision);

    CollectionWrapper<ContentSpecWrapper> getContentSpecRevisions(int id, Integer revision);

    String getContentSpecAsString(int id);

    String getContentSpecAsString(int id, Integer revision);

    ContentSpecWrapper createContentSpec(ContentSpecWrapper contentSpec);

    ContentSpecWrapper createContentSpec(ContentSpecWrapper contentSpec, LogMessageWrapper logMessage);

    ContentSpecWrapper updateContentSpec(ContentSpecWrapper contentSpec);

    ContentSpecWrapper updateContentSpec(ContentSpecWrapper contentSpec, LogMessageWrapper logMessage);

    boolean deleteContentSpec(Integer id);

    boolean deleteContentSpec(Integer id, LogMessageWrapper logMessage);

    ContentSpecWrapper newContentSpec();

    CollectionWrapper<ContentSpecWrapper> newContentSpecCollection();
}
