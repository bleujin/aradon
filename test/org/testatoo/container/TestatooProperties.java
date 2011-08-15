/**
 * Copyright (C) 2008 Ovea <dev@testatoo.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.testatoo.container;

public enum TestatooProperties {

    PORT("port"),
    CONTEXT("context"),
    WEBAPP_ROOT("webappRoot"),
    WEBAPP_CLASSPATH("webappcp"),
    SERVER_CLASSPATH("servercp"),
    CONTAINER("container"),
    UNKNOWN("UNKNOWN");

    private final String name;

    private TestatooProperties(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static TestatooProperties from(String name) {
        for (TestatooProperties properties : values())
            if (properties.getName().equals(name))
                return properties;
        return UNKNOWN;
    }
}
