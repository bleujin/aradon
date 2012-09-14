package net.ion.nradon.ranges;

import java.io.IOException;

import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.StaticFileHandler;

/**
 * This example has a simple HTML page with an audio element
 * With Chrome, the request for the audio file uses a Range header
 */
public class AudioTagUsesRangesExample {

    public static void main(String[] args) throws IOException {
        Radon webServer = RadonConfiguration.newBuilder(45453)
                .add(new StaticFileHandler("sample/net/ion/nradon/ranges/content"))
                .startRadon();

        System.out.println("Running on " + webServer.getConfig().publicUri());
    }

}
