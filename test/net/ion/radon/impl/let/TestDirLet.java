package net.ion.radon.impl.let;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.EventSourceMessage;
import net.ion.nradon.Radon;
import net.ion.nradon.ajax.BroadEchoWebSocket;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.SimpleStaticFileHandler;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestDirLet {

	
	@Test
	public void getFile() throws Exception{
		Aradon aradon = AradonTester.create().register("", "/{file}", DirLet.class).getAradon() ;
		aradon.getServiceContext().putAttribute("base.dir", "./resource") ;
		
		aradon.start() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Response response = ac.createRequest("/ptest.prop").handle(Method.GET) ;
		
		Debug.debug(response.getEntityAsText()) ;
		
		aradon.stop() ;
		
	}
	
	@Test
	public void testSlideAradon() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/{file}", IMatchMode.STARTWITH, DirLet.class).getAradon() ;
		aradon.getServiceContext().putAttribute("base.dir", "./resource/slide") ;
		
		aradon.startServer(ConnectorConfiguration.makeJettyHTTPConfig(9000)) ;
		
//		aradon.startServer(9000) ;
		new InfinityThread().startNJoin() ;
	}
	

	@Test
	public void testSlideTeam() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/{file}", IMatchMode.STARTWITH, DirLet.class).getAradon() ;
		aradon.getServiceContext().putAttribute("base.dir", "./resource/docs") ;
		
		aradon.startServer(ConnectorConfiguration.makeJettyHTTPConfig(9000)) ;
		
//		aradon.startServer(9000) ;
		new InfinityThread().startNJoin() ;
	}
	
	
	@Test
	public void testSlide() throws Exception {
		ExecutorService webThread = newSingleThreadExecutor();
		final Pusher pusher = new Pusher();
		Radon radon = RadonConfiguration.newBuilder(9000)
			.add("/websocket/{id}", new BroadEchoWebSocket())
			.add("/events/{id}", new EventSourceHandler() {
                public void onOpen(EventSourceConnection conn) throws Exception {
                    pusher.addConnection(conn);
                }

                public void onClose(EventSourceConnection conn) throws Exception {
                    pusher.removeConnection(conn);
                }
            })
			.add("/*", new SimpleStaticFileHandler(new File("./resource/slide/"))).createRadon() ;
		
		radon.start().get() ;
		pusher.pushPeriodicallyOn(webThread);
		
//		Aradon aradon = AradonTester.create().register("", "/{file}", "file", IMatchMode.STARTWITH, DirLet.class).getAradon() ;
//		aradon.getServiceContext().putAttribute("base.dir", "./resource/slide") ;
//		
//		aradon.startServer(9000) ;
		new InfinityThread().startNJoin() ;
	}
	
	 private static class Pusher {
	        private List<EventSourceConnection> connections = new CopyOnWriteArrayList<EventSourceConnection>() ;
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
		        	Thread.sleep(1000 * 2) ;
	                webThread.submit(new Runnable() {
	                    public void run() {
	                        broadcast(new Date().toString());
	                    }
	                }).get();
	            }
	        }

	        private void broadcast(String message) {
	            for (EventSourceConnection connection : connections) {
	                connection.send(new EventSourceMessage(message + ", clients:" + connections.size()));
	            }
	        }
	    }
}
