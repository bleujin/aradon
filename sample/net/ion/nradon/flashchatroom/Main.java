package net.ion.nradon.flashchatroom;

import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.SimpleStaticFileHandler;
import net.ion.nradon.handler.logging.LoggingHandler;
import net.ion.nradon.handler.logging.SimpleLogSink;

public class Main {

    public static void main(String[] args) throws Exception {
        Radon webServer = RadonConfiguration.newBuilder(9876)
                .add(new LoggingHandler(new SimpleLogSink(Chatroom.USERNAME_KEY)))
                .add("/chatsocket", new Chatroom())
                .add(new SimpleStaticFileHandler("./sample/net/ion/nradon/flashchatroom/content"))
                .startRadon();

        System.out.println("Chat room running on: " + webServer.getConfig().publicUri());
    }

}
