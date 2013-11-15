package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.StringConstants;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBStringConstantWrapper extends DBBaseEntityWrapper<StringConstantWrapper, StringConstants> implements StringConstantWrapper {
    private final StringConstants stringConstant;

    public DBStringConstantWrapper(final DBProviderFactory providerFactory, final StringConstants stringConstant, boolean isRevision) {
        super(providerFactory, isRevision, StringConstants.class);
        this.stringConstant = stringConstant;
    }

    @Override
    protected StringConstants getEntity() {
        return stringConstant;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setStringConstantsId(id);
    }

    @Override
    public String getName() {
        return getEntity().getConstantName();
    }

    @Override
    public void setName(String name) {
        getEntity().setConstantName(name);
    }

    @Override
    public String getValue() {
        return getEntity().getConstantValue();
    }

    @Override
    public void setValue(String value) {
        getEntity().setConstantValue(value);
    }
}
