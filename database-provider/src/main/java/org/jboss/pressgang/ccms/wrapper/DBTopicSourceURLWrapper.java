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

import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBTopicSourceURLWrapper extends DBBaseEntityWrapper<TopicSourceURLWrapper, TopicSourceUrl> implements TopicSourceURLWrapper {
    private final TopicSourceUrl topicSourceUrl;

    public DBTopicSourceURLWrapper(final DBProviderFactory providerFactory, final TopicSourceUrl topicSourceUrl, boolean isRevision) {
        super(providerFactory, isRevision, TopicSourceUrl.class);
        this.topicSourceUrl = topicSourceUrl;
    }

    @Override
    protected TopicSourceUrl getEntity() {
        return topicSourceUrl;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTopicSourceUrlId(id);
    }

    @Override
    public String getTitle() {
        return getEntity().getTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().setTitle(title);
    }

    @Override
    public String getUrl() {
        return getEntity().getSourceUrl();
    }

    @Override
    public void setUrl(String url) {
        getEntity().setSourceUrl(url);
    }

    @Override
    public String getDescription() {
        return getEntity().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getEntity().setDescription(description);
    }
}
