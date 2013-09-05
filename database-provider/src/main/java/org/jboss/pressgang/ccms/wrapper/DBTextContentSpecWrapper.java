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
