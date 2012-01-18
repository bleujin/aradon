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

import static org.apache.commons.cli.OptionBuilder.withArgName;
import static org.apache.commons.cli.OptionBuilder.withDescription;
import static org.testatoo.container.ContainerConfiguration.DEFAULT_CONTEXT_PATH;
import static org.testatoo.container.ContainerConfiguration.DEFAULT_PORT;
import static org.testatoo.container.ContainerConfiguration.DEFAULT_WEBAPP_ROOT;
import static org.testatoo.container.TestatooProperties.CONTAINER;
import static org.testatoo.container.TestatooProperties.CONTEXT;
import static org.testatoo.container.TestatooProperties.PORT;
import static org.testatoo.container.TestatooProperties.SERVER_CLASSPATH;
import static org.testatoo.container.TestatooProperties.WEBAPP_CLASSPATH;
import static org.testatoo.container.TestatooProperties.WEBAPP_ROOT;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Server;

/**
 * This class can be used to start a container. You can use this to check available options:
 * <code>
 * java com.ovea.ui.test.container.ContainerRunner -help
 * </code>
 * To specify aditionnal options for specific implementations, you can use system properties (-Doption=value)
 * <br>
 * Examples:
 * <code>
 * java com.ovea.ui.test.container.ContainerRunner -container jetty6 -Dconf=src/main/conf/jettyConfig.xml
 * java com.ovea.ui.test.container.ContainerRunner -container com.my.company.MyContainer
 * <code>
 * <b>Date:</b> 2008-03-16<br>
 */
@SuppressWarnings({"AccessStaticViaInstance"})
public final class ContainerRunner {

    private static final Options options = new Options();

    static {
        Option container = withArgName(CONTAINER.getName())
                .hasArg()
                .withDescription("Required. Set which container to use. You use one of " + Arrays.deepToString(TestatooContainer.values()) + " or specify your own class implementing interface " + Container.class.getName() + " or extending " + ContainerSkeleton.class.getName())
                .create(CONTAINER.getName());

        Option context = withArgName(CONTEXT.getName())
                .hasArg()
                .withDescription("Optional. Set the context path to use. Default to: " + DEFAULT_CONTEXT_PATH)
                .create(CONTEXT.getName());

        Option port = withArgName(PORT.getName())
                .hasArg()
                .withDescription("Optional. Set the port to listen to. Default to: " + DEFAULT_PORT)
                .create(PORT.getName());

        Option webappRoot = withArgName(WEBAPP_ROOT.getName())
                .hasArg()
                .withDescription("Optional. Set the location of the webapp. Default to: " + DEFAULT_WEBAPP_ROOT)
                .create(WEBAPP_ROOT.getName());

        Option webappcp = withArgName(WEBAPP_CLASSPATH.getName())
                .hasArg()
                .withDescription("Optional. Set the additional classpath entries to add to the webapp, separated by ',', ':' or ';'")
                .create(WEBAPP_CLASSPATH.getName());

        Option servercp = withArgName(SERVER_CLASSPATH.getName())
                .hasArg()
                .withDescription("Optional. Set the additional classpath entries to add to the server, separated by ',', ':' or ';'")
                .create(SERVER_CLASSPATH.getName());

        Option help = withDescription("Display this help")
                        .create("help");

        options.addOption(container)
                .addOption(context)
                .addOption(port)
                .addOption(webappRoot)
                .addOption(webappcp)
                .addOption(servercp)
                .addOption(help);
    }

    public Server buildServer(String...args) throws ParseException{
    	CommandLineParser parser = new GnuParser();
        CommandLine line = parser.parse(options, args);

        // Set default values
        // And override by system properties. This way, we can start ContainerRunner with specific options such as -DjettyConf=.. -DjettyEnv=...
        ContainerConfiguration settings = ContainerConfiguration.from(System.getProperties());
        // Then parse command line
        for (Option option : line.getOptions()) {
            settings.set(TestatooProperties.from(option.getOpt()), line.getOptionValue(option.getOpt()));
        }
        Container container = settings.buildContainer(line.getOptionValue(CONTAINER.getName()));
        return container.getServer() ;
    }
    
    public static void main(String... args) throws Exception {
        CommandLineParser parser = new GnuParser();
        CommandLine line = parser.parse(options, args);

        if (line.hasOption("help") || !line.hasOption(CONTAINER.getName())) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(ContainerRunner.class.getSimpleName(), options);
            return;
        }

        // Set default values
        // And override by system properties. This way, we can start ContainerRunner with specific options such as -DjettyConf=.. -DjettyEnv=...
        ContainerConfiguration settings = ContainerConfiguration.from(System.getProperties());
        // Then parse command line
        for (Option option : line.getOptions()) {
            settings.set(TestatooProperties.from(option.getOpt()), line.getOptionValue(option.getOpt()));
        }
        Container container = settings.buildContainer(line.getOptionValue(CONTAINER.getName()));
        container.start();
        
        container.getServer() ;
        
    }
}
