/*
 * Copyright 2015 yihtserns.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.spring;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.apache.camel.model.FromDefinition;
import org.apache.camel.model.ToDefinition;

/**
 *
 * @author yihtserns
 */
@XmlTransient
public class FileDefinition {

    private static final String LOCAL_NAME = "file";

    @XmlRootElement(name = LOCAL_NAME, namespace = NamespaceUri.CONSUMERS)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(namespace = NamespaceUri.CONSUMERS)
    public static final class FromFileDefinition extends FromDefinition {

        @XmlAttribute(required = true)
        String directory;
        @XmlAttribute
        Boolean autoCreate;
        @XmlAttribute
        Integer bufferSize;
        @XmlAttribute
        String fileName;
        @XmlAttribute
        Boolean flatten;
        @XmlAttribute
        String charset;
        @XmlAttribute
        Boolean copyAndDeleteOnRenameFail;
        @XmlAttribute
        Boolean renameUsingCopy;
        @XmlAttribute
        Long initialDelay;
        @XmlAttribute
        Long delay;
        @XmlAttribute
        Boolean useFixedDelay;
        @XmlAttribute
        Boolean delete;

        @Override
        public String getUri() {
            return new UriBuilder("file", directory)
                    .addQueryParamFromDeclaredFields(this)
                    .toString();
        }
    }

    @XmlRootElement(name = LOCAL_NAME, namespace = NamespaceUri.PRODUCERS)
    @XmlAccessorType(XmlAccessType.PROPERTY)
    @XmlType(namespace = NamespaceUri.PRODUCERS)
    public static final class ToFileDefinition extends ToDefinition {

        @XmlType(namespace = NamespaceUri.PRODUCERS)
        public enum FileExistStrategy {

            Override, Append, Fail, Ignore, Move, TryRename
        }

        @XmlAttribute(required = true)
        private String directory;

        @Override
        public String getUri() {
            return "file://" + directory;
        }
    }
}
