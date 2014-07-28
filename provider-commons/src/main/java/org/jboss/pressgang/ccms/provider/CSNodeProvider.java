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

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface CSNodeProvider {
    CSNodeWrapper getCSNode(int id);

    CSNodeWrapper getCSNode(int id, Integer revision);

    CollectionWrapper<CSNodeWrapper> getCSNodesWithQuery(String query);

    CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedToNodes(int id, Integer revision);

    CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedFromNodes(int id, Integer revision);

    UpdateableCollectionWrapper<CSNodeWrapper> getCSNodeChildren(int id, Integer revision);

    CollectionWrapper<CSNodeWrapper> getCSNodeRevisions(int id, Integer revision);

    CSNodeWrapper newCSNode();

    UpdateableCollectionWrapper<CSNodeWrapper> newCSNodeCollection();

    CSRelatedNodeWrapper newCSRelatedNode();

    CSRelatedNodeWrapper newCSRelatedNode(final CSNodeWrapper node);

    UpdateableCollectionWrapper<CSRelatedNodeWrapper> newCSRelatedNodeCollection();

    CSNodeWrapper createCSNode(CSNodeWrapper nodeEntity);

    CSNodeWrapper updateCSNode(CSNodeWrapper nodeEntity);

    boolean deleteCSNode(int id);

    UpdateableCollectionWrapper<CSNodeWrapper> createCSNodes(final UpdateableCollectionWrapper<CSNodeWrapper> csNodes);

    UpdateableCollectionWrapper<CSNodeWrapper> updateCSNodes(final UpdateableCollectionWrapper<CSNodeWrapper> csNodes);

    boolean deleteCSNodes(final List<Integer> csNodeIds);
}
