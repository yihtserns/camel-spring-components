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
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 * @author yihtserns
 */
public class XmlUtil {

    public static <T> T unmarshal(String xml, Class<T> type) throws SAXException, JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        unmarshaller.setSchema(getSchema());

        return type.cast(unmarshaller.unmarshal(new StringReader(xml)));
    }

    public static Schema getSchema() throws SAXException {
        ClassLoader classLoader = FileDefinitionTest.class.getClassLoader();
        Source[] schemaSources = {
            new StreamSource(classLoader.getResourceAsStream("camel-spring.xsd")),
            new StreamSource(classLoader.getResourceAsStream("camel-spring-consumers-2.15.0.xsd")),
            new StreamSource(classLoader.getResourceAsStream("camel-spring-producers-2.15.0.xsd"))
        };

        return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaSources);
    }
}
