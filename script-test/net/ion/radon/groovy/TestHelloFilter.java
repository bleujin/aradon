package net.ion.radon.groovy;

import net.ion.bulletin.HelloFilter;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestHelloFilter extends TestAradon{
	
	public void testLoad() throws Exception {
		initAradon() ;
		SectionService section = aradon.attach("test", XMLConfig.BLANK) ;
		section.attach(PathInfo.create("test", "/hello", "", "", HelloWorldLet.class)) ;
		section.addPreFilter(new HelloFilter()) ;
		
		Request request = new Request(Method.GET, "riap://component/test/hello?name=greetinggreeting");
		 
		Response response = aradon.handle(request);
		
		
		
	}

}
