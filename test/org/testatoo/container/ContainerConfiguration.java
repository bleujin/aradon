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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.testatoo.container.TestatooProperties.*;

public final class ContainerConfiguration {

    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_CONTEXT_PATH = "/";
    public static final String DEFAULT_WEBAPP_ROOT = "src/main/webapp";

    final Properties properties = new Properties();

    private ContainerConfiguration() {
        port(DEFAULT_PORT);
        context(DEFAULT_CONTEXT_PATH);
        webappRoot(DEFAULT_WEBAPP_ROOT);
    }

    /* Fluent config */

    public Container buildContainer(TestatooContainer type) {
        return buildContainer(type.serverClass());
    }

    public Container buildContainer(String containerClass) {
        notNull(containerClass, "Container type " + Arrays.deepToString(TestatooContainer.values()) + " or container class");
        Class<Container> clazz = null;
        for (TestatooContainer container : TestatooContainer.values()) {
            if (container.name().equalsIgnoreCase(containerClass)) {
                clazz = loadServerClass(container.serverClass());
                break;
            }
        }
        if (clazz == null) clazz = loadServerClass(containerClass);
        try {
            return clazz.getConstructor(ContainerConfiguration.class).newInstance(ContainerConfiguration.from(this));
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof RuntimeException) throw (RuntimeException) e.getTargetException();
            else throw new RuntimeException(e.getTargetException().getMessage(), e.getTargetException());
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot instanciate class [" + clazz + "]. Ensure there is a public constructor having a parameter of type " + ContainerConfiguration.class.getName(), e);
        }
    }

    /* fluent config */

    public ContainerConfiguration serverClassPath(String serverClassPath) {
        notNull(serverClassPath, "Server classpath");
        return serverClassPath(toUrl(serverClassPath));
    }

    public ContainerConfiguration serverClassPath(File... paths) {
        notNull(paths, "Server classpath");
        List<URL> urls = new ArrayList<URL>(paths.length);
        for (File path : paths) urls.add(pathAsURL(path.getAbsolutePath()));
        return serverClassPath(urls.toArray(new URL[urls.size()]));
    }

    public ContainerConfiguration serverClassPath(URL... locations) {
        notNull(locations, "Server classpath");
        properties.put(SERVER_CLASSPATH, locations);
        return this;
    }

    public boolean hasServerClassPath() {
        return properties.get(SERVER_CLASSPATH) != null;
    }

    public URL[] serverClassPath() {
        URL[] locations = (URL[]) properties.get(SERVER_CLASSPATH);
        return locations == null ? new URL[0] : locations;
    }

    public ContainerConfiguration webappClassPath(String webappClassPath) {
        notNull(webappClassPath, "Webapp classpath");
        return webappClassPath(toUrl(webappClassPath));
    }

    public ContainerConfiguration webappClassPath(File... paths) {
        notNull(paths, "Webapp classpath");
        List<URL> urls = new ArrayList<URL>(paths.length);
        for (File path : paths) urls.add(pathAsURL(path.getAbsolutePath()));
        return webappClassPath(urls.toArray(new URL[urls.size()]));
    }

    public ContainerConfiguration webappClassPath(URL... locations) {
        notNull(locations, "Webapp classpath");
        properties.put(WEBAPP_CLASSPATH, locations);
        return this;
    }

    public boolean hasWebappClassPath() {
        return properties.get(WEBAPP_CLASSPATH) != null;
    }

    public URL[] webappClassPath() {
        URL[] locations = (URL[]) properties.get(WEBAPP_CLASSPATH);
        return locations == null ? new URL[0] : locations;
    }

    public ContainerConfiguration webappRoot(String webappRoot) {
        notNull(webappRoot, "Webapp root");
        return webappRoot(new File(webappRoot));
    }

    public ContainerConfiguration webappRoot(File webappRoot) {
        notNull(webappRoot, "Webapp root");
        properties.put(WEBAPP_ROOT, webappRoot);
        return this;
    }

    public File webappRoot() {
        return (File) properties.get(WEBAPP_ROOT);
    }

    public ContainerConfiguration context(String contextPath) {
        notNull(contextPath, "Webapp context path");
        properties.put(CONTEXT, contextPath.startsWith("/") ? contextPath : "/" + contextPath);
        return this;
    }

    public String context() {
        return (String) properties.get(CONTEXT);
    }

    public ContainerConfiguration port(int port) {
        if (port < 0 || port > 65535) throw new IllegalArgumentException("Invalid port: " + port);
        properties.put(PORT, port);
        return this;
    }

    public int port() {
        return (Integer) properties.get(PORT);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ContainerConfiguration:\n");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    /* properties */

    public boolean has(String property) {
        return properties.getProperty(property) != null;
    }

    public String get(String property) {
        if (has(property)) return properties.getProperty(property);
        throw new IllegalArgumentException("No property has been defined for [" + property + "]");
    }

    public ContainerConfiguration add(ContainerConfiguration settings) {
        add(settings.properties);
        return this;
    }

    public ContainerConfiguration add(Properties settings) {
        // emulate JDK 6's stringPropertyNames()
        for (Map.Entry<Object, Object> entry : settings.entrySet()) {
            if (entry.getKey() instanceof String && entry.getValue() instanceof String)
                set((String) entry.getKey(), (String) entry.getValue());
        }
        // override by more important properties
        for (Map.Entry<Object, Object> entry : settings.entrySet())
            if (entry.getKey() instanceof TestatooProperties)
                properties.put(entry.getKey(), entry.getValue());
        return this;
    }

    public ContainerConfiguration set(String property, String value) {
        notNull(property, "Property name");
        properties.setProperty(property, value);
        return this;
    }

    public ContainerConfiguration set(TestatooProperties property, String value) {
        notNull(property, "Property name");
        switch (property) {
            case CONTEXT:
                return context(value);
            case PORT:
                return port(Integer.parseInt(value));
            case SERVER_CLASSPATH:
                return serverClassPath(value);
            case WEBAPP_CLASSPATH:
                return webappClassPath(value);
            case WEBAPP_ROOT:
                return webappRoot(value);
        }
        return this;
    }

    public ContainerConfiguration clear(String property) {
        notNull(property, "Property name");
        properties.remove(property);
        return this;
    }

    /* static creation */

    public static ContainerConfiguration create() {
        return new ContainerConfiguration();
    }

    public static ContainerConfiguration from(Properties settings) {
        return new ContainerConfiguration().add(settings);
    }

    public static ContainerConfiguration from(ContainerConfiguration settings) {
        return new ContainerConfiguration().add(settings);
    }

    /* some utility methods */

    private static void notNull(Object o, String message) {
        if (o == null) throw new IllegalArgumentException(message + " cannot be null !");
    }

    private static URL[] toUrl(String classPath) {
        String[] cp = classPath.split(",|;|:");
        List<URL> urls = new ArrayList<URL>(cp.length);
        for (String s : cp)
            if (s != null && !(s.length() == 0))
                urls.add(pathAsURL(s));
        return urls.toArray(new URL[urls.size()]);
    }

    private static URL pathAsURL(String location) {
        File f = new File(location);
        if (f.exists()) {
            if (f.isDirectory()) {
                location += location.endsWith("/") ? "" : "/";
            }
            try {
                return new File(location).toURI().toURL();
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Illegal location [" + location + "]: " + e.getMessage(), e);
            }
        }
        try {
            return new URL(location);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Illegal location [" + location + "]: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings({"unchecked"})
    private static Class<Container> loadServerClass(String name) {
        Class<?> c;
        try {
            c = Thread.currentThread().getContextClassLoader().loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to load class " + name + " from current context classloader");
        }
        if (!Container.class.isAssignableFrom(c)) {
            throw new IllegalArgumentException("Container class must implement " + Container.class.getName() + " interface or extends " + ContainerSkeleton.class.getName());
        }
        return (Class<Container>) c;
    }

}
