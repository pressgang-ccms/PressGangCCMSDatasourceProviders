/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper.base;

public interface BaseCSNodeWrapper<T extends BaseCSNodeWrapper<T>> extends EntityWrapper<T> {
    String getTitle();

    void setTitle(String title);

    String getTargetId();

    void setTargetId(String targetId);

    String getAdditionalText();

    void setAdditionalText(String additionalText);

    String getCondition();

    void setCondition(String condition);

    Integer getEntityId();

    void setEntityId(Integer id);

    Integer getEntityRevision();

    void setEntityRevision(Integer revision);

    Integer getNodeType();

    void setNodeType(Integer typeId);
}
