package net.ion.radon.impl.section;

import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.socketio.examples.chat.ChatSocketServlet;
import net.ion.radon.socketio.server.SocketIOServlet;

public class ServletInfo extends BasePathInfo{

	
	private String pname;
	private String url;
	private IMatchMode matchMode ;
	private String description ;
	private Class<? extends SocketIOServlet> servletClass;
	

	private ServletInfo(String pname, String url, String matchMode, String description, Class<? extends SocketIOServlet> servletClass) {
		this.pname = pname ;
		this.url = url ;
		this.matchMode = IMatchMode.fromString(matchMode) ;
		this.description = description ;
		this.servletClass = servletClass ;
	}

	public static ServletInfo create(String pname, String url, Class<? extends SocketIOServlet> servletClass) {
		return create(pname, url, "", "", servletClass) ;
	}

	public static ServletInfo create(String pname, String url, String matchMode, String description, Class<? extends SocketIOServlet> servletClass) {
		return new ServletInfo(pname, url, matchMode, description, servletClass);
	}
	
	

	public String getName() {
		return pname;
	}

	@Override
	public Class<? extends SocketIOServlet> getHandlerClass() {
		return servletClass ;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public IMatchMode getMatchMode() {
		return IMatchMode.EQUALS;
	}

	@Override
	public String[] getUrls() {
		return new String[]{url};
	}


}
