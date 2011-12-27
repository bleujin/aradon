package net.ion.radon.util;

import org.junit.Test;

import net.ion.radon.Options;
import junit.framework.TestCase;

public class TestOptions {

	@Test
	public void option() throws Exception {
		Options opt = new Options(new String[]{"-config:123"});
		
		assertEquals("123", opt.getString("config", "")) ;
		assertEquals("456", opt.getString("not", "456")) ;
	}
	
	private void assertEquals(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void prefix() throws Exception {
		Options opt = new Options(new String[]{"*config:123"}, '*');
		
		assertEquals("123", opt.getString("config", "")) ;
		assertEquals("456", opt.getString("not", "456")) ;
	}

	@Test
	public void noPrefix() throws Exception {
		Options opt = new Options(new String[]{"config:123"}, ' ');
		
		assertEquals("123", opt.getString("config", "")) ;
		assertEquals("456", opt.getString("not", "456")) ;
	}

}
