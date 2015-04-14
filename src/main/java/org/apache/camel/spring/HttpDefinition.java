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
import org.apache.camel.model.ToDefinition;

/**
 *
 * @author yihtserns
 */
@XmlTransient
public class HttpDefinition {

    @XmlRootElement(name = ToHttpDefinition.LOCAL_NAME, namespace = NamespaceUri.PRODUCERS)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(namespace = NamespaceUri.PRODUCERS)
    public static class ToHttpDefinition extends ToDefinition {

        private static final String LOCAL_NAME = "http";

        @XmlAttribute(required = true)
        protected String url;
        @XmlAttribute
        protected Boolean disableStreamCache;

        @Override
        public String getUri() {
            StringBuilder sb = new StringBuilder(getProtocol()).append("://");
            sb.append(url);

            return sb.toString();
        }

        protected String getProtocol() {
            return LOCAL_NAME;
        }

        @Override
        public void setRef(String ref) {
            throw new UnsupportedOperationException("'ref' is read-only property");
        }

        @Override
        public void setUri(String uri) {
            throw new UnsupportedOperationException("'uri' is read-only property");
        }

    }

    @XmlRootElement(name = ToHttpsDefinition.LOCAL_NAME, namespace = NamespaceUri.PRODUCERS)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(namespace = NamespaceUri.PRODUCERS)
    public static final class ToHttpsDefinition extends ToHttpDefinition {

        private static final String LOCAL_NAME = "https";

        @Override
        protected String getProtocol() {
            return LOCAL_NAME;
        }

    }
}
