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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
