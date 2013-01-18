package net.ion.nradon.ranges;

import java.util.concurrent.ExecutionException;

import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.SimpleStaticFileHandler;

/**
 * This example has a simple HTML page with an audio element
 * With Chrome, the request for the audio file uses a Range header
 */
public class AudioTagUsesRangesExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Radon webServer = RadonConfiguration.newBuilder(45453)
                .add(new SimpleStaticFileHandler("sample/net/ion/nradon/ranges/content"))
                .startRadon();

        System.out.println("Running on " + webServer.getConfig().publicUri());
    }

}
