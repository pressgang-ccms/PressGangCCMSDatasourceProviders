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

package org.jboss.pressgang.ccms.proxy;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslationDetailV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.base.RESTBaseContentSpecV1;

public class RESTCSTranslationDetailV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSTranslationDetailV1> {
    public RESTCSTranslationDetailV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTCSTranslationDetailV1 entity,
            boolean isRevisionEntity, final RESTBaseContentSpecV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    @Override
    protected RESTBaseContentSpecV1<?, ?, ?> getParent() {
        return (RESTBaseContentSpecV1<?, ?, ?>) super.getParent();
    }
}
