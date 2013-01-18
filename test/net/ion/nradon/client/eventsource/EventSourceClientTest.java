package net.ion.nradon.client.eventsource;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceMessage;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class EventSourceClientTest {
    private Radon webServer;
    private EventSource eventSource;

    @Before
    public void createServer() {
        webServer = RadonConfiguration.newBuilder(59504).createRadon();
    }

    @After
    public void die() throws IOException, InterruptedException, ExecutionException {
        eventSource.close().join();
        webServer.stop().get() ;
    }

    @Test
    public void canSendAndReadTwoSingleLineMessages() throws Exception {
        assertSentAndReceived(asList("a", "b"));
    }

    @Test
    public void canSendAndReadThreeSingleLineMessages() throws Exception {
        assertSentAndReceived(asList("C", "D", "E"));
    }

    @Test
    public void canSendAndReadOneMultiLineMessages() throws Exception {
        assertSentAndReceived(asList("f\ng\nh"));
    }

    @Test
    public void reconnectsIfServerIsDownAtCreationTime() throws Exception {
        List<String> messages = asList("a", "b");
        CountDownLatch messageCountdown = new CountDownLatch(messages.size());
        CountDownLatch errorCountdown = new CountDownLatch(1);
        startClient(messages, messageCountdown, errorCountdown, 100);
        startServer(messages);
        assertTrue("Didn't get an error on first failed connection", errorCountdown.await(2000, TimeUnit.MILLISECONDS));
        assertTrue("Didn't get all messages", messageCountdown.await(2000, TimeUnit.MILLISECONDS));
    }

    @Test
    @Ignore // Because of https://github.com/joewalnes/webbit/issues/29
    public void reconnectsIfServerGoesDownAfterConnectionEstablished() throws Exception {
        final CountDownLatch messageOneCountdown = new CountDownLatch(1);
        final CountDownLatch messageTwoCountdown = new CountDownLatch(1);
        final CountDownLatch errorCountdown = new CountDownLatch(1);

        webServer.getConfig()
                .add("/es/.*", new net.ion.nradon.EventSourceHandler() {
                    public int counter = 1;

                    public void onOpen(EventSourceConnection connection) throws Exception {
                        connection.send(new EventSourceMessage(Integer.toString(counter++)));
                    }

                    public void onClose(EventSourceConnection connection) throws Exception {
                    }
                }) ;
        webServer.start();


        eventSource = new EventSource(Executors.newSingleThreadExecutor(), 100, URI.create("http://localhost:59504/es/hello"), new EventSourceHandler() {
            public void onConnect() {
            }

            public void onMessage(String event, MessageEvent message) throws IOException {
                if (message.data.equals("1")) {
                    messageOneCountdown.countDown();
                } else if (message.data.equals("2")) {
                    messageTwoCountdown.countDown();
                } else {
                    throw new RuntimeException("Bad message");
                }
            }

            public void onError(Throwable t) {
                System.out.println("ERROR: " + t);
                errorCountdown.countDown();
            }
        });
        eventSource.connect();

        assertTrue("Didn't get 1st message", messageOneCountdown.await(1000, TimeUnit.MILLISECONDS));

        System.out.println("Stopping server..");
        webServer.stop().get() ;
        System.out.println("Stopped");
        System.out.println("KILLED");

        assertTrue("Didn't get an error on first failed connection", errorCountdown.await(1000, TimeUnit.MILLISECONDS));

        webServer.start();
        assertTrue("Didn't get all messages", messageTwoCountdown.await(1000, TimeUnit.MILLISECONDS));
    }

    private void assertSentAndReceived(final List<String> messages) throws IOException, InterruptedException {
        startServer(messages);
        CountDownLatch messageCountdown = new CountDownLatch(messages.size());
        startClient(messages, messageCountdown, new CountDownLatch(0), 5000);
        assertTrue("Didn't get all messages", messageCountdown.await(1000, TimeUnit.MILLISECONDS));
    }

    private void startClient(final List<String> expectedMessages, final CountDownLatch messageCountdown, final CountDownLatch errorCountdown, long reconnectionTimeMillis) throws InterruptedException {
        eventSource = new EventSource(Executors.newSingleThreadExecutor(), reconnectionTimeMillis, URI.create("http://localhost:59504/es/hello?echoThis=yo"), new EventSourceHandler() {
            int n = 0;

            public void onConnect() {
            }

            public void onMessage(String event, net.ion.nradon.client.eventsource.MessageEvent message) {
                assertEquals(expectedMessages.get(n++) + " yo", message.data);
                assertEquals("http://localhost:59504/es/hello?echoThis=yo", message.origin);
                messageCountdown.countDown();
            }

            public void onError(Throwable t) {
                errorCountdown.countDown();
            }
        });
        eventSource.connect().await();
    }

    private void startServer(final List<String> messagesToSend) throws IOException {
        webServer.getConfig()
                .add("/es/.*", new net.ion.nradon.EventSourceHandler() {
                    public void onOpen(EventSourceConnection connection) throws Exception {
                        for (String message : messagesToSend) {
                            String data = message + " " + connection.httpRequest().queryParam("echoThis");
                            connection.send(new EventSourceMessage(data));
                        }
                    }

                    public void onClose(EventSourceConnection connection) throws Exception {
                    }
                }) ;
        webServer.start();
    }
}
