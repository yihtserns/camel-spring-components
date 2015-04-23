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

import org.apache.camel.spring.HttpDefinition.ToHttpDefinition;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.apache.camel.spring.XmlUtil.*;

/**
 * @author yihtserns
 */
public class HttpDefinitionTest {

    @Test
    public void shouldConvertAllToHttpAttributesToUri() throws Exception {
        String xml = "<http xmlns=\"http://camel.apache.org/schema/spring/producers\""
                + " url=\"http://example.org\""
                + " bridgeEndpoint=\"false\""
                + " clientConnectionManager=\"#connManager\""
                + " disableStreamCache=\"false\""
                + " headerFilterStrategy=\"#headerFilter\""
                + " httpBinding=\"#httpBinding\""
                + " httpClientConfigurer=\"#httpConfigurer\""
                + " throwExceptionOnFailure=\"true\""
                + " transferException=\"false\""
                + " urlRewrite=\"#uriRewriter\""
                + "/>";

        ToHttpDefinition definition = unmarshal(xml, ToHttpDefinition.class);
        assertThat(definition.getUri(), is("http://example.org"
                + "?bridgeEndpoint=false"
                + "&clientConnectionManager=#connManager"
                + "&disableStreamCache=false"
                + "&headerFilterStrategy=#headerFilter"
                + "&httpBinding=#httpBinding"
                + "&httpClientConfigurer=#httpConfigurer"
                + "&throwExceptionOnFailure=true"
                + "&transferException=false"
                + "&urlRewrite=#uriRewriter"));
    }

    @Test
    public void canUseHttpsUrl() throws Exception {
        String xml = "<http xmlns=\"http://camel.apache.org/schema/spring/producers\""
                + " url=\"https://example.org\""
                + " bridgeEndpoint=\"false\""
                + "/>";

        ToHttpDefinition definition = unmarshal(xml, ToHttpDefinition.class);
        assertThat(definition.getUri(), is("https://example.org?bridgeEndpoint=false"));
    }

    @Test
    public void canSetHttpClientDynamicAttributes() throws Exception {
        String xml = "<http xmlns=\"http://camel.apache.org/schema/spring/producers\""
                + " xmlns:dynattr=\"http://camel.apache.org/schema/spring/dynamic-attributes\""
                + " url=\"http://example.org\""
                + " dynattr:httpClient.soTimeout=\"5000\""
                + " bridgeEndpoint=\"false\""
                + " dynattr:httpClient.cookiePolicy=\"ignoreCookies\""
                + "/>";

        ToHttpDefinition definition = unmarshal(xml, ToHttpDefinition.class);
        assertThat(definition.getUri(), is("http://example.org"
                + "?bridgeEndpoint=false"
                + "&httpClient.cookiePolicy=ignoreCookies"
                + "&httpClient.soTimeout=5000"));
    }
}
