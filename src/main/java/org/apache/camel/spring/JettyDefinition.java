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
public class JettyDefinition {

    private static final String LOCAL_NAME = "jetty";

    @XmlRootElement(name = LOCAL_NAME, namespace = NamespaceUri.CONSUMERS)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(namespace = NamespaceUri.CONSUMERS)
    public static final class FromJettyDefinition extends FromDefinition {

        @XmlAttribute(required = true)
        String url;
        @XmlAttribute
        Boolean sessionSupport;
        @XmlAttribute
        String httpBindingRef;
        @XmlAttribute
        Boolean matchOnUriPrefix;
        @XmlAttribute
        String handlers;
        @XmlAttribute
        Boolean chunked;
        @XmlAttribute
        Boolean enableJmx;
        @XmlAttribute
        Boolean disableStreamCache;
        @XmlAttribute
        Boolean transferException;
        @XmlAttribute
        Boolean bridgeEndpoint;
        @XmlAttribute
        Boolean enableMultipartFilter;
        @XmlAttribute
        String multipartFilterRef;
        @XmlAttribute
        String filtersRef;
        @XmlAttribute
        Integer continuationTimeout;
        @XmlAttribute
        Boolean useContinuation;
        @XmlAttribute
        String sslContextParametersRef;
        @XmlAttribute
        Boolean traceEnabled;
        @XmlAttribute
        String headerFilterStrategy;
        @XmlAttribute
        String httpMethodRestrict;
        @XmlAttribute
        Integer responseBufferSize;
        @XmlAttribute
        Boolean sendServerVersion;
        @XmlAttribute
        Boolean sendDateHeader;
        @XmlAttribute
        Boolean enableCORS;

        @Override
        public String getUri() {
            return new UriBuilder(LOCAL_NAME, url)
                    .addQueryParamFromDeclaredFields(this)
                    .toString();
        }
    }

    @XmlRootElement(name = LOCAL_NAME, namespace = NamespaceUri.PRODUCERS)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(namespace = NamespaceUri.PRODUCERS)
    public static final class ToJettyDefinition extends ToDefinition {

        @XmlAttribute(required = true)
        String url;
        @XmlAttribute
        String httpClient;
        @XmlAttribute
        Integer httpClientMinThreads;
        @XmlAttribute
        Integer httpClientMaxThreads;
        @XmlAttribute
        String jettyHttpBindingRef;
        @XmlAttribute
        Boolean enableJmx;
        @XmlAttribute
        Boolean throwExceptionOnFailure;
        @XmlAttribute
        Boolean transferException;
        @XmlAttribute
        Boolean bridgeEndpoint;
        @XmlAttribute
        String sslContextParametersRef;
        @XmlAttribute
        String headerFilterStrategy;
        @XmlAttribute
        String urlRewrite;
        @XmlAttribute
        Integer responseBufferSize;
        @XmlAttribute
        String proxyHost;
        @XmlAttribute
        Integer proxyPort;

        @Override
        public String getUri() {
            return new UriBuilder(LOCAL_NAME, url)
                    .addQueryParamFromDeclaredFields(this)
                    .addQueryParamFromDynamicAttributes(this)
                    .toString();
        }
    }
}
