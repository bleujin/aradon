package net.ion.radon.core.annotation;

import junit.framework.TestCase;
import net.ion.framework.util.InfinityThread;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.util.AradonTester;

import org.restlet.resource.Get;

public class TestHello extends TestCase {

	public void testRunAradon() throws Exception {
		AradonTester.create().register("", "/outer", OuterLet.class).startServer(9000) ;
		new InfinityThread().startNJoin() ;
	}
}

class OuterLet implements IServiceLet {
	
	@Get
	public String viewText() {
		return "Hello" ;
	}
}
