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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.FileDefinition.FromFileDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author yihtserns
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class FileDefinitionTest {

    @Produce(uri = "direct:start")
    private ProducerTemplate startTemplate;
    @EndpointInject(uri = "mock:end")
    private MockEndpoint resultEndpoint;

    @Test
    public void canUseToFileAndFromFileDefinition() throws Exception {
        String expectedContent = Long.toString(System.currentTimeMillis());
        startTemplate.sendBody(expectedContent);

        resultEndpoint.expectedBodiesReceived(expectedContent);
        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void shouldConvertAllAttributesToUri() throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(FromFileDefinition.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();

        String xml = "<file xmlns=\"http://camel.apache.org/schema/spring/consumers\""
                + " directory=\"c:/temp\""
                + " autoCreate=\"true\""
                + " bufferSize=\"1024\""
                + " fileName=\"myfile\""
                + " flatten=\"true\""
                + " charset=\"UTF-8\""
                + " copyAndDeleteOnRenameFail=\"true\""
                + " renameUsingCopy=\"true\""
                + " initialDelay=\"60\""
                + " delay=\"60\""
                + " useFixedDelay=\"true\""
                + " delete=\"true\""
                + "/>";
        FromFileDefinition definition = (FromFileDefinition) unmarshaller.unmarshal(new StringReader(xml));

        assertThat(definition.getUri(), is("file:c:/temp"
                + "?autoCreate=true"
                + "&bufferSize=1024"
                + "&fileName=myfile"
                + "&flatten=true"
                + "&charset=UTF-8"
                + "&copyAndDeleteOnRenameFail=true"
                + "&renameUsingCopy=true"
                + "&initialDelay=60"
                + "&delay=60"
                + "&useFixedDelay=true"
                + "&delete=true"));
    }
}
