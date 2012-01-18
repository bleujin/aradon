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

package org.testatoo.container.impl;

import static org.testatoo.container.util.ClassloaderUtils.tryEnhanceContextClassloaderWithClasspath;

import java.io.File;
import java.net.URL;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.testatoo.container.ContainerConfiguration;
import org.testatoo.container.ContainerSkeleton;

public final class JettyContainer extends ContainerSkeleton<Server> {

    public static final String PROPERTY_JETTY_CONF = "jetty.conf";
    public static final String PROPERTY_JETTY_ENV = "jetty.env";
    public static final String PROPERTY_JETTY_WEB = "jetty.web";
    public static final String PROPERTY_TRACE = "jetty.trace";

    public JettyContainer(ContainerConfiguration properties) {
        super(properties);
    }

    protected void start(Server server) {
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected void stop(Server server) {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected boolean isRunning(Server server) {
        return server.isStarting() || server.isStarted() || server.isRunning();
    }

    protected Server buildServer() {
        if (settings().hasServerClassPath()) {
            tryEnhanceContextClassloaderWithClasspath(settings().serverClassPath());
        }

        Server jetty = new Server();

        // Configure server with an xml file
        if (settings().has(PROPERTY_JETTY_CONF)) {
            try {
                XmlConfiguration configuration = new XmlConfiguration(new File(settings().get(PROPERTY_JETTY_CONF)).toURI().toURL());
                configuration.configure(jetty);
            } catch (Exception e) {
                throw new RuntimeException("An error has been detected in the Jetty configuration file you provided: " + e.getClass().getSimpleName() + " - " + e.getMessage(), e);
            }
            // In jetty config file, you can set the Jetty port.
            // If it is set, we must get it.
            // But as Jetty support several connectors, we can only reset the port of the first connector we find, hopping it will be the one we want...
            if (jetty.getConnectors() != null && jetty.getConnectors().length > 0) {
                settings().port(jetty.getConnectors()[0].getPort());
            }
        }

        // If no thread pool has been given, create a new one
        if (jetty.getThreadPool() == null) {
            QueuedThreadPool threadPool = new QueuedThreadPool();
            threadPool.setMaxThreads(50);
            threadPool.setMinThreads(2);
            jetty.setThreadPool(threadPool);
        }

        // Set default handlers if not set with the configuration file
        HandlerCollection contexts = (HandlerCollection) jetty.getChildHandlerByClass(ContextHandlerCollection.class);
        if (contexts == null) {
            contexts = (HandlerCollection) jetty.getChildHandlerByClass(HandlerCollection.class);
        }
        if (contexts == null) {
            HandlerCollection handlers = new HandlerCollection();
            contexts = new ContextHandlerCollection();

            // Logger
            if (settings().has(PROPERTY_TRACE)) {
                RequestLogHandler requestLogHandler = new RequestLogHandler();
                NCSARequestLog requestLog = new NCSARequestLog();
                requestLog.setExtended(true);
                requestLogHandler.setRequestLog(requestLog);

                handlers.setHandlers(new Handler[]{contexts, new DefaultHandler(), requestLogHandler});
            } else {
                handlers.setHandlers(new Handler[]{contexts, new DefaultHandler()});
            }

            jetty.setHandler(handlers);
        }

        // Set a connector if none has been given in the config file
        if (jetty.getConnectors() == null || jetty.getConnectors().length == 0) {
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(port());
            jetty.setConnectors(new Connector[]{connector});
        }

        // Create the web app modules:
        WebAppContext webapp = new WebAppContext(webappRoot().getAbsolutePath(), contextPath());
        contexts.addHandler(webapp);

        // Configure the web app with a config file if given
        if (settings().has(PROPERTY_JETTY_ENV)) {
            try {
                XmlConfiguration configuration = new XmlConfiguration(new File(settings().get(PROPERTY_JETTY_ENV)).toURI().toURL());
                configuration.configure(webapp);
            } catch (Exception e) {
                throw new RuntimeException("An error has been detected in the Environnement configuration file you provided: " + e.getClass().getSimpleName() + " - " + e.getMessage(), e);
            }
        } else if (settings().hasWebappClassPath()) {
            URL[] cp = settings().webappClassPath();
            StringBuilder sb = new StringBuilder();
            for (URL url : cp) {
                sb.append(url).append(",");
            }
            webapp.setExtraClasspath(sb.toString());
        }

        if (settings().has(PROPERTY_JETTY_WEB)) {
            webapp.setDescriptor(new File(settings().get(PROPERTY_JETTY_WEB)).getAbsolutePath());
        }
        return jetty;
    }
}
