package net.ion.nradon.handler;

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
import java.util.concurrent.ExecutionException;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;
import net.ion.nradon.helpers.HttpCookie;

import org.junit.After;
import org.junit.Test;
import org.restlet.data.Cookie;
import org.restlet.engine.header.CookieWriter;

public class TestCookie {
	private Radon webServer ;
	private RadonConfigurationBuilder configBuilder = RadonConfiguration.newBuilder(59504) ;
	@After
	public void die() throws IOException, InterruptedException, ExecutionException {
		webServer.stop().get();
	}

	@Test
	public void setsOneOutboundCookie() throws IOException, InterruptedException, ExecutionException {
		this.webServer = configBuilder.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				response.cookie(new HttpCookie("a", "b")).end();
			}
		}).startRadon();
		URLConnection urlConnection = httpGet(webServer, "/");
		List<HttpCookie> cookies = cookies(urlConnection);
		assertEquals(1, cookies.size());
		assertEquals("a", cookies.get(0).getName());
		assertEquals("b", cookies.get(0).getValue());
	}

	@Test
	public void setsTwoOutboundCookies() throws IOException, InterruptedException, ExecutionException {
		this.webServer = configBuilder.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				response.cookie(new HttpCookie("a", "b")).cookie(new HttpCookie("c", "d")).end();
			}
		}).startRadon();
		URLConnection urlConnection = httpGet(webServer, "/");
		List<HttpCookie> cookies = cookies(urlConnection);
		assertEquals(2, cookies.size());
		assertEquals("a", cookies.get(0).getName());
		assertEquals("b", cookies.get(0).getValue());
		assertEquals("c", cookies.get(1).getName());
		assertEquals("d", cookies.get(1).getValue());
	}

	@Test
	public void parsesOneInboundCookie() throws IOException, InterruptedException, ExecutionException {
		this.webServer = configBuilder.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				String body = "Your cookie value: " + request.cookieValue("someName");
				response.header("Content-Length", body.length()).content(body).end();
			}
		}).startRadon();
		URLConnection urlConnection = httpGet(webServer, "/");
		urlConnection.addRequestProperty("Cookie", "someName=someValue");
		assertEquals("Your cookie value: someValue", contents(urlConnection));
	}

	@Test
	public void parsesThreeInboundCookiesInTwoHeaders() throws IOException, InterruptedException, ExecutionException {
		this.webServer = configBuilder.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				String body = "Your cookies:";
				List<HttpCookie> cookies = sort(request.cookies());
				for (HttpCookie cookie : cookies) {
					body += " " + cookie.getName() + "=" + cookie.getValue();
				}
				response.header("Content-Length", body.length()).content(body).end();
			}
		}).startRadon();
		URLConnection urlConnection = httpGet(webServer, "/");
		urlConnection.addRequestProperty("Cookie", ("a=b").toString());
		urlConnection.addRequestProperty("Cookie", ("c=\"d").toString() + "; " + ("e=f").toString());
		// assertEquals("Your cookies: a=b c=\"d e=f", contents(urlConnection));
	}

	@Test
	public void behavesWellWhenThereAreNoInboundCookies() throws IOException, InterruptedException, ExecutionException {
		this.webServer = configBuilder.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				String body = "Cookie count:" + request.cookies().size();
				response.header("Content-Length", body.length()).content(body).end();
			}
		}).startRadon();
		URLConnection urlConnection = httpGet(webServer, "/");
		assertEquals("Cookie count:0", contents(urlConnection));
	}

	// You wouldn't have thought it was that convoluted, but it is.
	private List<HttpCookie> cookies(URLConnection urlConnection) throws IOException {
		List<HttpCookie> cookies = new ArrayList<HttpCookie>();
		Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
		for (Map.Entry<String, List<String>> header : headerFields.entrySet()) {
			if ("Set-Cookie".equals(header.getKey())) {
				List<String> value = header.getValue();
				for (String hvalue : value) {
					cookies.addAll(HttpCookie.parse(hvalue));
				}
			}
		}
		return sort(cookies);
	}

	private List<HttpCookie> sort(List<HttpCookie> cookies) {
		Collections.sort(cookies, new Comparator<HttpCookie>() {
			public int compare(HttpCookie a, HttpCookie b) {
				return a.getName().compareTo(b.getName());
			}
		});
		return cookies;
	}
	
	@Test
	public void testCookie() throws Exception {
		this.webServer = configBuilder.createRadon() ;
		Cookie cookie = new Cookie("a", "b") ;
		assertEquals("a=b", CookieWriter.write(cookie)) ;
	}
}
