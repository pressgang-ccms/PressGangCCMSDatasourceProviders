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

package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public abstract interface AuditedEntityWrapper<T extends AuditedEntityWrapper<T>> extends EntityWrapper<T> {
    /**
     * Get the revision of the entity.
     *
     * @return The revision number for the entity.
     */
    Integer getRevision();

    /**
     * Get the revisions for the entity.
     *
     * @return A collection of revision entities for the entity.
     */
    CollectionWrapper<T> getRevisions();

    /**
     * Check if the entity is a revision entity or the latest entity.
     *
     * @return True if the entity represents a revision otherwise false.
     */
    boolean isRevisionEntity();
}
