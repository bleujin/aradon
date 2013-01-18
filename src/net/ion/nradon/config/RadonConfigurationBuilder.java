package net.ion.nradon.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import net.ion.framework.util.ListUtil;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.DateHeaderHandler;
import net.ion.nradon.handler.HttpToEventSourceHandler;
import net.ion.nradon.handler.HttpToWebSocketHandler;
import net.ion.nradon.handler.ServerHeaderHandler;
import net.ion.nradon.handler.URIPathMatchHandler;
import net.ion.nradon.handler.exceptions.PrintStackTraceExceptionHandler;
import net.ion.nradon.handler.exceptions.SilentExceptionHandler;
import net.ion.nradon.helpers.RadonException;
import net.ion.nradon.helpers.SslFactory;
import net.ion.nradon.netty.NettyWebServer;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.config.SslParameter;

import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.routing.VirtualHost;

public class RadonConfigurationBuilder {
	private static final long DEFAULT_STALE_CONNECTION_TIMEOUT = 5000;

	private List<HttpHandler> handlers = ListUtil.newList() ;
	
	private URI publicUri ;
	private Executor executor = null ;
	
	private Thread.UncaughtExceptionHandler exceptionHandler;
	private Thread.UncaughtExceptionHandler ioExceptionHandler;
	private SocketAddress socketAddress;
	
	private long staleConnectionTimeout = DEFAULT_STALE_CONNECTION_TIMEOUT;
	private int maxInitialLineLength = 4096;
	private int maxHeaderSize = 8192;
	private int maxChunkSize = 8192;
	private int maxContentLength = 1024 * 1024 * 20; // 20M
	private TreeContext rootContext = null;
	
    private SSLContext sslContext;
    
	private Protocol protocol = Protocol.HTTP;
	private int portNum = 9000 ;

	RadonConfigurationBuilder() {
		setupDefaultHandlers() ;
	}

	public RadonConfiguration build() {
		if (this.rootContext == null) this.rootContext = TreeContext.createRootContext(new VirtualHost(new Context()));
		if (this.publicUri == null) this.publicUri = localUri(this.protocol, this.portNum) ;
		if (this.executor == null) this.executor = Executors.newSingleThreadScheduledExecutor() ;
		if (this.exceptionHandler == null) this.exceptionHandler = new PrintStackTraceExceptionHandler() ;
		if (this.ioExceptionHandler == null) this.ioExceptionHandler = new SilentExceptionHandler() ;
		if (this.socketAddress == null) this.socketAddress = new InetSocketAddress(publicUri.getPort()) ;
		
		return new RadonConfiguration(this.rootContext, this.publicUri, this.executor, this.exceptionHandler, this.ioExceptionHandler, this.socketAddress, this.handlers, this.sslContext, 
				this.staleConnectionTimeout, this.maxInitialLineLength, this.maxHeaderSize, this.maxChunkSize, this.maxContentLength) ;
	}
	
	
	protected void setupDefaultHandlers() {
		handlers.add(new ServerHeaderHandler("Aradon"));
		handlers.add(new DateHeaderHandler());
	}

	public RadonConfigurationBuilder add(HttpHandler handler){
		handlers.add(handler);
		return this ;
	}

	public List<String> astericURLPattern(List<String> urlPattrns, IMatchMode matchMode){
		List<String> result = ListUtil.newList() ;
		for (String urlPattern : urlPattrns) {
			String astericPattern = urlPattern.replaceAll("\\{[^\\/]*\\}", ".*");
			if (matchMode == IMatchMode.STARTWITH) astericPattern += urlPattern.equals("/") ? ".*" : "/.*" ;
			result.add(astericPattern) ;
		}
		return result ;
	}
	

	public RadonConfigurationBuilder add(String pathPattern, HttpHandler handler){
		add(new URIPathMatchHandler(pathPattern, handler));
		return this ;
	}

	public RadonConfigurationBuilder add(String pathPattern, WebSocketHandler handler){
		add(pathPattern, new HttpToWebSocketHandler(handler));
		return this ;
	}

	public RadonConfigurationBuilder add(String pathPattern, EventSourceHandler handler){
		add(pathPattern, new HttpToEventSourceHandler(handler));
		return this ;
	}
	
	public RadonConfigurationBuilder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler exceptionHandler){
		this.exceptionHandler = exceptionHandler;
		return this ;
	}

	public RadonConfigurationBuilder connectionExceptionHandler(Thread.UncaughtExceptionHandler ioExceptionHandler){
		this.ioExceptionHandler = ioExceptionHandler;
		return this ;
	}

	public RadonConfigurationBuilder port(int portNum){
		this.portNum = portNum ;
		return this ;
	}

	public RadonConfigurationBuilder executor(Executor executor){
		this.executor = executor ;
		return this ;
	}

	public RadonConfigurationBuilder staleConnectionTimeout(long millis){
		this.staleConnectionTimeout = millis;
		return this ;
	}
	
	static URI localUri(Protocol protocol,  int port) {
		try {
			return URI.create(protocol.getSchemeName() + "://" + InetAddress.getLocalHost().getHostName() + (port == 80 ? "" : (":" + port)) + "/");
		} catch (UnknownHostException e) {
			throw new IllegalStateException(e) ;
		}
	}
	
	public RadonConfigurationBuilder maxChunkSize(int maxChunkSize) {
		this.maxChunkSize = maxChunkSize;
		return this;
	}

	public RadonConfigurationBuilder maxContentLength(int maxContentLength) {
		this.maxContentLength = maxContentLength;
		return this;
	}

	public RadonConfigurationBuilder maxHeaderSize(int maxHeaderSize) {
		this.maxHeaderSize = maxHeaderSize;
		return this;
	}

	public RadonConfigurationBuilder maxInitialLineLength(int maxInitialLineLength) {
		this.maxInitialLineLength = maxInitialLineLength;
		return this;
	}

	public RadonConfigurationBuilder publicUri(URI publicUri) {
		this.publicUri = publicUri ;
		return this;
	}

	public RadonConfigurationBuilder socketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress ;
		return this;
	}

	public NettyWebServer createRadon() {
		return new NettyWebServer(build());
	}

	public Future<Radon> start() {
		return createRadon().start() ;
	}

	public Radon startRadon() throws InterruptedException, ExecutionException {
		return start().get() ;
	}


	public SSLContext getSslContext(){
		return sslContext ;
	}
	
    public RadonConfigurationBuilder setupSsl(InputStream keyStorePath, String pass) throws RadonException {
        return this.setupSsl(keyStorePath, pass, pass);
    }

    public RadonConfigurationBuilder setupSsl(InputStream keyStorePath, String keyStorePass, String keyPass) throws RadonException {
        this.sslContext = new SslFactory(keyStorePath, keyStorePass).getServerContext(keyPass);
        return this;
    }

	public RadonConfigurationBuilder setupSsl(SslParameter sslParam) throws FileNotFoundException {
		final InputStream fis = new FileInputStream(new File(sslParam.keystorePath()));
		this.sslContext = new SslFactory(fis, sslParam.keystorePassword()).getServerContext(sslParam.keyPassword()) ;
		return this ;
	}

	public RadonConfigurationBuilder protocol(Protocol protocol) {
		this.protocol = protocol ;
		return this;
	}

	public RadonConfigurationBuilder rootContext(TreeContext rootContext) {
		this.rootContext = rootContext ;
		return this ;
	}

}
