package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.StringConstants;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBStringConstantWrapper extends DBBaseWrapper<StringConstantWrapper> implements StringConstantWrapper {

    private final StringConstants stringConstant;

    public DBStringConstantWrapper(final DBProviderFactory providerFactory, final StringConstants stringConstant, boolean isRevision) {
        super(providerFactory, isRevision);
        this.stringConstant = stringConstant;
    }

    protected StringConstants getStringConstant() {
        return stringConstant;
    }

    @Override
    public Integer getId() {
        return getStringConstant().getId();
    }

    @Override
    public void setId(Integer id) {
        getStringConstant().setStringConstantsId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getStringConstant().getRevision();
    }

    @Override
    public CollectionWrapper<StringConstantWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getStringConstant()),
                StringConstants.class, true);
    }

    @Override
    public StringConstants unwrap() {
        return stringConstant;
    }

    @Override
    public boolean isRevisionEntity() {
        return getStringConstant().getRevision() != null;
    }

    @Override
    public String getName() {
        return getStringConstant().getConstantName();
    }

    @Override
    public void setName(String name) {
        getStringConstant().setConstantName(name);

    }

    @Override
    public String getValue() {
        return getStringConstant().getConstantValue();
    }

    @Override
    public void setValue(String value) {
        getStringConstant().setConstantValue(value);
    }

}
