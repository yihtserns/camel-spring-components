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

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.FileDefinition.FromFileDefinition;
import org.apache.camel.spring.FileDefinition.ToFileDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.apache.camel.spring.XmlUtil.*;

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

        FromFileDefinition definition = unmarshal(xml, FromFileDefinition.class);
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

    @Test
    public void shouldConvertAllToFileAttributesToUri() throws Exception {
        String xml = "<file xmlns=\"http://camel.apache.org/schema/spring/producers\""
                + " directory=\"c:/temp\""
                + " autoCreate=\"true\""
                + " bufferSize=\"1024\""
                + " fileName=\"myfile\""
                + " flatten=\"true\""
                + " charset=\"UTF-8\""
                + " copyAndDeleteOnRenameFail=\"true\""
                + " renameUsingCopy=\"true\""
                + " fileExist=\"Append\""
                + " tempPrefix=\"temp-\""
                + " tempFileName=\"temp-$simple{file:name:ext}\""
                + " moveExisting=\"$simple{file:name:ext}\""
                + " keepLastModified=\"false\""
                + " eagerDeleteTargetFile=\"false\""
                + " doneFileName=\"$simple{file:name:ext}-done\""
                + " allowNullBody=\"true\""
                + " forceWrites=\"true\""
                + " chmod=\"777\""
                + "/>";

        ToFileDefinition definition = unmarshal(xml, ToFileDefinition.class);
        assertThat(definition.getUri(), is("file:c:/temp"
                + "?allowNullBody=true"
                + "&autoCreate=true"
                + "&bufferSize=1024"
                + "&charset=UTF-8"
                + "&chmod=777"
                + "&copyAndDeleteOnRenameFail=true"
                + "&doneFileName=$simple{file:name:ext}-done"
                + "&eagerDeleteTargetFile=false"
                + "&fileExist=Append"
                + "&fileName=myfile"
                + "&flatten=true"
                + "&forceWrites=true"
                + "&keepLastModified=false"
                + "&moveExisting=$simple{file:name:ext}"
                + "&renameUsingCopy=true"
                + "&tempFileName=temp-$simple{file:name:ext}"
                + "&tempPrefix=temp-"));
    }
}
