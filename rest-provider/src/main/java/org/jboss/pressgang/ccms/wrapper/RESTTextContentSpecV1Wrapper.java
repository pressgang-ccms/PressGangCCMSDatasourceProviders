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

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLocaleV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseContentSpecV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTTextContentSpecV1Wrapper extends RESTBaseContentSpecV1Wrapper<TextContentSpecWrapper,
        RESTTextContentSpecV1> implements TextContentSpecWrapper {

    protected RESTTextContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTTextContentSpecV1 entity,
            boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTTextContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTTextContentSpecV1 entity,
            boolean isRevision, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public void setTags(CollectionWrapper<TagWrapper> tags) {
        getEntity().explicitSetTags(tags == null ? null : (RESTTagCollectionV1) tags.unwrap());
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> properties) {
        getEntity().explicitSetProperties(properties == null ? null : (RESTAssignedPropertyTagCollectionV1) properties.unwrap());
    }

    @Override
    public String getText() {
        return getProxyEntity().getText();
    }

    @Override
    public void setText(String text) {
        getEntity().explicitSetText(text);
    }

    @Override
    public String getTitle() {
        return getProxyEntity().getTitle();
    }

    @Override
    public String getProduct() {
        return getProxyEntity().getProduct();
    }

    @Override
    public String getVersion() {
        return getProxyEntity().getVersion();
    }

    @Override
    public void setLocale(LocaleWrapper locale) {
        getEntity().explicitSetLocale(locale == null ? null : (RESTLocaleV1) locale.unwrap());
    }

    @Override
    public TextContentSpecWrapper clone(boolean deepCopy) {
        return new RESTTextContentSpecV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }
}
