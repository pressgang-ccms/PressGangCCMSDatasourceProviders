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

import org.jboss.pressgang.ccms.contentspec.utils.CSTransformer;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.provider.ContentSpecProvider;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseContentSpecWrapper;

public class DBTextContentSpecWrapper extends DBBaseContentSpecWrapper<TextContentSpecWrapper> implements TextContentSpecWrapper {
    public DBTextContentSpecWrapper(final DBProviderFactory providerFactory, final ContentSpec contentSpec, boolean isRevision) {
        super(providerFactory, contentSpec, isRevision);
    }

    @Override
    public String getText() {
        final ContentSpecProvider contentSpecProvider = getDatabaseProvider().getProvider(ContentSpecProvider.class);
        final org.jboss.pressgang.ccms.contentspec.ContentSpec contentSpec = CSTransformer.transform(
                contentSpecProvider.getContentSpec(getId(), getRevision()), getDatabaseProvider(), false);
        return contentSpec.toString(false);
    }

    @Override
    public void setText(String text) {
        throw new UnsupportedOperationException("setText(String text) has no implementation.");
    }
}
