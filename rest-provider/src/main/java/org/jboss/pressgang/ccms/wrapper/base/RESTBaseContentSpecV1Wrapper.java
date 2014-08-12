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

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Collection;
import java.util.Date;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentBaseRESTEntityWithPropertiesV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.base.RESTBaseContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTContentSpecTypeV1;
import org.jboss.pressgang.ccms.wrapper.LocaleWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public abstract class RESTBaseContentSpecV1Wrapper<T extends BaseContentSpecWrapper<T>, U extends RESTBaseContentSpecV1<U, ?,
        ?>> extends RESTBaseEntityWrapper<T, U> implements BaseContentSpecWrapper<T> {

    protected RESTBaseContentSpecV1Wrapper(RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTBaseContentSpecV1Wrapper(RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity,
            Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTags())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getProperties() {
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>)
                RESTCollectionWrapperBuilder.<PropertyTagInContentSpecWrapper>newBuilder()
                        .providerFactory(getProviderFactory())
                        .collection(getProxyEntity().getProperties())
                        .isRevisionCollection(isRevisionEntity())
                        .parent(getProxyEntity())
                        .entityWrapperInterface(PropertyTagInContentSpecWrapper.class)
                        .build();
    }

    @Override
    public LocaleWrapper getLocale() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getLocale())
                .isRevision(isRevisionEntity())
                .build();
    }

    @Override
    public Integer getType() {
        return RESTContentSpecTypeV1.getContentSpecTypeId(getProxyEntity().getType());
    }

    @Override
    public Date getLastModified() {
        return getProxyEntity().getLastModified();
    }

    @Override
    public String getErrors() {
        return getProxyEntity().getErrors();
    }

    @Override
    public void setErrors(String errors) {
        getEntity().setErrors(errors);
    }

    @Override
    public String getFailed() {
        return getProxyEntity().getFailedContentSpec();
    }

    @Override
    public void setFailed(String failed) {
        getEntity().setFailedContentSpec(failed);
    }

    @Override
    public PropertyTagInContentSpecWrapper getProperty(int propertyId) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(ComponentBaseRESTEntityWithPropertiesV1.returnProperty(getProxyEntity(), propertyId))
                .isRevision(isRevisionEntity())
                .parent(getProxyEntity())
                .wrapperInterface(PropertyTagInContentSpecWrapper.class)
                .build();
    }

    @Override
    public boolean hasTag(final int tagId) {
        return ComponentContentSpecV1.hasTag(getProxyEntity(), tagId);
    }
}
