package net.ion.nradon.eventsource;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.EventSourceMessage;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.SimpleStaticFileHandler;

public class Main {
    public static class Pusher {
        private List<EventSourceConnection> connections = new ArrayList<EventSourceConnection>();
        private int count = 1;

        public void addConnection(EventSourceConnection conn) {
            conn.data("id", count++);
            connections.add(conn);
            broadcast("Client " + conn.data("id") + " joined");
        }

        public void removeConnection(EventSourceConnection conn) {
            connections.remove(conn);
            broadcast("Client " + conn.data("id") + " left");
        }

        public void pushPeriodicallyOn(ExecutorService webThread) throws InterruptedException, ExecutionException {
            while (true) {
                sleep(1000);
                webThread.submit(new Runnable() {
                    public void run() {
                        broadcast(new Date().toString());
                    }
                }).get();
            }
        }

        private void broadcast(String message) {
            for (EventSourceConnection connection : connections) {
                connection.send(new EventSourceMessage(message));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService webThread = newSingleThreadExecutor();
        final Pusher pusher = new Pusher();

        Radon webServer = RadonConfiguration.newBuilder(webThread, 9876)
                .add(new SimpleStaticFileHandler("./sample/net/ion/nradon/eventsource/content/"))
                .add("/events/my", new EventSourceHandler() {
                    public void onOpen(EventSourceConnection conn) throws Exception {
                        pusher.addConnection(conn);
                    }

                    public void onClose(EventSourceConnection conn) throws Exception {
                        pusher.removeConnection(conn);
                    }
                })
                .startRadon();

        System.out.println("EventSource demo running on: " + webServer.getConfig().publicUri());

        pusher.pushPeriodicallyOn(webThread);
    }
}
