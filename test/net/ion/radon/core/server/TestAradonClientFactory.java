package net.ion.radon.core.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Ignore;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.WritableRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

public class TestAradonClientFactory {

	@Ignore
	@Test
	public void cache() throws Exception {
		AradonClient ac1 = AradonClientFactory.create("http://www.i-on.net");
		AradonClient ac2 = AradonClientFactory.create("http://www.i-on.net");
		AradonClient ac3 = AradonClientFactory.create("http://www.i-on.net/");
		AradonClient ac4 = AradonClientFactory.create("http://www.i-on.net:80/");

		assertEquals(true, ac1 == ac2);
		assertEquals(true, ac2 == ac3);
		assertEquals(true, ac3 == ac4);
		ac1.stop() ;
		ac2.stop() ;
		ac3.stop() ;
		ac4.stop() ;
	}

	
	@Ignore
	@Test
	public void testRequest() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", LongTimeLet.class).getAradon();
		// WebServer server = WebServers.createWebServer(80).add(AradonHandler.create(aradon)).start() ;
		aradon.startServer(ConnectorConfiguration.makeSimpleHTTPConfig(9000)) ;
		
		final AradonClient ac = AradonClientFactory.create("http://localhost:9000");

		ExecutorService es = Executors.newCachedThreadPool();
		// ExecutorService es = Executors.newFixedThreadPool(2) ;
		for (int i : ListUtil.rangeNum(3)) {
			es.execute(new Runnable() {
				public void run() {
					IAradonRequest req = ac.createRequest("/test");
					InputStream input = null;
					try {
						Representation repr = req.get() ;
						input = repr.getStream() ;
						while(true){
							int b = input.read() ;
							if (b == -1) break ;
							Debug.line(b);
						}
					} catch (ResourceException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						IOUtil.closeQuietly(input); 
					}
				}
			});
		}
		es.awaitTermination(10, TimeUnit.SECONDS) ;
		aradon.stop() ;
	}
	
	
	@Ignore
	@Test
	public void testMyRequest() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", LongTimeLet.class).getAradon();
		aradon.startServer(9000) ;
		
		final AradonClient ac = AradonClientFactory.create("http://localhost:9000");
		
		for (int i : ListUtil.rangeNum(3)) {
			new MyClientThread(ac).start() ;
		}
		
		synchronized (this) {
			wait(3000);
		}
		ac.stop() ;
		aradon.stop() ;
	}
}

class MyClientThread extends Thread{
	
	private AradonClient ac ;
	MyClientThread(AradonClient ac){
		this.ac = ac ;
	}
	
	public void run(){
		try {
			IAradonRequest req = ac.createRequest("/test");
			Debug.line(req.get().getText());
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

class LongTimeLet extends AbstractServerResource {

	@Get
	public Representation longTimeGet() throws ResourceException {

		return new OutputRepresentation(MediaType.TEXT_ALL) {

			@Override
			public void write(final OutputStream output) throws IOException {
				for (int b : ListUtil.rangeNum(10)) {
					output.write(b);
					output.flush();
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				output.flush();
				IOUtil.closeQuietly(output);
			}
		};

		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// return new StringRepresentation("Hello");
	}
}


class ChannelTimeLet extends AbstractServerResource {

	@Get
	public Representation longTimeGet() throws ResourceException {

		return new WritableRepresentation(MediaType.TEXT_ALL) {
			@Override
			public void write(final WritableByteChannel output) throws IOException {
				for (int b : ListUtil.rangeNum(10)) {
					output.write(ByteBuffer.wrap(int2byte(b)));
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				IOUtil.closeQuietly(output);
			}

		};

		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// return new StringRepresentation("Hello");
	}
	public static final byte[] int2byte(int i) {
       byte[] dest = new byte[4];
       dest[3] = (byte) (i & 0xff);
       dest[2] = (byte) ((i >> 8) & 0xff);
       dest[1] = (byte) ((i >> 16) & 0xff);
       dest[0] = (byte) ((i >> 24) & 0xff);
       return dest;
	 }
}




