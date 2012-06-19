package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Before;
import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestGeneral extends TestBaseAradon {
	
	private Aradon aradon ;
	@Before
	public void setUp() throws Exception {
		this.aradon = AradonTester.create()
			.register("another", "/hello", HelloWorldLet.class)
			.getAradon() ;
	}
	
	@Test
	public void testBeforeHandle() throws Exception {
		Response response = handle(aradon, "/another/hello?param=abcd", Method.GET) ;
		assertEquals(200, response.getStatus().getCode()) ;
	}
	
	@Test
	public void testSectionFilter() throws Exception {
		Response response = handle(aradon, "/another/hello", Method.GET) ;
		assertEquals(200, response.getStatus().getCode()) ;
	}

	@Test
	public void testNotFound() throws Exception {
		Response response = handle(aradon, "/another/no_path", Method.GET) ;
		assertEquals(404, response.getStatus().getCode()) ;
	}
	
	
	
}
