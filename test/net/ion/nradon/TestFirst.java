package net.ion.nradon;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;
import net.ion.nradon.handler.AbstractHttpHandler;
import net.ion.nradon.servlet.HelloServlet;

public class TestFirst extends TestCase{

	
	public void testHttpServer() throws Exception {
		final CountDownLatch latch = new CountDownLatch(1) ;
		
		final AtomicReference<String> uri = new AtomicReference<String>() ;
		final AtomicReference<String> hostHeader = new AtomicReference<String>() ;
		
		RadonConfigurationBuilder builder = RadonConfiguration.newBuilder(Executors.newSingleThreadExecutor(), 9000);
		Radon radon = builder.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				uri.set(request.uri()) ;
				hostHeader.set(request.header("Host"));
				latch.countDown(); 
			}
		}).start().get() ;
		
		
		Socket client = new Socket(InetAddress.getLocalHost(), 9000) ;
		OutputStream output = client.getOutputStream() ;
		output.write(("GET /index.html HTTP/1.0\r\n" + 	
					"Host: www.aradon.com\r\n\r\n").getBytes("UTF-8"));
		output.flush(); 
		
		latch.await(1000, TimeUnit.SECONDS) ;
		radon.stop().get() ;
		
		assertEquals("/index.html", uri.get());
		assertEquals("www.aradon.com", hostHeader.get());
	}
	
	
	public void testServlet() throws Exception {
		final CountDownLatch latch = new CountDownLatch(1) ;
		final HelloServlet servlet = new HelloServlet() ;
		servlet.init(); 
		RadonConfigurationBuilder builder = RadonConfiguration.newBuilder(Executors.newCachedThreadPool(), 9000);
		Radon radon = builder.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				servlet.doGet(new HttpServletRequestRadon(request), new HttpServletResponseRadon(response));
				response.end() ;
				latch.countDown();
			}
		}).start().get() ;
		

		Socket client = new Socket(InetAddress.getLocalHost(), 9000) ;
		OutputStream output = client.getOutputStream() ;
		output.write(("GET /index.html HTTP/1.0\r\n" + 	
					"Host: www.aradon.com\r\n\r\n").getBytes("UTF-8"));
		output.flush();
		latch.await(1000, TimeUnit.SECONDS) ;
		
		InputStream input = client.getInputStream();
		Debug.line(IOUtil.toStringWithClose(input)) ;
        input.close(); 
		
		servlet.destroy(); 
		radon.stop().get() ;
	}

}
