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

    private static final String LOCAL_NAME = "http";

    @XmlRootElement(name = LOCAL_NAME, namespace = NamespaceUri.PRODUCERS)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(namespace = NamespaceUri.PRODUCERS)
    public static final class ToHttpDefinition extends ToDefinition {

        /**
         * E.g.
         * <ul>
         * <li>http://example.org</li>
         * <li>https://example.org</li>
         * <li>http://example.org:80</li>
         * <li>https://example.org:443/path</li>
         * </ul>
         */
        @XmlAttribute(required = true)
        String url;
        @XmlAttribute
        Boolean throwExceptionOnFailure;
        @XmlAttribute
        Boolean bridgeEndpoint;
        @XmlAttribute
        Boolean disableStreamCache;
        @XmlAttribute
        String httpBinding;
        @XmlAttribute
        String httpClientConfigurer;
        @XmlAttribute
        String clientConnectionManager;
        @XmlAttribute
        Boolean transferException;
        @XmlAttribute
        String headerFilterStrategy;
        @XmlAttribute
        String urlRewrite;

        @Override
        public String getUri() {
            return new UriBuilder(url)
                    .addQueryParamFromDeclaredFields(this)
                    .addQueryParamFromDynamicAttributes(this)
                    .toString();
        }
    }
}
