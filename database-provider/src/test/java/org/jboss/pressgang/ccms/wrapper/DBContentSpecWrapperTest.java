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

import static junit.framework.Assert.fail;

import javax.persistence.EntityManager;
import java.util.ArrayList;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.provider.exception.BadRequestException;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSNodeCollectionWrapper;
import org.junit.Before;
import org.junit.Test;

public class DBContentSpecWrapperTest {
    DBProviderFactory providerFactory;

    @Before
    public void setUp() {
        providerFactory = DBProviderFactory.create((EntityManager) null);
    }

    /*
     * New collection being set to the field
     */
    @Test
    public void should() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        final CSNode child2 = new CSNode();
        child2.setCSNodeId(2);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final CSNodeWrapper csNodeWrapper2 = new DBCSNodeWrapper(providerFactory, child2, false);
        final DBCSNodeCollectionWrapper collectionWrapper = new DBCSNodeCollectionWrapper(providerFactory.getWrapperFactory(),
                new ArrayList<CSNode>(), false);

        // When
        collectionWrapper.addNewItem(csNodeWrapper2);
        contentSpecWrapper.setChildren(collectionWrapper);

        // Then
        assert contentSpec.getCSNodes().size() == 1;
        assert contentSpec.getCSNodes().contains(child2);
        assert child.getContentSpec() == null;
        assert child2.getContentSpec().equals(contentSpec);
    }

    /*
     * Existing collection being added to
     */
    @Test
    public void should2() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        final CSNode child2 = new CSNode();
        child2.setCSNodeId(2);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final CSNodeWrapper csNodeWrapper2 = new DBCSNodeWrapper(providerFactory, child2, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        collectionWrapper.addNewItem(csNodeWrapper2);
        contentSpecWrapper.setChildren(collectionWrapper);

        // Then
        assert contentSpec.getCSNodes().size() == 2;
        assert contentSpec.getCSNodes().contains(child);
        assert contentSpec.getCSNodes().contains(child2);
        assert child.getContentSpec().equals(contentSpec);
        assert child2.getContentSpec().equals(contentSpec);
    }

    /*
     * Existing collection being added to after being set
     */
    @Test
    public void should2_1() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        final CSNode child2 = new CSNode();
        child2.setCSNodeId(2);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final CSNodeWrapper csNodeWrapper2 = new DBCSNodeWrapper(providerFactory, child2, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        contentSpecWrapper.setChildren(collectionWrapper);
        collectionWrapper.addNewItem(csNodeWrapper2);

        // Then
        assert contentSpec.getCSNodes().size() == 2;
        assert contentSpec.getCSNodes().contains(child);
        assert contentSpec.getCSNodes().contains(child2);
        assert child.getContentSpec().equals(contentSpec);
        assert child2.getContentSpec().equals(contentSpec);
    }

    /*
     * Existing collection being added to without be set
     */
    @Test
    public void should2_2() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        final CSNode child2 = new CSNode();
        child2.setCSNodeId(2);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final CSNodeWrapper csNodeWrapper2 = new DBCSNodeWrapper(providerFactory, child2, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        collectionWrapper.addNewItem(csNodeWrapper2);

        // Then
        assert contentSpec.getCSNodes().size() == 2;
        assert contentSpec.getCSNodes().contains(child);
        assert contentSpec.getCSNodes().contains(child2);
        assert child.getContentSpec().equals(contentSpec);
        assert child2.getContentSpec().equals(contentSpec);
    }

    /*
     * Existing collection having an element removed
     */
    @Test
    public void should3() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        collectionWrapper.addRemoveItem(collectionWrapper.getItems().get(0));
        contentSpecWrapper.setChildren(collectionWrapper);

        // Then
        assert contentSpec.getCSNodes().size() == 0;
    }

    /*
     * Existing collection having an element removed and not set
     */
    @Test
    public void should3_1() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        collectionWrapper.addRemoveItem(collectionWrapper.getItems().get(0));

        // Then
        assert contentSpec.getCSNodes().size() == 0;
    }

    /*
     * Existing collection having a temp entity added
     */
    @Test
    public void should4() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        final CSNode child2 = new CSNode();
        child2.setCSNodeId(2);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final CSNodeWrapper csNodeWrapper2 = new DBCSNodeWrapper(providerFactory, child2, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        collectionWrapper.addItem(csNodeWrapper2);
        contentSpecWrapper.setChildren(collectionWrapper);

        // Then
        assert contentSpec.getCSNodes().size() == 1;
        assert contentSpec.getCSNodes().contains(child);
        assert child.getContentSpec().equals(contentSpec);
        assert child2.getContentSpec() == null;
    }

    /*
     * Existing collection having a temp entity added and not setting the collection
     */
    @Test
    public void should4_1() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        final CSNode child2 = new CSNode();
        child2.setCSNodeId(2);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final CSNodeWrapper csNodeWrapper2 = new DBCSNodeWrapper(providerFactory, child2, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        collectionWrapper.addItem(csNodeWrapper2);

        // Then
        assert contentSpec.getCSNodes().size() == 1;
        assert contentSpec.getCSNodes().contains(child);
        assert child.getContentSpec().equals(contentSpec);
        assert child2.getContentSpec() == null;
    }

    /*
     * Existing collection having a entity removed temporarily
     */
    @Test
    public void should5() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        collectionWrapper.remove(collectionWrapper.getItems().get(0));
        contentSpecWrapper.setChildren(collectionWrapper);

        // Then
        assert contentSpec.getCSNodes().size() == 1;
        assert contentSpec.getCSNodes().contains(child);
        assert child.getContentSpec().equals(contentSpec);
    }

    /*
     * Existing collection having a entity removed temporarily and not setting the collection
     */
    @Test
    public void should5_1() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        collectionWrapper.remove(collectionWrapper.getItems().get(0));

        // Then
        assert contentSpec.getCSNodes().size() == 1;
        assert contentSpec.getCSNodes().contains(child);
        assert child.getContentSpec().equals(contentSpec);
    }

    /*
     * Existing collection having a entity updated that doesn't exist
     */
    @Test
    public void should6() {
        // Given
        final ContentSpec contentSpec = new ContentSpec();
        final CSNode child = new CSNode();
        child.setCSNodeId(1);
        final CSNode child2 = new CSNode();
        child2.setCSNodeId(2);
        contentSpec.addChild(child);
        final DBContentSpecWrapper contentSpecWrapper = new DBContentSpecWrapper(providerFactory, contentSpec, false);
        final CSNodeWrapper csNodeWrapper2 = new DBCSNodeWrapper(providerFactory, child2, false);
        final DBCSNodeCollectionWrapper collectionWrapper = (DBCSNodeCollectionWrapper) contentSpecWrapper.getChildren();

        // When
        try {
            collectionWrapper.addUpdateItem(csNodeWrapper2);
            fail("An exception should have been thrown");
        } catch (BadRequestException e) {

        }
    }
}
