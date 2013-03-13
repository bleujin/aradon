package net.ion.nradon.config;

import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.net.ssl.SSLContext;

import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.HttpToEventSourceHandler;
import net.ion.nradon.handler.HttpToWebSocketHandler;
import net.ion.nradon.handler.URIPathMatchHandler;
import net.ion.nradon.helpers.SslFactory;
import net.ion.radon.core.TreeContext;


public class RadonConfiguration {

	private TreeContext rootContext ;
	private URI publicUri ;
	private Executor executor ;
	private UncaughtExceptionHandler exceptionHandler ;
	private UncaughtExceptionHandler ioExceptionHandler ;
	private SocketAddress socketAddress ;
	private List<HttpHandler> handlers ; 
	private long staleConnectionTimeout ;
	private int maxInitialLineLength ;
	private int maxHeaderSize ;
	private int maxChunkSize ;
	private int maxContentLength ;
	private SSLContext sslContext;
	
	RadonConfiguration(TreeContext rootContext, URI publicUri, Executor executor, UncaughtExceptionHandler exceptionHandler, UncaughtExceptionHandler ioExceptionHandler, SocketAddress socketAddress, List<HttpHandler> handlers, SSLContext sslContext,  
			long staleConnectionTimeout, int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, int maxContentLength) {
		this.rootContext = rootContext ;
		this.publicUri = publicUri ;
		this.executor = executor ;
		this.exceptionHandler = exceptionHandler ;
		this.ioExceptionHandler = ioExceptionHandler ;
		this.socketAddress = socketAddress ;
		this.handlers = handlers ;
		this.sslContext = sslContext ;
		
		this.staleConnectionTimeout = staleConnectionTimeout ;
		this.maxInitialLineLength = maxInitialLineLength ;
		this.maxHeaderSize = maxHeaderSize ;
		this.maxChunkSize = maxChunkSize ;
		this.maxContentLength = maxContentLength ;
	}
	
	public final static RadonConfigurationBuilder newBuilder(int portNum){
		return new RadonConfigurationBuilder().port(portNum) ;
	}
	
	public static RadonConfigurationBuilder newBuilder(Executor executor, int port) {
		return RadonConfiguration.newBuilder(port).executor(executor).port(port);
	}

	public static RadonConfigurationBuilder newBuilder(Executor executor, SocketAddress socketAddress, URI publicUri) {
		return RadonConfiguration.newBuilder(publicUri.getPort()).executor(executor).socketAddress(socketAddress).publicUri(publicUri) ;
	}
	
	public TreeContext getServiceContext(){
		return rootContext ;
	}
	
	public URI publicUri() {
		return publicUri;
	}

	public Executor executor() {
		return executor;
	}

	public UncaughtExceptionHandler exceptionHandler() {
		return exceptionHandler;
	}

	public UncaughtExceptionHandler ioExceptionHandler() {
		return ioExceptionHandler;
	}

	public SocketAddress socketAddress() {
		return socketAddress;
	}

	public List<HttpHandler> handlers() {
		return handlers;
	}

	public long staleConnectionTimeout() {
		return staleConnectionTimeout;
	}

	public int maxInitialLineLength() {
		return maxInitialLineLength;
	}

	public int maxHeaderSize() {
		return maxHeaderSize;
	}

	public int maxChunkSize() {
		return maxChunkSize;
	}

	public int maxContentLength() {
		return maxContentLength;
	}
	

	public RadonConfiguration portNum(int portNum) throws URISyntaxException {
		this.publicUri = new URI(publicUri.getScheme(), publicUri.getUserInfo(), publicUri.getHost(), portNum, publicUri.getPath(), publicUri.getQuery(), publicUri.getFragment()) ;
		this.socketAddress = new InetSocketAddress(portNum) ;
		return this ;
	}

	public int getPort() {
		int port = publicUri.getPort();
		if (port == -1){
			if (publicUri.getScheme().startsWith("https")) return 443 ;
			if (publicUri.getScheme().startsWith("http")) return 80 ;
		}
		return port;
	}
	

	public RadonConfiguration uncaughtExceptionHandler(UncaughtExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler ;
		return this;
	}

	public RadonConfiguration ioExceptionHandler(UncaughtExceptionHandler ioExceptionHandler) {
		this.ioExceptionHandler = ioExceptionHandler ;
		return this;
	}

	public RadonConfiguration add(HttpHandler handler) {
		handlers.add(handler) ;
		return this ;
	}

	public RadonConfiguration add(String path, HttpHandler handler) {
		return add(new URIPathMatchHandler(path, handler));
	}

	public RadonConfiguration add(String path, WebSocketHandler handler) {
		return add(path, new HttpToWebSocketHandler(handler));
	}

	public RadonConfiguration add(String path, EventSourceHandler handler) {
		return add(path, new HttpToEventSourceHandler(handler));
	}

	public SSLContext getSslContext() {
		return sslContext;
	}
	public RadonConfiguration setUpSsl(InputStream keyStore, String pass) {
		return setUpSsl(keyStore, pass, pass) ;
	}

	public RadonConfiguration setUpSsl(InputStream keyStore, String pass, String keyPass) {
		this.sslContext = new SslFactory(keyStore, pass).getServerContext(keyPass) ;
		return this ;
	}


}
