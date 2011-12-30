package net.ion.nradon.handler;

import static net.ion.nradon.WebServers.createWebServer;
import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.httpGet;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.ListUtil;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebServer;

import org.junit.After;
import org.junit.Test;
import org.restlet.data.Cookie;
import org.restlet.engine.http.header.CookieReader;
import org.restlet.engine.http.header.CookieWriter;

public class TestCookie {
	private WebServer webServer = createWebServer(59504);

	@After
	public void die() throws IOException, InterruptedException {
		webServer.stop().join();
	}

	@Test
	public void setsOneOutboundCookie() throws IOException, InterruptedException {
		webServer.add(new HttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				response.cookie(new Cookie("a", "b")).end();
			}
		}).start();
		URLConnection urlConnection = httpGet(webServer, "/");
		List<Cookie> cookies = cookies(urlConnection);
		assertEquals(1, cookies.size());
		assertEquals("a", cookies.get(0).getName());
		assertEquals("b", cookies.get(0).getValue());
	}

	@Test
	public void setsTwoOutboundCookies() throws IOException, InterruptedException {
		webServer.add(new HttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				response.cookie(new Cookie("a", "b")).cookie(new Cookie("c", "d")).end();
			}
		}).start();
		URLConnection urlConnection = httpGet(webServer, "/");
		List<Cookie> cookies = cookies(urlConnection);
		assertEquals(2, cookies.size());
		assertEquals("a", cookies.get(0).getName());
		assertEquals("b", cookies.get(0).getValue());
		assertEquals("c", cookies.get(1).getName());
		assertEquals("d", cookies.get(1).getValue());
	}

	@Test
	public void parsesOneInboundCookie() throws IOException, InterruptedException {
		webServer.add(new HttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				String body = "Your cookie value: " + request.cookieValue("someName");
				response.header("Content-Length", body.length()).content(body).end();
			}
		}).start();
		URLConnection urlConnection = httpGet(webServer, "/");
		urlConnection.addRequestProperty("Cookie", "someName=someValue");
		assertEquals("Your cookie value: someValue", contents(urlConnection));
	}

	@Test
	public void parsesThreeInboundCookiesInTwoHeaders() throws IOException, InterruptedException {
		webServer.add(new HttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				String body = "Your cookies:";
				List<Cookie> cookies = sort(request.cookies());
				for (Cookie cookie : cookies) {
					body += " " + cookie.getName() + "=" + cookie.getValue();
				}
				response.header("Content-Length", body.length()).content(body).end();
			}
		}).start();
		URLConnection urlConnection = httpGet(webServer, "/");
		urlConnection.addRequestProperty("Cookie", ("a=b").toString());
		urlConnection.addRequestProperty("Cookie", ("c=\"d").toString() + "; " + ("e=f").toString());
		// assertEquals("Your cookies: a=b c=\"d e=f", contents(urlConnection));
	}

	@Test
	public void behavesWellWhenThereAreNoInboundCookies() throws IOException {
		webServer.add(new HttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				String body = "Cookie count:" + request.cookies().size();
				response.header("Content-Length", body.length()).content(body).end();
			}
		}).start();
		URLConnection urlConnection = httpGet(webServer, "/");
		assertEquals("Cookie count:0", contents(urlConnection));
	}

	// You wouldn't have thought it was that convoluted, but it is.
	private List<Cookie> cookies(URLConnection urlConnection) throws IOException {
		List<Cookie> cookies = new ArrayList<Cookie>();
		Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
		for (Map.Entry<String, List<String>> header : headerFields.entrySet()) {
			if ("Set-Cookie".equals(header.getKey())) {
				List<String> value = header.getValue();
				for (String cookie : value) {
					cookies.addAll(ListUtil.toList(CookieReader.read(cookie)));
				}
			}
		}
		return sort(cookies);
	}

	private List<Cookie> sort(List<Cookie> cookies) {
		Collections.sort(cookies, new Comparator<Cookie>() {
			public int compare(Cookie a, Cookie b) {
				return a.getName().compareTo(b.getName());
			}
		});
		return cookies;
	}
	
	@Test
	public void testCookie() throws Exception {
		Cookie cookie = new Cookie("a", "b") ;
		assertEquals("a=b", CookieWriter.write(cookie)) ;
	}
}
