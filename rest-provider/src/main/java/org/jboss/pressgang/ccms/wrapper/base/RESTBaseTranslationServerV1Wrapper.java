/*
 * Copyright 2011-2014 Red Hat, Inc.
 *
 * This file is part of PressGang CCMS.
 *
 * PressGang CCMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PressGang CCMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with PressGang CCMS. If not, see <http://www.gnu.org/licenses/>.
 */

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTranslationServerV1;

public abstract class RESTBaseTranslationServerV1Wrapper<T extends BaseTranslationServerWrapper<T>,
        U extends RESTBaseTranslationServerV1<U>> extends RESTBaseEntityWrapper<T, U>
        implements BaseTranslationServerWrapper<T> {

    protected RESTBaseTranslationServerV1Wrapper(final RESTProviderFactory providerFactory, final U entity,
            boolean isNewEntity) {
        super(providerFactory, entity, isNewEntity);
    }

    protected RESTBaseTranslationServerV1Wrapper(final RESTProviderFactory providerFactory, final U entity,
            boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isNewEntity, expandedMethods);
    }

    @Override
    public String getName() {
        return getEntity().getName();
    }

    @Override
    public String getUrl() {
        return getEntity().getUrl();
    }
}
