package net.ion.nradon.config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.DateHeaderHandler;
import net.ion.nradon.handler.HttpToEventSourceHandler;
import net.ion.nradon.handler.HttpToWebSocketHandler;
import net.ion.nradon.handler.PathMatchHandler;
import net.ion.nradon.handler.ServerHeaderHandler;
import net.ion.nradon.handler.aradon.AradonHandler;
import net.ion.nradon.handler.exceptions.PrintStackTraceExceptionHandler;
import net.ion.nradon.handler.exceptions.SilentExceptionHandler;
import net.ion.nradon.netty.NettyWebServer;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.let.EPathService;
import net.ion.radon.core.let.PathService;
import net.ion.radon.core.let.WSPathService;

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
	private int maxContentLength = 65536;
	private Aradon aradon = Aradon.create();
	private boolean initializedAradon = false ;
	RadonConfigurationBuilder() {
		setupDefaultHandlers() ;
	}

	public RadonConfiguration build() {
		if (this.publicUri == null) this.publicUri = localUri(9000) ;
		if (this.executor == null) this.executor = Executors.newSingleThreadScheduledExecutor() ;
		if (this.exceptionHandler == null) this.exceptionHandler = new PrintStackTraceExceptionHandler() ;
		if (this.ioExceptionHandler == null) this.ioExceptionHandler = new SilentExceptionHandler() ;
		if (this.socketAddress == null) this.socketAddress = new InetSocketAddress(publicUri.getPort()) ;
		
		return new RadonConfiguration(this.aradon, this.publicUri, this.executor, this.exceptionHandler, this.ioExceptionHandler, this.socketAddress, this.handlers, this.staleConnectionTimeout, 
				this.maxInitialLineLength, this.maxHeaderSize, this.maxChunkSize, this.maxContentLength) ;
	}
	
	
	protected void setupDefaultHandlers() {
		handlers.add(new ServerHeaderHandler("Aradon"));
		handlers.add(new DateHeaderHandler());
	}

	public RadonConfigurationBuilder add(HttpHandler handler){
		handlers.add(handler);
		return this ;
	}

	public RadonConfigurationBuilder add(Aradon aradon) {
		// add(AradonHandler.create(aradon)) ;
		if (initializedAradon) throw new IllegalStateException("already aradon setted") ;
		this.aradon = aradon ;
		this.initializedAradon = true ;
		
		for (SectionService ss : aradon.getChildren()) {
			if (StringUtil.isBlank(ss.getName())){
				for(PathService ps : ss.getChildren()){
					IMatchMode matchMode = ps.getConfig().imatchMode();
					List<String> urlPatterns = astericURLPattern(ps.getConfig().urlPatterns(), matchMode) ;
					for (String urlPattern : urlPatterns) {
						add(urlPattern, AradonHandler.create(aradon)) ;
					}
				}
			} else {
				for(WSPathService ws : ss.getWSChildren()){
					IMatchMode matchMode = ws.getConfig().imatchMode();
					List<String> configUrlPatterns = ws.getConfig().urlPatterns();
					List<String> urlPatterns = astericURLPattern(configUrlPatterns, matchMode) ;
					for (int i = 0 ; i < urlPatterns.size() ; i++) {
						String urlPattern = "/" + ss.getName() + urlPatterns.get(i) ;
						add(urlPattern, ws.handlerResource()) ;
					}
				}
				
				for(EPathService ws : ss.getEChildren()){
					IMatchMode matchMode = ws.getConfig().imatchMode();
					List<String> configUrlPatterns = ws.getConfig().urlPatterns();
					List<String> urlPatterns = astericURLPattern(configUrlPatterns, matchMode) ;
					for (int i = 0 ; i < urlPatterns.size() ; i++) {
						String urlPattern = "/" + ss.getName() + urlPatterns.get(i) ;
						add(urlPattern, ws.handlerResource()) ;
						Debug.line(ss.getName(), urlPattern, configUrlPatterns.get(i)) ;
					}
				}
				
				add("/" + ss.getName() + "/.*", AradonHandler.create(aradon)) ;
			}
			
		}
		
		return this;
	}
	
	public RadonConfigurationBuilder add(Configuration aradonConfig) {
		Aradon aradon = Aradon.create(aradonConfig) ;
		add(aradon) ;
		return this ;
	}
	
	public List<String> astericURLPattern(List<String> urlPattrns, IMatchMode matchMode){
		List<String> result = ListUtil.newList() ;
		for (String urlPattern : urlPattrns) {
			String astericPattern = urlPattern.replaceAll("\\{[^\\/]*\\}", ".*");
			if (matchMode == IMatchMode.STARTWITH) astericPattern += "/.*" ;
			result.add(astericPattern) ;
		}
		return result ;
	}
	

	public RadonConfigurationBuilder add(String pathPattern, HttpHandler handler){
		add(new PathMatchHandler(pathPattern, handler));
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
	

	public RadonConfigurationBuilder add(String pathPattern, Aradon aradon) {
		add(pathPattern, AradonHandler.create(aradon));
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
		this.publicUri = localUri(portNum);
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
	
	private static URI localUri(int port) {
		try {
			return URI.create("http://" + InetAddress.getLocalHost().getHostName() + (port == 80 ? "" : (":" + port)) + "/");
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

	public NettyWebServer startRadon() throws IOException {
		return createRadon().start() ;
	}




}
