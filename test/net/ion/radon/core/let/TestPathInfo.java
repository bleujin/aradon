package net.ion.radon.core.let;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.radon.core.path.URLPattern;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;

import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Reference;

public class TestPathInfo extends TestCase{
	
	public void testPattern() throws Exception {
		
		PathInfo pinfo = PathInfo.create("my", "/path1/path2", "", "", HelloWorldLet.class) ;
		
		Reference r = new Reference("/path1/path2") ;
		assertTrue(pinfo.isMatchURL(r)) ;
	}
	
	public void testSubPathPattern() throws Exception {
		
		PathInfo pinfo = PathInfo.create("my", "/path1/{path2}", "", "", HelloWorldLet.class) ;
		
		Reference r = new Reference("/path1/subpath") ;
		assertTrue(pinfo.isMatchURL(r)) ;
	}
	
	public void testUrlPattern() throws Exception {
		Debug.debug(URLPattern.isMatch("/path1/subpath", "/path1/{path2}")) ;
	}
	
	public void testPatterns() throws Exception {
		PathInfo pinfo = PathInfo.create("my", "/path1,  /path1/{path2}", "", "", HelloWorldLet.class) ;
		
		Reference r = new Reference("/path1") ;
		assertTrue(pinfo.isMatchURL(r)) ;
		
		r = new Reference("/path1/subpath") ;
		assertTrue(pinfo.isMatchURL(r)) ;
	}

	public void testBlankPatterns() throws Exception {
		PathInfo pinfo = PathInfo.create("my", "/", "", "", HelloWorldLet.class) ;
		Reference r = new Reference("/favicon.ico") ;
		assertEquals(false, pinfo.isMatchURL(r)) ;

		pinfo = PathInfo.create("my", "/favicon.ico", "", "", HelloWorldLet.class) ;
		r = new Reference("/favicon.ico") ;
		assertEquals(true, pinfo.isMatchURL(r)) ;
	}
	
	
	public void testReference() throws Exception {
		Request req = new Request(Method.GET, "http://localhost:9002/section/path") ;
		
		assertEquals("/section/path", req.getResourceRef().getPath()) ;
		
		req = new Request(Method.GET, "/section/path") ;
		assertEquals("/section/path", req.getResourceRef().getPath()) ;
		
	}

	public void testSegment() throws Exception {
		Reference r = new Reference("/abc") ;
		Debug.debug(r.getSegments()) ;
		Debug.debug(new Reference("/abc/").getSegments()) ;
	}
	

	public void testStartWith() throws Exception {
		PathInfo pinfo = PathInfo.create("my", "/{greeting}", "STARTWITH", "", HelloWorldLet.class) ;
		Reference r = new Reference("/hi") ;
		assertEquals(true, pinfo.isMatchURL(r)) ;
	}
	
	public void testStartWith2() throws Exception {
		PathInfo pinfo = PathInfo.create("my", "/hi/{greeting}", "STARTWITH", "", HelloWorldLet.class) ;
		Reference r = new Reference("/hi") ;
		assertEquals(false, pinfo.isMatchURL(r)) ;

		r = new Reference("/hi/") ;
		assertEquals(true, pinfo.isMatchURL(r)) ;
	}
	

}
