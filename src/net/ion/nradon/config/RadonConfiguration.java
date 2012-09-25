package net.ion.nradon.config;

import java.lang.Thread.UncaughtExceptionHandler;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Executor;

import net.ion.framework.util.InstanceCreationException;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.HttpToEventSourceHandler;
import net.ion.nradon.handler.HttpToWebSocketHandler;
import net.ion.nradon.handler.PathMatchHandler;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.config.XMLConfig;


public class RadonConfiguration {

	private Aradon aradon ;
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
	
	RadonConfiguration(Aradon aradon, URI publicUri, Executor executor, UncaughtExceptionHandler exceptionHandler, UncaughtExceptionHandler ioExceptionHandler, SocketAddress socketAddress, List<HttpHandler> handlers, 
			long staleConnectionTimeout, int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, int maxContentLength) {
		this.aradon = aradon ;
		this.publicUri = publicUri ;
		this.executor = executor ;
		this.exceptionHandler = exceptionHandler ;
		this.ioExceptionHandler = ioExceptionHandler ;
		this.socketAddress = socketAddress ;
		this.handlers = handlers ;
		
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
	
	public static RadonConfigurationBuilder newBuilder(XMLConfig xconfig) throws InstanceCreationException {
		Configuration config = ConfigurationBuilder.load(xconfig).build() ;

		return newBuilder(config.server().connector().port()).add(config);
	}

	public static RadonConfigurationBuilder newBuilder(int port, XMLConfig xconfig) throws InstanceCreationException {
		Configuration config = ConfigurationBuilder.load(xconfig).build() ;

		return newBuilder(port).add(config);
	}

	public Aradon aradon(){
		return aradon ;
	}
	
	public TreeContext getServiceContext(){
		return aradon.getServiceContext() ;
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
	

	public RadonConfiguration connectionExceptionHandler(UncaughtExceptionHandler ioExceptionHandler) {
		this.ioExceptionHandler = ioExceptionHandler ;
		return this;
	}

	public RadonConfiguration add(HttpHandler handler) {
		handlers.add(handler) ;
		return this ;
	}

	public RadonConfiguration add(String path, HttpHandler handler) {
		return add(new PathMatchHandler(path, handler));
	}

	public RadonConfiguration add(String path, WebSocketHandler handler) {
		return add(path, new HttpToWebSocketHandler(handler));
	}

	public RadonConfiguration add(String path, EventSourceHandler handler) {
		return add(path, new HttpToEventSourceHandler(handler));
	}


}
