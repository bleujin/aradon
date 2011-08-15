package net.ion.bleujin.asyncrestlet;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.restlet.data.MediaType;


public class TestMediaType extends TestCase{

	public void testMediaType() throws Exception {
		Debug.line() ;
		Debug.line(MediaType.valueOf("1.gif")) ;
		
		
	}
}
