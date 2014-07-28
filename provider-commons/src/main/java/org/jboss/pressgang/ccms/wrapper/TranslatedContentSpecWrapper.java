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

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public interface TranslatedContentSpecWrapper extends EntityWrapper<TranslatedContentSpecWrapper> {
    Integer getContentSpecId();

    void setContentSpecId(Integer id);

    Integer getContentSpecRevision();

    void setContentSpecRevision(Integer revision);

    String getZanataId();

    UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes();

    void setTranslatedNodes(UpdateableCollectionWrapper<TranslatedCSNodeWrapper> translatedNodes);

    ContentSpecWrapper getContentSpec();

    void setContentSpec(ContentSpecWrapper contentSpec);

    String getEditorURL(ZanataDetails zanataDetails, String locale);
}
