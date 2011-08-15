package net.ion.radon.core.path;

import junit.framework.TestCase;

public class TestUrlPattern extends TestCase{

	
	public void testIsMatch() throws Exception {
		URLPattern<Object> p = new URLPattern<Object>() ;
		
		final String urlPattern = "/sample/basic/employee/{empno}.{format}";
		final String url = "/sample/basic/employee/method.merge";
		assertTrue(p.isMatch(url, urlPattern));
		//String s = urlPattern.replaceAll(URLPattern.FIND_PATTERN, URLPattern.TRANS_PATTERN);
	}
}
