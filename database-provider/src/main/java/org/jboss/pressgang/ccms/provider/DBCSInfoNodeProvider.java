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

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.CSInfoNode;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.CSInfoNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBCSInfoNodeProvider extends DBDataProvider implements CSInfoNodeProvider {
    protected DBCSInfoNodeProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public CollectionWrapper<CSInfoNodeWrapper> getCSNodeInfoRevisions(int id, Integer revision, CSNodeWrapper parent) {
        final List<CSInfoNode> revisions = getRevisionList(CSInfoNode.class, id);
        return getWrapperFactory().createCollection(revisions, CSInfoNode.class, revision != null);
    }

    @Override
    public CSInfoNodeWrapper newCSNodeInfo(CSNodeWrapper parent) {
        return getWrapperFactory().create(new CSInfoNode(), false, CSInfoNodeWrapper.class);
    }
}
