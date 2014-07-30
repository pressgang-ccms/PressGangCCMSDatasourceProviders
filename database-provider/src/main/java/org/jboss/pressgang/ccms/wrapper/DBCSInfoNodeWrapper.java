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

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.contentspec.CSInfoNode;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBCSInfoNodeWrapper extends DBBaseEntityWrapper<CSInfoNodeWrapper, CSInfoNode> implements CSInfoNodeWrapper {
    private final CSInfoNode csInfoNode;

    public DBCSInfoNodeWrapper(final DBProviderFactory providerFactory, final CSInfoNode csInfoNode, boolean isRevision) {
        super(providerFactory, isRevision, CSInfoNode.class);
        this.csInfoNode = csInfoNode;
    }

    @Override
    protected CSInfoNode getEntity() {
        return csInfoNode;
    }

    @Override
    public String getCondition() {
        return getEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().setCondition(condition);
    }

    @Override
    public Integer getTopicId() {
        return getEntity().getTopicId();
    }

    @Override
    public void setTopicId(Integer id) {
        getEntity().setTopicId(id);
    }

    @Override
    public Integer getTopicRevision() {
        return getEntity().getTopicRevision();
    }

    @Override
    public void setTopicRevision(Integer revision) {
        getEntity().setTopicRevision(revision);
    }

    @Override
    public String getInheritedCondition() {
        return getEntity().getInheritedCondition();
    }

    @Override
    public TopicWrapper getTopic() {
        return getWrapperFactory().create(getEntity().getTopic(getEntityManager()), isRevisionEntity(), TopicWrapper.class);
    }

    @Override
    public void setId(Integer id) {
        getEntity().setCSNodeInfoId(id);
    }
}
