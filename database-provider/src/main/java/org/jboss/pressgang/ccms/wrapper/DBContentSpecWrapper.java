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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCSBookTagCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCSNodeCollectionHandler;

public class DBContentSpecWrapper extends DBBaseContentSpecWrapper<ContentSpecWrapper> implements ContentSpecWrapper {
    private final DBCSNodeCollectionHandler csNodeCollectionHandler;
    private final DBCSBookTagCollectionHandler bookTagCollectionHandler;

    public DBContentSpecWrapper(final DBProviderFactory providerFactory, final ContentSpec contentSpec, boolean isRevision) {
        super(providerFactory, contentSpec, isRevision);
        csNodeCollectionHandler = new DBCSNodeCollectionHandler(contentSpec);
        bookTagCollectionHandler = new DBCSBookTagCollectionHandler(contentSpec);
    }

    @Override
    public CollectionWrapper<TagWrapper> getBookTags() {
        return getWrapperFactory().createCollection(getEntity().getBookTags(), Tag.class, isRevisionEntity(), bookTagCollectionHandler);
    }

    @Override
    public void setBookTags(CollectionWrapper<TagWrapper> bookTags) {
        if (bookTags == null) return;
        final DBTagCollectionWrapper dbTags = (DBTagCollectionWrapper) bookTags;
        dbTags.setHandler(bookTagCollectionHandler);

        // Since book tags in a content spec are generated from a set and not cached, there is no way to see if this collection is the same
        // as the collection passed. So just process all the tags anyway.

        // Add new tags and skip any existing tags
        final List<Tag> currentBookTags = getEntity().getBookTags();
        final Collection<Tag> newBookTags = dbTags.unwrap();
        for (final Tag bookTag : newBookTags) {
            if (currentBookTags.contains(bookTag)) {
                currentBookTags.remove(bookTag);
                continue;
            } else {
                getEntity().addBookTag(bookTag);
            }
        }

        // Remove tags that should no longer exist in the collection
        for (final Tag removeBookTag : currentBookTags) {
            getEntity().removeBookTag(removeBookTag);
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getChildren() {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getChildrenList(), CSNode.class,
                isRevisionEntity(), csNodeCollectionHandler);
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    @Override
    public void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes) {
        if (nodes == null) return;
        final DBCSNodeCollectionWrapper dbNodes = (DBCSNodeCollectionWrapper) nodes;
        dbNodes.setHandler(csNodeCollectionHandler);

        // Since children nodes in a content spec are generated from a set and not cached, there is no way to see if this collection is
        // the same as the collection passed. So just process all the nodes anyway.

        // Add new tags and skip any existing tags
        final Set<CSNode> currentChildren = getEntity().getChildren();
        final Collection<CSNode> newChildren = dbNodes.unwrap();
        for (final CSNode child : newChildren) {
            if (currentChildren.contains(child)) {
                currentChildren.remove(child);
                continue;
            } else {
                getEntity().addChild(child);
            }
        }

        // Remove tags that should no longer exist in the collection
        for (final CSNode removeChild : currentChildren) {
            getEntity().removeChild(removeChild);
        }
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecs() {
        return getWrapperFactory().createCollection(getEntity().getTranslatedContentSpecs(getEntityManager(), getRevision()),
                TranslatedContentSpec.class, isRevisionEntity());
    }

    @Override
    public void setType(Integer typeId) {
        getEntity().setContentSpecType(typeId);
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
    public CSNodeWrapper getMetaData(String metaDataTitle) {
        return getWrapperFactory().create(getEntity().getMetaData(metaDataTitle), isRevisionEntity());
    }
}
