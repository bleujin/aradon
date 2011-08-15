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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ion.framework.util.Debug;
import net.ion.radon.socketio.common.DisconnectReason;
import net.ion.radon.socketio.common.SocketIOException;
import net.ion.radon.socketio.server.SocketIOFrame;
import net.ion.radon.socketio.server.SocketIOInbound;
import net.ion.radon.socketio.server.SocketIOServlet;
import net.ion.radon.socketio.server.Transport;

import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.ajax.JSON;

public class ChatSocketServlet extends SocketIOServlet {
	private static final long serialVersionUID = 1L;
	private AtomicInteger ids = new AtomicInteger(1);
	private Set<ChatConnection> connections = new HashSet<ChatConnection>();

	private class ChatConnection implements SocketIOInbound {
		private SocketIOOutbound outbound = null;
		private Integer sessionId = ids.getAndIncrement();

		private String uri ;
		public ChatConnection(String uri) {
			this.uri = uri ;
		}

		public void onConnect(SocketIOOutbound outbound) {
			this.outbound = outbound;
			synchronized (connections) {
				connections.add(this);
			}
			try {
				outbound.sendMessage(SocketIOFrame.JSON_MESSAGE_TYPE, JSON.toString(Collections.singletonMap("welcome", "Welcome to Aradon Socket Chat!")));
			} catch (SocketIOException e) {
				e.printStackTrace() ;
				outbound.disconnect();
			}
			broadcast(SocketIOFrame.JSON_MESSAGE_TYPE, JSON.toString(Collections.singletonMap("announcement", sessionId + " connected")));
		}

		public void onDisconnect(DisconnectReason reason, String errorMessage) {
			synchronized (this) {
				this.outbound = null;
			}
			synchronized (connections) {
				connections.remove(this);
			}
			broadcast(SocketIOFrame.JSON_MESSAGE_TYPE, JSON.toString(Collections.singletonMap("announcement", sessionId + " disconnected")));
		}

		public void onMessage(int messageType, String message) {
			Debug.debug("Recieved: " + message, uri);
			if (message.equals("/rclose")) {
				outbound.close();
			} else if (message.equals("/rdisconnect")) {
				outbound.disconnect();
			} else if (message.startsWith("/sleep")) {
				int sleepTime = 1;
				String parts[] = message.split("\\s+");
				if (parts.length == 2) {
					sleepTime = Integer.parseInt(parts[1]);
				}
				try {
					Thread.sleep(sleepTime * 1000);
				} catch (InterruptedException e) {
					// Ignore
				}
				try {
					outbound.sendMessage(SocketIOFrame.JSON_MESSAGE_TYPE, JSON.toString(Collections.singletonMap("message", "Slept for " + sleepTime + " seconds.")));
				} catch (SocketIOException e) {
					e.printStackTrace() ;
					outbound.disconnect();
				}
			} else {
				broadcast(SocketIOFrame.JSON_MESSAGE_TYPE, JSON.toString(Collections.singletonMap("message", new String[] { sessionId.toString(), (String) message })));
			}
		}

		private void broadcast(int messageType, String message) {
			Debug.debug("Broadcasting: " + message, uri);
			synchronized (connections) {
				for (ChatConnection c : connections) {
					if (c != this) {
						try {
							c.outbound.sendMessage(messageType, message);
						} catch (IOException e) {
							e.printStackTrace() ;
							c.outbound.disconnect();
						}
					}
				}
			}
		}
	}

	@Override
	protected SocketIOInbound doSocketIOConnect(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) cookies = new Cookie[0] ;
		for (Cookie cookie : cookies) {
			Debug.debug(cookie.getName(), cookie.getValue(), cookie.getMaxAge(), cookie.getDomain()) ;
		}
		Debug.line(request.getRequestURI(), request.getPathInfo(), request.getAttribute("subject"), cookies) ;
		return new ChatConnection(request.getRequestURI());
	}

	@Override
	protected Transport getTrasnport(String[] pathParts){
		return transportsMap.get(pathParts[0]);
	}
}
