package net.ion.nradon.ranges;

import net.ion.nradon.WebServer;
import net.ion.nradon.handler.StaticFileHandler;

import java.io.IOException;

import static net.ion.nradon.WebServers.createWebServer;

/**
 * This example has a simple HTML page with an audio element
 * With Chrome, the request for the audio file uses a Range header
 */
public class AudioTagUsesRangesExample {

    public static void main(String[] args) throws IOException {
        WebServer webServer = createWebServer(45453)
                .add(new StaticFileHandler("sample/net/ion/nradon/ranges/content"))
                .start();

        System.out.println("Running on " + webServer.getUri());
    }

}
