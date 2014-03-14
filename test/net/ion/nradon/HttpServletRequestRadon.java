package net.ion.nradon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.ion.framework.util.NumberUtil;
import net.ion.framework.util.StringUtil;

public class HttpServletRequestRadon implements HttpServletRequest {

	private HttpRequest inner;

	public HttpServletRequestRadon(HttpRequest request) {
		this.inner = request ;
	}

	public Object getAttribute(String key) {
		return inner.data(key) ;
	}

	public Enumeration getAttributeNames() {
		return new Vector(inner.data().keySet()).elements() ;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalName() {
		return null;
	}

	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getParameter(String key) {
		return StringUtil.defaultIfEmpty(inner.queryParam(key), inner.postParam(key));
	}

	public Map getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getParameterValues(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRealPath(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public RequestDispatcher getRequestDispatcher(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeAttribute(String s) {
		// TODO Auto-generated method stub

	}

	public void setAttribute(String s, Object obj) {
		// TODO Auto-generated method stub

	}

	public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	}

	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getDateHeader(String s) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getHeader(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getHeaders(String name) {
		return new Vector(inner.headers(name)).elements();
	}

	public int getIntHeader(String name) {
		return NumberUtil.toInt(inner.header(name), -1) ;
	}

	public String getMethod() {
		return inner.method() ;
	}

	public String getPathInfo() {
		return inner.uri() ;
	}

	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpSession getSession(boolean flag) {
		// TODO Auto-generated method stub
		return null;
	}

	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isUserInRole(String s) {
		// TODO Auto-generated method stub
		return false;
	}

}
