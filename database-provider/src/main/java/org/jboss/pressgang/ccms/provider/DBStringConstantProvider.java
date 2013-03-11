package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.model.StringConstants;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBStringConstantProvider extends DBDataProvider implements StringConstantProvider {
    protected DBStringConstantProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public StringConstantWrapper getStringConstant(int id) {
        final StringConstants stringConstant = getEntityManager().find(StringConstants.class, id);
        return getWrapperFactory().create(stringConstant, false);
    }

    @Override
    public StringConstantWrapper getStringConstant(int id, Integer revision) {
        if (revision == null) {
            return getStringConstant(id);
        } else {
            final StringConstants dummyStringConstant = new StringConstants();
            dummyStringConstant.setStringConstantsId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyStringConstant, revision), true);
        }
    }

    @Override
    public CollectionWrapper<StringConstantWrapper> getStringConstantRevisions(int id, Integer revision) {
        final StringConstants tag = new StringConstants();
        tag.setStringConstantsId(id);
        final Map<Number, StringConstants> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), tag);

        final List<StringConstants> revisions = new ArrayList<StringConstants>();
        for (final Map.Entry<Number, StringConstants> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, StringConstants.class, revision != null);
    }
}
