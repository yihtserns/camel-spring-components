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
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.FromDefinition;
import org.apache.camel.model.ToDefinition;

/**
 *
 * @author yihtserns
 */
@XmlTransient
public class FileDefinition {

    private static final String LOCAL_NAME = "file";

    @XmlRootElement(name = LOCAL_NAME, namespace = NamespaceUri.CONSUMERS)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(namespace = NamespaceUri.CONSUMERS)
    public static final class FromFileDefinition extends FromDefinition {

        @XmlType(namespace = NamespaceUri.CONSUMERS)
        public enum ReadLock {

            none, markerFile, fileLock, rename, changed
        }

        @XmlAttribute(required = true)
        String directory;
        @XmlAttribute
        Boolean autoCreate;
        @XmlAttribute
        Integer bufferSize;
        @XmlAttribute
        String fileName;
        @XmlAttribute
        Boolean flatten;
        @XmlAttribute
        String charset;
        @XmlAttribute
        Boolean copyAndDeleteOnRenameFail;
        @XmlAttribute
        Boolean renameUsingCopy;
        @XmlAttribute
        Long initialDelay;
        @XmlAttribute
        Long delay;
        @XmlAttribute
        Boolean useFixedDelay;
        @XmlAttribute
        LoggingLevel runLoggingLevel;
        @XmlAttribute
        Boolean recursive;
        @XmlAttribute
        Boolean delete;
        @XmlAttribute
        Boolean noop;
        @XmlAttribute
        String preMove;
        @XmlAttribute
        String move;
        @XmlAttribute
        String moveFailed;
        @XmlAttribute
        String include;
        @XmlAttribute
        String exclude;
        @XmlAttribute
        String antInclude;
        @XmlAttribute
        String antExclude;
        @XmlAttribute
        Boolean antFilterCaseSensitive;
        @XmlAttribute
        Boolean idempotent;
        @XmlAttribute
        String idempotentKey;
        @XmlAttribute
        String idempotentRepository;
        @XmlAttribute
        String inProgressRepository;
        @XmlAttribute
        String filter;
        @XmlAttribute
        String sorter;
        @XmlAttribute
        String sortBy;
        @XmlAttribute
        ReadLock readLock;
        @XmlAttribute
        Integer readLockTimeout;
        @XmlAttribute
        Integer readLockCheckInterval;
        @XmlAttribute
        Integer readLockMinLength;
        @XmlAttribute
        Integer readLockMinAge;
        @XmlAttribute
        LoggingLevel readLockLoggingLevel;
        @XmlAttribute
        Boolean readLockMarkerFile;
        @XmlAttribute
        Boolean directoryMustExist;
        @XmlAttribute
        String doneFileName;
        @XmlAttribute
        String exclusiveReadLockStrategy;
        @XmlAttribute
        Integer maxMessagesPerPoll;
        @XmlAttribute
        Boolean eagerMaxMessagesPerPoll;
        @XmlAttribute
        Integer minDepth;
        @XmlAttribute
        Integer maxDepth;
        @XmlAttribute
        String processStrategy;
        @XmlAttribute
        Boolean startingDirectoryMustExist;
        @XmlAttribute
        String pollStrategy;
        @XmlAttribute
        Boolean sendEmptyMessageWhenIdle;
        @XmlAttribute(name = "consumer.bridgeErrorHandler")
        Boolean consumerBridgeErrorHandler;
        @XmlAttribute
        String scheduledExecutorService;
        @XmlAttribute
        String scheduler;
        @XmlAttribute
        Integer backoffMultipler;
        @XmlAttribute
        Integer backoffIdleThreshold;
        @XmlAttribute
        Integer backoffErrorThreshold;

        @Override
        public String getUri() {
            return new UriBuilder(LOCAL_NAME, directory)
                    .addQueryParamFromDeclaredFields(this)
                    .toString();
        }
    }

    @XmlRootElement(name = LOCAL_NAME, namespace = NamespaceUri.PRODUCERS)
    @XmlAccessorType(XmlAccessType.PROPERTY)
    @XmlType(namespace = NamespaceUri.PRODUCERS)
    public static final class ToFileDefinition extends ToDefinition {

        @XmlType(namespace = NamespaceUri.PRODUCERS)
        public enum FileExistStrategy {

            Override, Append, Fail, Ignore, Move, TryRename
        }

        @XmlAttribute(required = true)
        String directory;
        @XmlAttribute
        Boolean autoCreate;
        @XmlAttribute
        Integer bufferSize;
        @XmlAttribute
        String fileName;
        @XmlAttribute
        Boolean flatten;
        @XmlAttribute
        String charset;
        @XmlAttribute
        Boolean copyAndDeleteOnRenameFail;
        @XmlAttribute
        Boolean renameUsingCopy;
        @XmlAttribute
        FileExistStrategy fileExist;
        @XmlAttribute
        String tempPrefix;
        @XmlAttribute
        String tempFileName;
        @XmlAttribute
        String moveExisting;
        @XmlAttribute
        Boolean keepLastModified;
        @XmlAttribute
        Boolean eagerDeleteTargetFile;
        @XmlAttribute
        String doneFileName;
        @XmlAttribute
        Boolean allowNullBody;
        @XmlAttribute
        Boolean forceWrites;
        @XmlAttribute
        String chmod;

        @Override
        public String getUri() {
            return new UriBuilder(LOCAL_NAME, directory)
                    .addQueryParamFromDeclaredFields(this)
                    .toString();
        }
    }
}
