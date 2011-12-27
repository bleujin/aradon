/**
 * The MIT License
 * Copyright (c) 2010 Tad Glines
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.ion.radon.socketio.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ion.radon.socketio.server.transport.FlashSocketTransport;
import net.ion.radon.socketio.server.transport.HTMLFileTransport;
import net.ion.radon.socketio.server.transport.JSONPPollingTransport;
import net.ion.radon.socketio.server.transport.WebSocketTransport;
import net.ion.radon.socketio.server.transport.XHRMultipartTransport;
import net.ion.radon.socketio.server.transport.XHRPollingTransport;

/**
 */
public abstract class SocketIOServlet extends GenericServlet {
	public static final String BUFFER_SIZE_INIT_PARAM = "bufferSize";
	public static final String MAX_IDLE_TIME_INIT_PARAM = "maxIdleTime";
	public static final int BUFFER_SIZE_DEFAULT = 8192;
	public static final int MAX_IDLE_TIME_DEFAULT = 300 * 1000;
	private static final long serialVersionUID = 1L;
	private SocketIOSessionManager sessionManager = null;
	protected Map<String, Transport> transportsMap = new HashMap<String, Transport>();

	@Override
	public void init() throws ServletException {
		super.init();
		String str = this.getInitParameter(BUFFER_SIZE_INIT_PARAM);
		int bufferSize = str == null ? BUFFER_SIZE_DEFAULT : Integer.parseInt(str);
		str = this.getInitParameter(MAX_IDLE_TIME_INIT_PARAM);
		int maxIdleTime = str == null ? MAX_IDLE_TIME_DEFAULT : Integer.parseInt(str);

		sessionManager = new SocketIOSessionManager();
		addTrasnport(new WebSocketTransport(bufferSize, maxIdleTime),  new FlashSocketTransport(bufferSize, maxIdleTime),  new HTMLFileTransport(bufferSize, maxIdleTime)
			, new XHRMultipartTransport(bufferSize, maxIdleTime), new XHRPollingTransport(bufferSize, maxIdleTime), new JSONPPollingTransport(bufferSize, maxIdleTime)) ;
		
		for (Transport t : transportsMap.values()) {
			t.init(this.getServletConfig());
		}
	}

	private void addTrasnport(Transport... transports) {
		for (Transport tran : transports) {
			transportsMap.put(tran.getName(), tran);
		}
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		service((HttpServletRequest) request, (HttpServletResponse) response);
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("OPTIONS".equals(request.getMethod())) {
			// TODO: process and reply to CORS preflight request.
			return;
		}

		String path = request.getPathInfo();
		if (path == null || path.length() == 0 || "/".equals(path)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing SocketIO transport");
			return;
		}
		if (path.startsWith("/"))
			path = path.substring(1);

		Transport transport = getTrasnport(path.split("/"));
		if (transport == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown SocketIO transport");
			return;
			
//			if ("GET".equals(request.getMethod()) &&  path.endsWith("socket.io.js")) {
//				response.setContentType("text/javascript");
//				//InputStream is = this.getClass().getClassLoader().getResourceAsStream("net/ion/bleujin/socketio/socket.io.js");
//				InputStream is = new FileInputStream("test/net/ion/bleujin/socketio/socket.io.js");
//				OutputStream os = response.getOutputStream();
//				IO.copy(is, os);
//				return;
//			} else {
//				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown SocketIO transport");
//				return;
//			}
		}

		transport.handle(request, response, new Transport.InboundFactory() {

			public SocketIOInbound getInbound(HttpServletRequest request, String[] protocols) {
				return SocketIOServlet.this.doSocketIOConnect(request);
			}

		}, sessionManager);
	}
	
	protected Transport getTrasnport(String[] pathParts){
		return transportsMap.get(pathParts[0]);
	}
	

	public void destroy() {
		for (Transport t : transportsMap.values()) {
			t.destroy();
		}
		super.destroy();
	}

	/**
	 * Returns an instance of SocketIOInbound or null if the connection is to be denied. The value of cookies and protocols may be null.
	 */
	protected abstract SocketIOInbound doSocketIOConnect(HttpServletRequest request);
}
