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
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
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
    public void shouldConvertAllFromFileAttributesToUri() throws Exception {
        Source[] schemaSources = {
            new StreamSource(getClass().getResourceAsStream("/camel-spring.xsd")),
            new StreamSource(getClass().getResourceAsStream("/camel-spring-consumers-2.15.0.xsd"))
        };
        Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaSources);

        JAXBContext ctx = JAXBContext.newInstance(FromFileDefinition.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        unmarshaller.setSchema(schema);

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
                + " runLoggingLevel=\"TRACE\""
                + " recursive=\"true\""
                + " delete=\"true\""
                + " noop=\"false\""
                + " preMove=\"order\""
                + " move=\".done\""
                + " moveFailed=\".failed\""
                + " include=\"^.*$\""
                + " exclude=\"^.*ignore$\""
                + " antInclude=\"*\""
                + " antExclude=\"*ignore\""
                + " antFilterCaseSensitive=\"true\""
                + " idempotent=\"false\""
                + " idempotentKey=\"$simple{file:name}\""
                + " idempotentRepository=\"#idemRepo\""
                + " inProgressRepository=\"#inProgRepo\""
                + " filter=\"#fileFilter\""
                + " sorter=\"#fileSorter\""
                + " sortBy=\"$simple{file:name}\""
                + " readLock=\"none\""
                + " readLockTimeout=\"60\""
                + " readLockCheckInterval=\"60\""
                + " readLockMinLength=\"3\""
                + " readLockMinAge=\"3\""
                + " readLockLoggingLevel=\"WARN\""
                + " readLockMarkerFile=\"true\""
                + " directoryMustExist=\"true\""
                + " doneFileName=\"done\""
                + " exclusiveReadLockStrategy=\"#readLockStrategy\""
                + " maxMessagesPerPoll=\"3\""
                + " eagerMaxMessagesPerPoll=\"true\""
                + " minDepth=\"3\""
                + " maxDepth=\"3\""
                + " processStrategy=\"procStrategy\""
                + " startingDirectoryMustExist=\"true\""
                + " pollStrategy=\"#pollStrategy\""
                + " sendEmptyMessageWhenIdle=\"true\""
                + " consumer.bridgeErrorHandler=\"false\""
                + " scheduledExecutorService=\"#execService\""
                + " scheduler=\"#defaultScheduler\""
                + " backoffMultipler=\"3\""
                + " backoffIdleThreshold=\"3\""
                + " backoffErrorThreshold=\"3\""
                + "/>";
        FromFileDefinition definition = (FromFileDefinition) unmarshaller.unmarshal(new StringReader(xml));

        assertThat(definition.getUri(), is("file:c:/temp"
                + "?antExclude=*ignore"
                + "&antFilterCaseSensitive=true"
                + "&antInclude=*"
                + "&autoCreate=true"
                + "&backoffErrorThreshold=3"
                + "&backoffIdleThreshold=3"
                + "&backoffMultipler=3"
                + "&bufferSize=1024"
                + "&charset=UTF-8"
                + "&consumer.bridgeErrorHandler=false"
                + "&copyAndDeleteOnRenameFail=true"
                + "&delay=60"
                + "&delete=true"
                + "&directoryMustExist=true"
                + "&doneFileName=done"
                + "&eagerMaxMessagesPerPoll=true"
                + "&exclude=^.*ignore$"
                + "&exclusiveReadLockStrategy=#readLockStrategy"
                + "&fileName=myfile"
                + "&filter=#fileFilter"
                + "&flatten=true"
                + "&idempotent=false"
                + "&idempotentKey=$simple{file:name}"
                + "&idempotentRepository=#idemRepo"
                + "&inProgressRepository=#inProgRepo"
                + "&include=^.*$"
                + "&initialDelay=60"
                + "&maxDepth=3"
                + "&maxMessagesPerPoll=3"
                + "&minDepth=3"
                + "&move=.done"
                + "&moveFailed=.failed"
                + "&noop=false"
                + "&pollStrategy=#pollStrategy"
                + "&preMove=order"
                + "&processStrategy=procStrategy"
                + "&readLock=none"
                + "&readLockCheckInterval=60"
                + "&readLockLoggingLevel=WARN"
                + "&readLockMarkerFile=true"
                + "&readLockMinAge=3"
                + "&readLockMinLength=3"
                + "&readLockTimeout=60"
                + "&recursive=true"
                + "&renameUsingCopy=true"
                + "&runLoggingLevel=TRACE"
                + "&scheduledExecutorService=#execService"
                + "&scheduler=#defaultScheduler"
                + "&sendEmptyMessageWhenIdle=true"
                + "&sortBy=$simple{file:name}"
                + "&sorter=#fileSorter"
                + "&startingDirectoryMustExist=true"
                + "&useFixedDelay=true"));
    }
}
