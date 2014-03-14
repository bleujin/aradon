package net.ion.nradon;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseRadon implements HttpServletResponse {

	private HttpResponse inner;
	private PrintWriter pwriter ;
	public HttpServletResponseRadon(HttpResponse response) {
		this.inner = response ;
		this.pwriter = new PrintWriter(new Writer(){

			@Override
			public void write(char[] cbuf, int off, int len) throws IOException {
				inner.content(CharBuffer.wrap(cbuf, off, len).toString()) ;
			}

			@Override
			public void flush() throws IOException {
				
			}

			@Override
			public void close() throws IOException {
				flush() ;
				inner.end() ;
			}
			
		}) ;
	}

	public void flushBuffer() throws IOException {
		pwriter.flush(); 
	}

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	public PrintWriter getWriter() throws IOException {
		return pwriter ;
	}

	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		pwriter.close(); 
	}

	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	public void setBufferSize(int i) {
		// TODO Auto-generated method stub

	}

	public void setCharacterEncoding(String s) {
		// TODO Auto-generated method stub

	}

	public void setContentLength(int i) {
		// TODO Auto-generated method stub

	}

	public void setContentType(String s) {
		// TODO Auto-generated method stub

	}

	public void setLocale(Locale locale) {
		// TODO Auto-generated method stub

	}

	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub
		
	}

	public void addDateHeader(String s, long l) {
		// TODO Auto-generated method stub
		
	}

	public void addHeader(String s, String s1) {
		// TODO Auto-generated method stub
		
	}

	public void addIntHeader(String s, int i) {
		// TODO Auto-generated method stub
		
	}

	public boolean containsHeader(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	public String encodeRedirectURL(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeRedirectUrl(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeURL(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeUrl(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendError(int i) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void sendError(int i, String s) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void sendRedirect(String s) throws IOException {
		// 
	}

	public void setDateHeader(String s, long l) {
		// TODO Auto-generated method stub
		
	}

	public void setHeader(String name, String value) {
		inner.header(name, value) ;
	}

	public void setIntHeader(String name, int value) {
		inner.header(name, value) ;
	}

	public void setStatus(int status) {
		inner.status(status) ;
	}

	public void setStatus(int status, String message) {
		inner.status(status) ;
	}

}
