package net.ion.nradon.netty;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.AbstractHttpHandler;

import org.junit.Ignore;
import org.junit.Test;

public class TestNettyWebServer {
	
	@Ignore
    @Test
    public void stopsServerCleanlyNotLeavingResourcesHanging() throws Exception {
        int threadCountStart = getCurrentThreadCount();
        Radon server = RadonConfiguration.newBuilder(9080).executor(Executors.newSingleThreadScheduledExecutor()).startRadon();
        assertEquals(threadCountStart + 2, getCurrentThreadCount());
        server.stop().join();
        sleep(100);
        assertEquals(threadCountStart, getCurrentThreadCount());
    }

    @Test
    public void stopsServerCleanlyAlsoWhenClientsAreConnected() throws Exception {
        final CountDownLatch stopper = new CountDownLatch(1);
        final Radon server = RadonConfiguration.newBuilder(9080).executor(Executors.newSingleThreadScheduledExecutor()).startRadon();
        server.getConfig().add(new AbstractHttpHandler() {
            public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
                server.stop().join();
                stopper.countDown();
            }
        }) ;
        
        
        Socket client = new Socket(InetAddress.getLocalHost(), 9080);
        OutputStream http = client.getOutputStream();
        http.write(("" +
                "GET /index.html HTTP/1.1\r\n" +
                "Host: www.example.com\r\n\r\n").getBytes("UTF-8"));
        http.flush();

        assertTrue("Server should have stopped by now", stopper.await(1000, TimeUnit.MILLISECONDS));
        server.stop() ;
    }

    @Test
    public void restartServerDoesNotThrowException() throws Exception {
        Radon server = RadonConfiguration.newBuilder(9080).executor(Executors.newSingleThreadScheduledExecutor()).startRadon();
        server.stop().join();
        server.start();
        server.stop().join();
    }

    @Test
    public void startServerAndTestIsRunning() throws Exception {
        NettyWebServer server = RadonConfiguration.newBuilder(9080).executor(Executors.newSingleThreadScheduledExecutor()).startRadon();
        assertTrue("Server should be running", server.isRunning());

        server.stop().join();
        assertTrue("Server should not be running", !server.isRunning());
    }

    private int getCurrentThreadCount() {
        return Thread.getAllStackTraces().keySet().size();
    }

}