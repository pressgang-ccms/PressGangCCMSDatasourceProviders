package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.CSInfoNode;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.CSInfoNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBCSInfoNodeProvider extends DBDataProvider implements CSInfoNodeProvider {
    protected DBCSInfoNodeProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public CollectionWrapper<CSInfoNodeWrapper> getCSNodeInfoRevisions(int id, Integer revision, CSNodeWrapper parent) {
        final List<CSInfoNode> revisions = getRevisionList(CSInfoNode.class, id);
        return getWrapperFactory().createCollection(revisions, CSInfoNode.class, revision != null);
    }

    @Override
    public CSInfoNodeWrapper newCSNodeInfo(CSNodeWrapper parent) {
        return getWrapperFactory().create(new CSInfoNode(), false, CSInfoNodeWrapper.class);
    }
}
