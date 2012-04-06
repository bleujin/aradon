package net.ion.nradon.client.eventsource.impl ;


import java.util.concurrent.Executor;

import net.ion.nradon.client.eventsource.EventSourceHandler;
import net.ion.nradon.client.eventsource.MessageEvent;

public class AsyncEventSourceHandler implements EventSourceHandler {
    private final Executor executor;
    private final EventSourceHandler eventSourceHandler;

    public AsyncEventSourceHandler(Executor executor, EventSourceHandler eventSourceHandler) {
        this.executor = executor;
        this.eventSourceHandler = eventSourceHandler;
    }

    public void onConnect() {
        executor.execute(new Runnable() {
            public void run() {
                try {
                    eventSourceHandler.onConnect();
                } catch (Exception e) {
                    onError(e);
                }
            }
        });
    }

    public void onMessage(final String event, final MessageEvent message) {
        executor.execute(new Runnable() {
            public void run() {
                try {
                    eventSourceHandler.onMessage(event, message);
                } catch (Exception e) {
                    onError(e);
                }
            }
        });
    }

    public void onError(final Throwable error) {
        executor.execute(new Runnable() {
            public void run() {
                try {
                    eventSourceHandler.onError(error);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
