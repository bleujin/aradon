package net.ion.nradon.flashchatroom;

import net.ion.nradon.WebServer;
import net.ion.nradon.handler.StaticFileHandler;
import net.ion.nradon.handler.logging.LoggingHandler;
import net.ion.nradon.handler.logging.SimpleLogSink;

import static net.ion.nradon.WebServers.createWebServer;

public class Main {

    public static void main(String[] args) throws Exception {
        WebServer webServer = createWebServer(9876)
                .add(new LoggingHandler(new SimpleLogSink(Chatroom.USERNAME_KEY)))
                .add("/chatsocket", new Chatroom())
                .add(new StaticFileHandler("./sample/net/ion/nradon/flashchatroom/content"))
                .start();

        System.out.println("Chat room running on: " + webServer.getUri());
    }

}
