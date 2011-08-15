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

import javax.net.ServerSocketFactory;

import org.eclipse.jetty.server.Server;

import java.io.File;
import java.net.ServerSocket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public abstract class ContainerSkeleton<T> implements Container {

    protected final transient Logger logger = Logger.getLogger(getClass().getName());

    private final ContainerConfiguration settings;
    private final T server;
    private final Thread shutdown = new Thread() {
        @Override
        public void run() {
            ContainerSkeleton.this.stop(server());
        }
    };

    // Implementations
    public ContainerSkeleton(ContainerConfiguration settings) {
        this.settings = settings;
        this.server = buildServer();
    }
    
    
    public  Server getServer(){
    	return (Server)server ;
    }

    public final void start() {
        if (!isRunning()) {
            verifyCanListenOnPort(port());
            if (!settings().webappRoot().exists() || !settings().webappRoot().canRead())
                throw new IllegalStateException("Cannot access webapp root " + settings().webappRoot());
            if (logger.isLoggable(Level.INFO)) {
                logger.info(format("Starting container %s on port %s...", getClass().getName(), port()));
            }
            start(server());
            Runtime.getRuntime().addShutdownHook(shutdown);
        }
    }

    public final void stop() {
        if (isRunning()) {
            try {
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(format("Stopping container %s on port %s...", getClass().getName(), port()));
                }
                stop(server());
            } finally {
                Runtime.getRuntime().removeShutdownHook(shutdown);
            }
        }
    }

    public final boolean isRunning() {
        return isRunning(server());
    }

    public final int port() {
        return settings().port();
    }

    public String contextPath() {
        return settings().context();
    }

    public File webappRoot() {
        return settings().webappRoot();
    }

    public boolean hasProperty(String property) {
        return settings().has(property);
    }

    public String getProperty(String property) {
        return settings().get(property);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Container:\n");
        sb.append(" - ").append("running").append(": ").append(isRunning()).append("\n");
        for (Map.Entry<Object, Object> entry : settings.properties.entrySet()) {
            sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    // Skeletons

    protected abstract T buildServer();

    protected abstract void start(T server);

    protected abstract void stop(T server);

    protected abstract boolean isRunning(T t);

    // Utility methods for subclasses

    protected final ContainerConfiguration settings() {
        return settings;
    }

    protected final T server() {
        return server;
    }

    private static void verifyCanListenOnPort(int port) {
        try {
            ServerSocket server = ServerSocketFactory.getDefault().createServerSocket(port);
            server.close();
        } catch (Exception e) {
            throw new PortAlreadyInUseException(port);
        }
    }

}
