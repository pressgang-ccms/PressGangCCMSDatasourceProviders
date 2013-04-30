package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.StringConstants;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBStringConstantProvider extends DBDataProvider implements StringConstantProvider {
    protected DBStringConstantProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public StringConstantWrapper getStringConstant(int id) {
        return getWrapperFactory().create(getEntity(StringConstants.class, id), false);
    }

    @Override
    public StringConstantWrapper getStringConstant(int id, Integer revision) {
        if (revision == null) {
            return getStringConstant(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(StringConstants.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<StringConstantWrapper> getStringConstantRevisions(int id, Integer revision) {
        final List<StringConstants> revisions = getRevisionList(StringConstants.class, id);
        return getWrapperFactory().createCollection(revisions, StringConstants.class, revision != null);
    }
}
