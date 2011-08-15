package net.ion.radon.core;

import static net.ion.radon.core.RadonAttributeKey.*;

import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.config.AttributeUtil;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.impl.section.PluginConfig;
import net.ion.radon.socketio.DefinedResourceLet;
import net.ion.radon.socketio.StaticFileLet;
import net.ion.radon.socketio.server.SocketIOServlet;
import net.ion.radon.socketio.server.transport.FlashSocketTransport;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.ClassUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Router;

public class NotificationSection extends SectionService {

	private TreeContext context;
	private Aradon aradon;
	private String sectionName;
	private Router router;
	private Server innerServer;
	private String host;
	private int port;

	private Map<String, Class<? extends SocketIOServlet>> serverHandlerClass = MapUtil.newMap();

	NotificationSection(Aradon aradon, String sectionName, TreeContext scontext, String host, int port) {
		super(sectionName, scontext);
		this.aradon = aradon;
		this.sectionName = sectionName;
		this.context = scontext;
		this.host = host;
		this.port = port;
		this.router = new Router();
	}

	public static NotificationSection create(Aradon aradon, String sectionName, String host, int port) {
		TreeContext scontext = aradon.getServiceContext().createChildContext();

		return new NotificationSection(aradon, sectionName, scontext, host, port);
	}


	@Override
	public Restlet createInboundRoot() {
		return router;
	}

	@Override public synchronized void start() throws Exception {
		if (!isStarted()) {
			super.start();

			this.attach(PathInfo.create("socket.io.js", "/socket.io.js", DefinedResourceLet.class));
			this.attach(PathInfo.create("WebSocketMain.swf", "/WebSocketMain.swf", DefinedResourceLet.class));

			innerServer = startServer();
		}
	}
 
	public synchronized void stop() throws Exception {
		if (!isStopped()) {
			super.stop();
			if (innerServer != null) {
				innerServer.setStopAtShutdown(true);
				for (Handler handler : innerServer.getChildHandlers()) {
					handler.destroy();
				}
				innerServer.destroy();
				innerServer.stop();
			}
		}
	}

	@Override
	public void attach(BasePathInfo pathInfo) {

		TreeContext pcontext = context.createChildContext();
		PathService pservice = PathService.create(this, pcontext, pathInfo);

		attach(pathInfo, pservice);
	}

	void attach(BasePathInfo pathInfo, PathService pservice) {
		PathInfo wrapperInfo = null;
		if (isWrapper(pathInfo.getHandlerClass())) {
			serverHandlerClass.put(pathInfo.getName(), pathInfo.getHandlerClass());
			wrapperInfo = PathInfo.create(pathInfo.getName(), StringUtil.join(pathInfo.getUrls(), ","), StaticFileLet.class);
		} else {
			wrapperInfo = PathInfo.create(pathInfo.getName(), StringUtil.join(pathInfo.getUrls(), ","), pathInfo.getHandlerClass());
		}

		if (pathServices.containsKey(wrapperInfo.getName()))
			throw new IllegalArgumentException("already exist path name :" + wrapperInfo.toString());
		pathServices.put(wrapperInfo.getName(), pservice);

		for (String urlPattern : wrapperInfo.getUrls()) {
			router.attach(urlPattern, wrapperInfo.getHandlerClass(), wrapperInfo.getMatchMode().toRouterMode());
		}
	}

	private boolean isWrapper(Class handlerClass) {
		return ClassUtils.getAllSuperclasses(handlerClass).contains(SocketIOServlet.class);
	}

	@Override
	public void handle(Request request, Response response) {
		final Reference pathReference = ((InnerRequest) request).getPathReference();
		for (PathService pservice : pathServices.values()) {
			if (pservice.getPathInfo().isMatchURL(pathReference)) {
				TreeContext requestContext = pservice.createChildContext();
				request.getAttributes().put(REQUEST_CONTEXT, requestContext);

				super.handle(request, response);
				return;
			}
		}
		throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "section:" + getName() + ", " + "path:" + pathReference);
		// Debug.debug(request.getResourceRef().getRemainingPart()) ;
		// super.handle(request, response);
	}

	@Override
	public PluginConfig getPluginConfig() {
		return PluginConfig.EMPTY;
	}

	public Aradon getAradon() {
		return aradon;
	}

	public void reload() throws Exception {
		// @TODO not impl
		getAradon().reload();
	}

	private Server startServer() throws Exception {

		final Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setHost(host);
		connector.setPort(port);

		server.addConnector(connector);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		for (Entry<String, Class<? extends SocketIOServlet>> entry : serverHandlerClass.entrySet()) {

			SocketIOServlet ss = (SocketIOServlet) entry.getValue().newInstance();
			ServletHolder holder = new ServletHolder(ss);
			holder.setInitParameter(FlashSocketTransport.FLASHPOLICY_SERVER_HOST_KEY, host);
			holder.setInitParameter(FlashSocketTransport.FLASHPOLICY_DOMAIN_KEY, host);
			holder.setInitParameter(FlashSocketTransport.FLASHPOLICY_PORTS_KEY, "" + port);
			context.addServlet(holder, "/" + entry.getKey() + "/*");
		}
		// context.addServlet(holder, "/socket.io/*");
		// context.addServlet(new ServletHolder(new StaticServlet()), "/*");

		server.setHandler(context);
		server.start();
		getLogger().warning(sectionName + " server started[" + this.port + "]");

		return server;
	}

}
