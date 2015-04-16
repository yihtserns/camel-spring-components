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

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yihtserns
 */
@XmlTransient // Don't schemagen me
class UriBuilder {

    /**
     * Using TreeMap for more predictable query param order - easier to assert.
     */
    private Map<String, String> queryKey2Value = new TreeMap<String, String>();
    private String scheme;
    private String hierarchicalPart;

    public UriBuilder(String scheme, String hierarchicalPart) {
        this.scheme = scheme;
        this.hierarchicalPart = hierarchicalPart;
    }

    public UriBuilder addQueryParam(String key, Object value) {
        if (value != null) {
            queryKey2Value.put(key, value.toString());
        }

        return this;
    }

    public UriBuilder addQueryParamFromDeclaredFields(Object instance) {
        try {
            for (Field field : instance.getClass().getDeclaredFields()) {
                XmlAttribute attribute = field.getAnnotation(XmlAttribute.class);
                if (attribute.required()) {
                    // query params are optional; a required attribute wouldn't be a query param
                    continue;
                }
                addQueryParam(field.getName(), field.get(instance));
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(scheme).append(":").append(hierarchicalPart);
        if (!queryKey2Value.isEmpty()) {
            sb.append('?');

            Iterator<Entry<String, String>> iter = queryKey2Value.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, String> entry = iter.next();
                sb.append(entry.getKey()).append('=').append(entry.getValue());

                if (iter.hasNext()) {
                    sb.append('&');
                }
            }
        }

        return sb.toString();
    }
}
