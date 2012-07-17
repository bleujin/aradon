package net.ion.radon.core;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.ListUtil;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.AbstractLet;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.engine.Engine;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class TestInitAradon {
	
	@Test
	public void initAradon() throws Exception {
		Engine.getInstance() ;

		
		Aradon aradon = new Aradon() ;
		aradon.init(XMLConfig.BLANK) ;
		
		SectionService section = aradon.attach("test", XMLConfig.BLANK) ;
		section.attach(PathInfo.create("hello", "/hello", GetLet.class)) ;
		
		Request request = new Request(Method.GET, "riap://component/test/hello") ;
		Response response = aradon.handle(request) ;
		
		
		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText()) ;
		aradon.stop() ;
	}
	
	@Test
	public void initTester() throws Exception {
		Aradon aradon = AradonTester.create().register("test", "/hello", GetLet.class).getAradon() ;

		Request request = new Request(Method.GET, "riap://component/test/hello") ;
		Response response = aradon.handle(request) ;
		
		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText()) ;
		aradon.stop() ;
	}
	
	@Test
	public void startNStop() throws Exception {
		for (int i : ListUtil.rangeNum(1, 3)) {
			Aradon aradon = AradonTester.create().register("", "/hello", HelloWorldLet.class).getAradon() ;
			aradon.startServer(9050) ;
			
			aradon.stop() ;
		}
	}
	
}

class GetLet extends AbstractLet {

	@Override
	public Representation myDelete() throws Exception {
		return null;
	}

	@Override
	public Representation myGet() throws Exception {
		return new StringRepresentation(GetLet.class.getCanonicalName());
	}

	@Override
	public Representation myPost(Representation entity) throws Exception {
		return new StringRepresentation("Hi");
	}

	@Override
	public Representation myPut(Representation entity) throws Exception {
		return null;
	}

}

