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

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface CSNodeWrapper extends BaseCSNodeWrapper<CSNodeWrapper> {
    UpdateableCollectionWrapper<CSNodeWrapper> getChildren();

    void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes);

    UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedToNodes();

    void setRelatedToNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedToNodes);

    UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedFromNodes();

    void setRelatedFromNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes);

    CSNodeWrapper getParent();

    void setParent(CSNodeWrapper parent);

    ContentSpecWrapper getContentSpec();

    CSNodeWrapper getNextNode();

    void setNextNode(CSNodeWrapper nextNode);

    void setContentSpec(ContentSpecWrapper contentSpec);

    String getInheritedCondition();

    CollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes();

    CSInfoNodeWrapper getInfoTopicNode();

    void setInfoTopicNode(CSInfoNodeWrapper csNodeInfo);

    String getFixedURL();

    void setFixedURL(String fixedURL);
}
