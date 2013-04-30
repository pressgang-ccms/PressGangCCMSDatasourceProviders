package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public interface TranslatedContentSpecWrapper extends EntityWrapper<TranslatedContentSpecWrapper> {
    Integer getContentSpecId();

    void setContentSpecId(Integer id);

    Integer getContentSpecRevision();

    void setContentSpecRevision(Integer revision);

    String getZanataId();

    UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes();

    void setTranslatedNodes(UpdateableCollectionWrapper<TranslatedCSNodeWrapper> translatedNodes);

    ContentSpecWrapper getContentSpec();

    void setContentSpec(ContentSpecWrapper contentSpec);

    String getEditorURL(ZanataDetails zanataDetails, String locale);
}