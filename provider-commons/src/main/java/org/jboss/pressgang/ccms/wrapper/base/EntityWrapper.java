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

public abstract interface EntityWrapper<T extends EntityWrapper<T>> extends BaseWrapper<T> {
    /**
     * Get the Unique ID for the entity.
     *
     * @return The ID value.
     */
    Integer getId();

    /**
     * Set the ID for the Entity.
     *
     * @param id The unique id value.
     */
    void setId(Integer id);

    /**
     * Clone the entity and wrapper.
     *
     * @param deepCopy If the collections in the entity should be cloned as well.
     * @return The cloned entity/wrapper.
     */
    T clone(boolean deepCopy);
}
