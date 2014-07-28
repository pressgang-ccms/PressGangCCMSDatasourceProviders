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

package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.TextCSProcessingOptionsWrapper;
import org.jboss.pressgang.ccms.wrapper.TextContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface TextContentSpecProvider {
    TextContentSpecWrapper getTextContentSpec(int id);

    TextContentSpecWrapper getTextContentSpec(int id, Integer revision);

    CollectionWrapper<TextContentSpecWrapper> getTextContentSpecsWithQuery(String query);

    CollectionWrapper<TextContentSpecWrapper> getTextContentSpecRevisions(int id, Integer revision);

    TextContentSpecWrapper createTextContentSpec(TextContentSpecWrapper contentSpec);

    TextContentSpecWrapper createTextContentSpec(TextContentSpecWrapper contentSpec, TextCSProcessingOptionsWrapper processingOptions);

    TextContentSpecWrapper createTextContentSpec(TextContentSpecWrapper contentSpec, TextCSProcessingOptionsWrapper processingOptions, LogMessageWrapper logMessage);

    TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpec);

    TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpec, TextCSProcessingOptionsWrapper processingOptions);

    TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpec, TextCSProcessingOptionsWrapper processingOptions, LogMessageWrapper logMessage);

    TextContentSpecWrapper newTextContentSpec();

    TextCSProcessingOptionsWrapper newTextProcessingOptions();
}
