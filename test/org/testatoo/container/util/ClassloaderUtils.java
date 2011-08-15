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

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public final class ClassloaderUtils {
    private ClassloaderUtils() {
    }

    @SuppressWarnings({"unchecked"})
    public static <T> Class<T> loadClass(String className) {
        try {
            return (Class<T>) Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            URL[] cp = tryToGetContextClassPath();
            throw new IllegalArgumentException("Cannot load class [" + className + "] from classpath " + (cp == null ? "<unknown>" : Arrays.deepToString(cp)), e);
        }
    }

    public static URL[] tryToGetClassPath(ClassLoader cl) {
        if (cl instanceof URLClassLoader) {
            return ((URLClassLoader) cl).getURLs();
        }
        return new URL[0];
    }

    public static URL[] tryToGetContextClassPath() {
        return tryToGetClassPath(Thread.currentThread().getContextClassLoader());
    }

    public static void tryEnhanceContextClassloaderWithClasspath(URL... classpath) {
        ClassLoader current = Thread.currentThread().getContextClassLoader();
        if (current instanceof URLClassLoader) {
            try {
                Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addURL.setAccessible(true);
                for (URL url : classpath) {
                    addURL.invoke(current, url);
                }
            } catch (Exception e) {
                // Should not occur since addURL is present in URlClassloader
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    public static Class loadIsolated(final String className, final ClassLoader cl) {
        ClassLoader old = Thread.currentThread().getContextClassLoader();
        final CallBack<Class> callback = new CallBack<Class>();
        Thread t = new Thread(new Runnable() {
            public void run() {
                Thread.currentThread().setContextClassLoader(cl);
                callback.value = loadClass(className);
            }
        }, "Isolated loader of: " + className);
        t.run();
        try {
            t.join();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            Thread.currentThread().setContextClassLoader(old);
        }
        return callback.value;
    }

    private static class CallBack<T> {
        T value;
    }
}