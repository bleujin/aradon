package net.ion.bleujin.study;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.restlet.data.MediaType;

public class TestRestlet extends TestCase{
	
	public void testMediaType() throws Exception {
		MediaType mt = MediaType.valueOf("text/html;charset=UTF-8") ;
		Debug.line(mt.getMainType()) ;
	}
}
