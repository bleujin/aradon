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

package org.testatoo.container.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public final class ResourceUtils {
    private ResourceUtils() {
    }

    public static URL url(File folder) {
        try {
            return folder.toURI().toURL();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static URL url(String location) {
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

    public static boolean hasContextResource(String location) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return cl.getResource(location) != null;
    }

    public static URL contextResource(String location) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL u = cl.getResource(location);
        if (u == null) {
            throw new IllegalArgumentException(String.format("Context resource '%s' does not exist", location));
        }
        return u;
    }

    public static boolean hasLocalResource(Class<?> clazz, String location) {
        return clazz.getResource(location) != null;
    }

    public static URL localResource(Class<?> clazz, String location) {
        URL u = clazz.getResource(location);
        if (u == null) {
            throw new IllegalArgumentException(String.format("Local resource '%s' from class '%s' does not exist", location, clazz.getName()));
        }
        return u;
    }

    public static URL container(Class clazz) {
        return container(classPackageAsResourcePath(clazz) + "/" + classFileName(clazz), clazz.getClassLoader());
    }

    public static URL container(String resource) {
        return container(resource, Thread.currentThread().getContextClassLoader());
    }

    public static URL container(String resource, ClassLoader cl) {
        final URL resUrl = cl.getResource(resource);
        if (resUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + resource);
        }
        final StringBuilder url = new StringBuilder(resUrl.toExternalForm());
        try {
            if ("jar".equalsIgnoreCase(resUrl.getProtocol())) {
                url.delete(0, 4).delete(url.indexOf(resource) - 2, url.length());
            } else if ("file".equalsIgnoreCase(resUrl.getProtocol())) {
                url.delete(url.indexOf(resource), url.length());
            } else {
                throw new UnsupportedOperationException("Cannot get container for resource [" + resource + "]. Unsupported URL protocol [" + resUrl.getProtocol() + "].");
            }
            return new URL(url.toString());
        } catch (MalformedURLException e) {
            throw new UnsupportedOperationException("Cannot get container for resource [" + resource + "]. Malformed URL [" + url + "].");
        }
    }

    /**
     * Determine the name of the class file, relative to the containing
     * package: e.g. "String.class"
     *
     * @param clazz the class
     * @return the file name of the ".class" file
     */
    public static String classFileName(Class clazz) {
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf('.');
        return className.substring(lastDotIndex + 1) + ".class";
    }

    /**
     * Given an input class object, return a string which consists of the
     * class's package name as a pathname, i.e., all dots ('.') are replaced by
     * slashes ('/'). Neither a leading nor trailing slash is added. The result
     * could be concatenated with a slash and the name of a resource, and fed
     * directly to <code>ClassLoader.getResource()</code>. For it to be fed to
     * <code>Class.getResource</code> instead, a leading slash would also have
     * to be prepended to the returned value.
     *
     * @param clazz the input class. A <code>null</code> value or the default
     *              (empty) package will result in an empty string ("") being returned.
     * @return a path which represents the package name
     * @see ClassLoader#getResource
     * @see Class#getResource
     */
    public static String classPackageAsResourcePath(Class clazz) {
        if (clazz == null) {
            return "";
        }
        String className = clazz.getName();
        int packageEndIndex = className.lastIndexOf('.');
        if (packageEndIndex == -1) {
            return "";
        }
        String packageName = className.substring(0, packageEndIndex);
        return packageName.replace('.', '/');
    }
}