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

import org.apache.camel.spring.JettyDefinition.FromJettyDefinition;
import org.apache.camel.spring.JettyDefinition.ToJettyDefinition;
import static org.apache.camel.spring.XmlUtil.unmarshal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * @author yihtserns
 */
public class JettyDefinitionTest {

    @Test
    public void shouldConvertAllFromJettyAttributesToUri() throws Exception {
        String xml = "<jetty xmlns=\"http://camel.apache.org/schema/spring/consumers\""
                + " url=\"http://example.org\""
                + " bridgeEndpoint=\"false\""
                + " chunked=\"true\""
                + " continuationTimeout=\"30000\""
                + " disableStreamCache=\"false\""
                + " enableCORS=\"false\""
                + " enableJmx=\"false\""
                + " enableMultipartFilter=\"true\""
                + " filtersRef=\"filters\""
                + " handlers=\"#handler1,#handler2\""
                + " headerFilterStrategy=\"#headerFilter\""
                + " httpBindingRef=\"httpBinding\""
                + " httpMethodRestrict=\"POST,GET\""
                + " matchOnUriPrefix=\"false\""
                + " multipartFilterRef=\"multipartFilter\""
                + " responseBufferSize=\"1024\""
                + " sendDateHeader=\"false\""
                + " sendServerVersion=\"true\""
                + " sessionSupport=\"false\""
                + " sslContextParametersRef=\"sslParams\""
                + " traceEnabled=\"false\""
                + " transferException=\"false\""
                + " useContinuation=\"true\""
                + "/>";

        FromJettyDefinition definition = unmarshal(xml, FromJettyDefinition.class);
        assertThat(definition.getUri(), is("jetty:http://example.org"
                + "?bridgeEndpoint=false"
                + "&chunked=true"
                + "&continuationTimeout=30000"
                + "&disableStreamCache=false"
                + "&enableCORS=false"
                + "&enableJmx=false"
                + "&enableMultipartFilter=true"
                + "&filtersRef=filters"
                + "&handlers=#handler1,#handler2"
                + "&headerFilterStrategy=#headerFilter"
                + "&httpBindingRef=httpBinding"
                + "&httpMethodRestrict=POST,GET"
                + "&matchOnUriPrefix=false"
                + "&multipartFilterRef=multipartFilter"
                + "&responseBufferSize=1024"
                + "&sendDateHeader=false"
                + "&sendServerVersion=true"
                + "&sessionSupport=false"
                + "&sslContextParametersRef=sslParams"
                + "&traceEnabled=false"
                + "&transferException=false"
                + "&useContinuation=true"));
    }

    @Test
    public void shouldConvertAllToJettyAttributesToUri() throws Exception {
        String xml = "<jetty xmlns=\"http://camel.apache.org/schema/spring/producers\""
                + " xmlns:dynattr=\"http://camel.apache.org/schema/spring/dynamic-attributes\""
                + " url=\"http://example.org\""
                + " bridgeEndpoint=\"false\""
                + " enableJmx=\"false\""
                + " headerFilterStrategy=\"#headerFilter\""
                + " httpClient=\"#httpClient\""
                + " dynattr:httpClient.soTimeout=\"30000\""
                + " httpClientMaxThreads=\"254\""
                + " httpClientMinThreads=\"8\""
                + " jettyHttpBindingRef=\"jettyHttpBinding\""
                + " proxyHost=\"localhost\""
                + " proxyPort=\"8888\""
                + " responseBufferSize=\"1024\""
                + " sslContextParametersRef=\"#sslParams\""
                + " throwExceptionOnFailure=\"true\""
                + " transferException=\"false\""
                + " urlRewrite=\"#urlRewriter\""
                + "/>";

        ToJettyDefinition definition = unmarshal(xml, ToJettyDefinition.class);
        assertThat(definition.getUri(), is("jetty:http://example.org"
                + "?bridgeEndpoint=false"
                + "&enableJmx=false"
                + "&headerFilterStrategy=#headerFilter"
                + "&httpClient=#httpClient"
                + "&httpClient.soTimeout=30000"
                + "&httpClientMaxThreads=254"
                + "&httpClientMinThreads=8"
                + "&jettyHttpBindingRef=jettyHttpBinding"
                + "&proxyHost=localhost"
                + "&proxyPort=8888"
                + "&responseBufferSize=1024"
                + "&sslContextParametersRef=#sslParams"
                + "&throwExceptionOnFailure=true"
                + "&transferException=false"
                + "&urlRewrite=#urlRewriter"));
    }
}
