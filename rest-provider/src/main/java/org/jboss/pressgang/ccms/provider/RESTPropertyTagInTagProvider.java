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

import java.util.Arrays;
import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInTagProvider extends RESTPropertyTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInTagProvider.class);

    protected RESTPropertyTagInTagProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTTagV1 loadTag(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTag(id, expandString);
        } else {
            return getRESTClient().getJSONTagRevision(id, revision, expandString);
        }
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTPropertyTagInTagRevisions(int id, Integer revision,
            final RESTBaseTagV1<?, ?, ?> parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTTagV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTagV1.class, tagId, tagRevision)) {
                tag = getRESTEntityCache().get(RESTTagV1.class, tagId, tagRevision);
            }

            // We need to expand the all property tag revisions in the tag
            final String expandString = getExpansionString(RESTTagV1.PROPERTIES_NAME, RESTAssignedPropertyTagV1.REVISIONS_NAME);

            // Load the tag from the REST Interface
            final RESTTagV1 tempTag = loadTag(id, revision, expandString);

            if (tag == null) {
                tag = tempTag;
                getRESTEntityCache().add(tag, tagRevision);
            } else if (tag.getProperties() == null) {
                tag.setProperties(tempTag.getProperties());
            } else {
                // Iterate over the current and old properties and add any missing objects.
                final List<RESTAssignedPropertyTagV1> properties = tag.getProperties().returnItems();
                final List<RESTAssignedPropertyTagV1> newProperties = tempTag.getProperties().returnItems();
                for (final RESTAssignedPropertyTagV1 newProperty : newProperties) {
                    boolean found = false;

                    for (final RESTAssignedPropertyTagV1 property : properties) {
                        if (property.getId().equals(newProperty.getId())) {
                            property.setRevisions(newProperty.getRevisions());

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        tag.getProperties().addItem(newProperty);
                    }
                }
            }

            for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : tag.getProperties().getItems()) {
                final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

                if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                    return propertyTag.getRevisions();
                }
            }

            return null;
        } catch (Exception e) {
            log.debug("Unable to retrieve the Revisions for PropertyTagInTag " + id + (revision == null ? "" : (", Revision " + revision)),
                    e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<PropertyTagInTagWrapper> getPropertyTagInTagRevisions(int id, Integer revision,
            final RESTBaseTagV1<?, ?, ?> parent) {
        return RESTCollectionWrapperBuilder.<PropertyTagInTagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTPropertyTagInTagRevisions(id, revision, parent))
                .isRevisionCollection()
                .parent(parent)
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .entityWrapperInterface(PropertyTagInTagWrapper.class)
                .build();
    }
}
