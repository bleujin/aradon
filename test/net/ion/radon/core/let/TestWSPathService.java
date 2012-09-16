package net.ion.radon.core.let;

import org.junit.Assert;
import org.junit.Test;

public class TestWSPathService {


	@Test
	public void testConvertURlPattern() throws Exception {
		String pattern = "/{roomid}/{userid}" ;
		String expect = "/.*/.*" ;
		Assert.assertEquals(expect, pattern.replaceAll("\\{[^\\/]*\\}", ".*")) ;
	}

	
	@Test
	public void testConvertURlPattern2() throws Exception {
		String pattern = "/abc/{roomid}/{userid}" ;
		String expect = "/abc/.*/.*" ;
		Assert.assertEquals(expect, pattern.replaceAll("\\{[^\\/]*\\}", ".*")) ;
	}
	

}
