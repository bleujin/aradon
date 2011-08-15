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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Random;

public final class FileUtils {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    private static int counter = -1;

    private FileUtils() {
    }

    public static String read(File file) throws IOException {
        return read(file.toURI().toURL());
    }

    public static String read(URL file) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(file.openStream()));
        try {
            StringWriter sw = new StringWriter();
            copy(input, sw);
            return sw.toString();
        } finally {
            input.close();
        }
    }

    public static void copyFileToFile(final File source, final File destination) throws IOException {
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }
        // Make sure we can write to destination
        if (destination.exists() && !destination.canWrite()) {
            throw new IllegalArgumentException("Unable to open file " + destination + " for writing.");
        }
        InputStream in = new BufferedInputStream(new FileInputStream(source));
        copy(in, destination);
        in.close();
    }

    public static void copyURLToFile(final URL source, final File destination) throws IOException {
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }
        // Make sure we can write to destination
        if (destination.exists() && !destination.canWrite()) {
            throw new IllegalArgumentException("Unable to open file " + destination + " for writing.");
        }
        InputStream in = new BufferedInputStream(source.openStream());
        copy(in, destination);
        in.close();
    }

    public static File createTemporaryFolder(String prefix) {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        File folder;
        do {
            folder = generateFile(prefix, "", tmpDir);
        }
        while (!folder.mkdirs());
        DeleteOnExitHook.instance().add(folder);
        return folder;
    }

    private static File generateFile(String prefix, String suffix, File dir) {
        if (counter == -1) {
            counter = new Random().nextInt() & 0xffff;
        }
        counter++;
        return new File(dir, prefix + Integer.toString(counter) + suffix);
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int n;
        while (-1 != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
        }
    }

    public static void copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    public static void copy(InputStream in, File dest) throws IOException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
        try {
            copy(in, out);
        } finally {
            out.flush();
            out.close();
        }
    }
}
