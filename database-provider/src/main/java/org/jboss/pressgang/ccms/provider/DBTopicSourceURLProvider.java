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
import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTopicSourceURLProvider extends DBDataProvider implements TopicSourceURLProvider {

    protected DBTopicSourceURLProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceURLRevisions(int id, Integer revision) {
        final List<TopicSourceUrl> revisions = getRevisionList(TopicSourceUrl.class, id);
        return getWrapperFactory().createCollection(revisions, TopicSourceUrl.class, revision != null);
    }

    @Override
    public TopicSourceURLWrapper newTopicSourceURL(TopicWrapper parent) {
        return getWrapperFactory().create(new TopicSourceUrl(), false);
    }

    @Override
    public UpdateableCollectionWrapper<TopicSourceURLWrapper> newTopicSourceURLCollection(TopicWrapper parent) {
        final CollectionWrapper<TopicSourceURLWrapper> collection = getWrapperFactory().createCollection(new ArrayList<TopicSourceUrl>(),
                TopicSourceUrl.class, false);
        return (UpdateableCollectionWrapper<TopicSourceURLWrapper>) collection;
    }
}
