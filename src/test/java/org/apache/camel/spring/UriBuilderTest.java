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

import java.io.StringReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.camel.model.ProcessorDefinition;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author yihtserns
 */
public class UriBuilderTest {

    @Test
    public void canBuildUriWithSchemeAndHierarchicalPart() {
        String uri = new UriBuilder("jetty", "http://localhost:8089").toString();

        assertThat(uri, is("jetty:http://localhost:8089"));
    }

    @Test
    public void canBuildUriWithCombinedSchemeAndHierarchicalPart() {
        String uri = new UriBuilder("jetty:http://localhost:8089").toString();

        assertThat(uri, is(uri));
    }

    @Test
    public void canBuildUriWithSchemeAndHierarchicalPartAndOneQueryParam() {
        String uri = new UriBuilder("jetty", "http://localhost:8089")
                .addQueryParam("throwExceptionOnFailure", false)
                .toString();

        assertThat(uri, is("jetty:http://localhost:8089?throwExceptionOnFailure=false"));
    }

    @Test
    public void canBuildUriWithSchemeAndHierarchicalPartAndMultiQueryParams() {
        String uri = new UriBuilder("jetty", "http://localhost:8089")
                .addQueryParam("bridgeEndpoint", true)
                .addQueryParam("throwExceptionOnFailure", false)
                .toString();

        assertThat(uri, is("jetty:http://localhost:8089?bridgeEndpoint=true&throwExceptionOnFailure=false"));
    }

    @Test
    public void ignoreQueryParamWithNullValue() {
        String uri = new UriBuilder("jetty", "http://localhost:8089")
                .addQueryParam("bridgeEndpoint", null)
                .addQueryParam("throwExceptionOnFailure", false)
                .toString();

        assertThat(uri, is("jetty:http://localhost:8089?throwExceptionOnFailure=false"));
    }

    @Test
    public void canBuildQueryParamFromFields() throws Exception {
        String xml = "<jaxbClass xmlns=\"http://example.org/jaxb\""
                + " value=\"My Val\""
                + " count=\"100\""
                + "/>";

        Class<JaxbClass> jaxbType = JaxbClass.class;
        JaxbClass instance = jaxbType.cast((JAXBContext.newInstance(jaxbType))
                .createUnmarshaller()
                .unmarshal(new StringReader(xml)));

        String uri = new UriBuilder("direct", "start")
                .addQueryParamFromDeclaredFields(instance)
                .toString();
        assertThat(uri, is("direct:start?count=100&value=My Val"));
    }

    /**
     * A required attribute cannot be a (optional) query param, right?
     */
    @Test
    public void shouldExcludeRequireFields() throws Exception {
        String xml = "<jaxbClassWithRequired xmlns=\"http://example.org/jaxb/required\""
                + " value=\"My Val\""
                + " count=\"100\""
                + " valid=\"true\""
                + "/>";

        Class<JaxbClassWithRequired> jaxbType = JaxbClassWithRequired.class;
        JaxbClassWithRequired instance = jaxbType.cast((JAXBContext.newInstance(jaxbType))
                .createUnmarshaller()
                .unmarshal(new StringReader(xml)));

        String uri = new UriBuilder("direct", "start")
                .addQueryParamFromDeclaredFields(instance)
                .toString();
        assertThat(uri, is("direct:start?count=100&value=My Val"));
    }

    @Test
    public void shouldUseCustomXmlAttributeNameAsQueryParamKeyIfAvailable() throws Exception {
        String xml = "<jaxbClassWithAttributeName xmlns=\"http://example.org/jaxb\""
                + " id=\"My Val\""
                + " count=\"100\""
                + "/>";

        Class<JaxbClassWithAttributeName> jaxbType = JaxbClassWithAttributeName.class;
        JaxbClassWithAttributeName instance = jaxbType.cast((JAXBContext.newInstance(jaxbType))
                .createUnmarshaller()
                .unmarshal(new StringReader(xml)));

        String uri = new UriBuilder("direct", "start")
                .addQueryParamFromDeclaredFields(instance)
                .toString();
        assertThat(uri, is("direct:start?count=100&id=My Val"));

    }

    @Test
    public void canBuildUriWithDynamicAttributes() throws Exception {
        String xml = "<jaxbClassWithDynamicAttributes xmlns=\"http://example.org/jaxb\""
                + " xmlns:dyn=\"http://camel.apache.org/schema/spring/dynamic-attributes\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xmlns:beans=\"http://www.springframework.org/schema/beans\""
                + " dyn:attr1=\"First\""
                + " dyn:attr2=\"Second\""
                + " xsi:nonAttr1=\"Skip this\""
                + " beans:nonAttr2=\"Skip this too\""
                + "/>";

        Class<JaxbClassWithDynamicAttributes> jaxbType = JaxbClassWithDynamicAttributes.class;
        JaxbClassWithDynamicAttributes instance = jaxbType.cast((JAXBContext.newInstance(jaxbType)
                .createUnmarshaller()
                .unmarshal(new StringReader(xml))));

        String uri = new UriBuilder("direct", "start")
                .addQueryParamFromDynamicAttributes(instance)
                .toString();
        assertThat(uri, is("direct:start?attr1=First&attr2=Second"));
    }

    @Test
    public void shouldNotFailWhenThereIsNoDynamicAttribute() throws Exception {
        String xml = "<jaxbClassWithDynamicAttributes xmlns=\"http://example.org/jaxb\""
                + " xmlns:dyn=\"http://camel.apache.org/schema/spring/dynamic-attributes\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xmlns:beans=\"http://www.springframework.org/schema/beans\""
                + "/>";

        Class<JaxbClassWithDynamicAttributes> jaxbType = JaxbClassWithDynamicAttributes.class;
        JaxbClassWithDynamicAttributes instance = jaxbType.cast((JAXBContext.newInstance(jaxbType)
                .createUnmarshaller()
                .unmarshal(new StringReader(xml))));

        String uri = new UriBuilder("direct", "start")
                .addQueryParamFromDynamicAttributes(instance)
                .toString();
        assertThat(uri, is("direct:start"));
    }

    @XmlRootElement(namespace = "http://example.org/jaxb")
    private static final class JaxbClass {

        @XmlAttribute
        String value;
        @XmlAttribute
        Integer count;
    }

    @XmlRootElement(namespace = "http://example.org/jaxb/required")
    private static final class JaxbClassWithRequired {

        @XmlAttribute(required = true)
        Boolean valid;
        @XmlAttribute
        String value;
        @XmlAttribute
        Integer count;
    }

    @XmlRootElement(namespace = "http://example.org/jaxb")
    private static final class JaxbClassWithAttributeName {

        @XmlAttribute(name = "id")
        String value;
        @XmlAttribute
        Integer count;
    }

    @XmlRootElement(namespace = "http://example.org/jaxb")
    private static final class JaxbClassWithDynamicAttributes extends ProcessorDefinition {

        @Override
        public List getOutputs() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isOutputSupported() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
