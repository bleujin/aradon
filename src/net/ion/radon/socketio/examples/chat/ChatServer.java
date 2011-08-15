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

package net.ion.radon.socketio.examples.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ion.framework.util.IOUtil;
import net.ion.radon.socketio.server.transport.FlashSocketTransport;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.restlet.ext.servlet.ServletUtils;



public class ChatServer {
	private static class StaticServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String path = req.getRequestURI();
			path = path.substring(req.getContextPath().length());
			if ("/json.js".equals(path)) {
				resp.setContentType("text/javascript");
				InputStream is = this.getClass().getClassLoader().getResourceAsStream("net/ion/bleujin/socketio/examples/chat/json.js");
				OutputStream os = resp.getOutputStream();
				IOUtil.copy(is, os) ;
				IOUtil.closeQuietly(is) ;
			} else if ("/socket.io.js".equals(path)) {
					resp.setContentType("text/javascript");
					InputStream is = this.getClass().getClassLoader().getResourceAsStream("net/ion/bleujin/socketio/socket.io.js");
					OutputStream os = resp.getOutputStream();
					IOUtil.copy(is, os) ;
					IOUtil.closeQuietly(is) ;
			} else if (path.endsWith("/chat.html")) {
				resp.setContentType("text/html");
				resp.addCookie(new Cookie("subject", "test")) ;
				InputStream is = this.getClass().getClassLoader().getResourceAsStream("net/ion/bleujin/socketio/examples/chat/chat.html");
				OutputStream os = resp.getOutputStream();
				IOUtil.copy(is, os) ;
				IOUtil.closeQuietly(is) ;
			} else {
				resp.sendRedirect("/chat.html");
			}
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String host = "61.250.201.78";
		int port = 8080;

		if (args.length > 0) {
			host = args[0];
		}

		if (args.length > 1) {
			port = Integer.parseInt(args[1]);
		}

		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setHost(host);
		connector.setPort(port);

		server.addConnector(connector);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		ServletHolder holder = new ServletHolder(new ChatSocketServlet());
		holder.setInitParameter(FlashSocketTransport.FLASHPOLICY_SERVER_HOST_KEY, host);
		holder.setInitParameter(FlashSocketTransport.FLASHPOLICY_DOMAIN_KEY, host);
		holder.setInitParameter(FlashSocketTransport.FLASHPOLICY_PORTS_KEY, "" + port);
		context.addServlet(holder, "/socket.io/*");
		context.addServlet(new ServletHolder(new StaticServlet()), "/*");

		server.setHandler(context);
		server.start();
		
	}

}
