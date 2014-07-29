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

import java.util.Arrays;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTStringConstantCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTStringConstantProvider extends RESTDataProvider implements StringConstantProvider {
    private static Logger log = LoggerFactory.getLogger(RESTStringConstantProvider.class);

    public RESTStringConstantProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTStringConstantV1 loadStringConstant(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONStringConstant(id, expandString);
        } else {
            return getRESTClient().getJSONStringConstantRevision(id, revision, expandString);
        }
    }

    public RESTStringConstantV1 getRESTStringConstant(int id) {
        return getRESTStringConstant(id, null);
    }

    @Override
    public StringConstantWrapper getStringConstant(int id) {
        return getStringConstant(id, null);
    }

    public RESTStringConstantV1 getRESTStringConstant(int id, Integer revision) {
        try {
            final RESTStringConstantV1 stringConstant;
            if (getRESTEntityCache().containsKeyValue(RESTStringConstantV1.class, id, revision)) {
                stringConstant = getRESTEntityCache().get(RESTStringConstantV1.class, id, revision);
            } else {
                stringConstant = loadStringConstant(id, revision, null);
                getRESTEntityCache().add(stringConstant, revision);
            }
            return stringConstant;
        } catch (Exception e) {
            log.debug("Failed to retrieve String Constant " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public StringConstantWrapper getStringConstant(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTStringConstant(id, revision))
                .isRevision(revision != null)
                .build();
    }

    public RESTStringConstantCollectionV1 getRESTStringConstantRevisions(int id, Integer revision) {
        try {
            RESTStringConstantV1 stringConstant = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTStringConstantV1.class, id, revision)) {
                stringConstant = getRESTEntityCache().get(RESTStringConstantV1.class, id, revision);

                if (stringConstant.getRevisions() != null) {
                    return stringConstant.getRevisions();
                }
            }
            // We need to expand the revisions in the string constant
            final String expandString = getExpansionString(RESTStringConstantV1.REVISIONS_NAME);

            // Load the string constant from the REST Interface
            final RESTStringConstantV1 tempStringConstant = loadStringConstant(id, revision, expandString);

            if (stringConstant == null) {
                stringConstant = tempStringConstant;
                getRESTEntityCache().add(stringConstant, revision);
            } else {
                stringConstant.setRevisions(tempStringConstant.getRevisions());
            }

            return stringConstant.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for String Constant " + id + (revision == null ? "" : (", Revision " + revision)),
                    e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<StringConstantWrapper> getStringConstantRevisions(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<StringConstantWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTStringConstantRevisions(id, revision))
                .isRevisionCollection()
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }
}
