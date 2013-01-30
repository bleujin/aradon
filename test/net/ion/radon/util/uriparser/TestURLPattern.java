package net.ion.radon.util.uriparser;

import static org.junit.Assert.assertEquals;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestURLPattern {

	@Test
	public void simpleExpr() throws Exception {
		final URIPattern uriPattern = new URIPattern("/users/{user}");
		
		Assert.assertEquals(true, uriPattern.match("/users/bleujin")) ;
		assertEquals(true, new URIResolver("/users/bleujin").find(createSamplePatterns()) != null) ;
		URIResolveResult result = new URIResolver("/users/bleujin").resolve(uriPattern);
		
		assertEquals(1, result.names().size()) ;
		assertEquals("bleujin", result.get("user")) ;

		result = new URIResolver("/users/bleujin%20hi").resolve(uriPattern);
		assertEquals(1, result.names().size()) ;
		assertEquals("bleujin hi", result.get("user")) ;

	}
	
	@Test
	public void simpleUTFExpr() throws Exception {
		final URIPattern uriPattern = new URIPattern("/users/{user}");
		final String url = "/users/" + URLEncoder.encode("한글", "UTF-8");
		
		Assert.assertEquals(true, uriPattern.match(url)) ;
		URIResolveResult result = new URIResolver(url).resolve(uriPattern);
		
		assertEquals(1, result.names().size()) ;
		assertEquals("한글", result.get("user")) ;
	}
	
	
	@Test
	public void encode() throws Exception {
		final String url = "/users/한글";
		assertEquals(URICoder.encode(url, '/'), URICoder.minimalEncode(url)) ;
		assertEquals(URICoder.encode(url, '/'),"/users/" + URLEncoder.encode("한글", "UTF-8")) ; 
		
	}
	
	@Test
	public void asterkExpr() throws Exception {
		final URIPattern uriPattern = new URIPattern("/users/{user}*");
		Assert.assertEquals(true, uriPattern.match("/users/bleujin")) ;
		Assert.assertEquals(true, uriPattern.match("/users/bleujin/a")) ;
		Assert.assertEquals(true, uriPattern.match("/users/bleujin/a/b")) ;

		URIResolveResult result = new URIResolver("/users/bleujin/a/b/c").resolve(uriPattern);
		assertEquals(2, result.names().size()) ;
		assertEquals("bleujin", result.get("user")) ;
		assertEquals("/a/b/c", result.get("*")) ;
	}
	
	@Test
	public void plusExpr() throws Exception {
		final URIPattern uriPattern = new URIPattern("/{+path}/bleujin");
		Assert.assertEquals(true, uriPattern.match("/a/b/bleujin")) ;
		Assert.assertEquals(true, uriPattern.match("/c/d/e/bleujin")) ;
		
		URIResolveResult result = new URIResolver("/a/b/c/bleujin").resolve(uriPattern);
		assertEquals(1, result.names().size()) ;
		assertEquals("a/b/c", result.get("path")) ;
	}
	
	
	@Test
	public void listExpr() throws Exception {
		final URIPattern uriPattern = new URIPattern("/users/{+list}");
		Assert.assertEquals(true, uriPattern.match("/users/bleujin")) ;
		Assert.assertEquals(true, uriPattern.match("/users/bleujin/hero")) ;
		Assert.assertEquals(true, uriPattern.match("/users/bleujin,hero,jin")) ;

		URIResolveResult result = new URIResolver("/users/bleujin,hero,jin").resolve(uriPattern);
		assertEquals(1, result.names().size()) ;
		assertEquals("bleujin,hero,jin", result.get("list")) ;
	}
	
	@Test
	public void formExpr() throws Exception {
		URIPattern uriPattern = new URIPattern("/users{?who}");
		Assert.assertEquals(true, uriPattern.match("/users?who=bleujin")) ;
		uriPattern = new URIPattern("/users{?who,name}");
		Assert.assertEquals(true, uriPattern.match("/users?who=bleujin&name=bleuin")) ;
		uriPattern = new URIPattern("/users{?list}");
		Assert.assertEquals(true, uriPattern.match("/users?list=a,b,c")) ;
		
		
		uriPattern = new URIPattern("/users{?who,name}");
		URIResolveResult result = new URIResolver("/users?who=jin&name=hero").resolve(uriPattern);
		
		assertEquals(0, result.names().size()) ;
	}
	
	
	private List<URIPattern> createSamplePatterns(){
		List<URIPattern> list = new ArrayList<URIPattern>() ;
		list.add(new URIPattern("/users/{user}")) ;
		list.add(new URIPattern("/groups/{group}*")) ;
		list.add(new URIPattern("/users/{user}/name")) ;
		list.add(new URIPattern("/users/{user}/{age}")) ;
		
		return list ;
	}
	
}
