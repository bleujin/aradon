package net.ion.radon.script;

import static org.junit.Assert.assertEquals;

import java.io.File;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.core.script.ScriptFactory;
import net.ion.radon.impl.let.HelloWorldLet;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;

public class TestRhinoFilter extends TestBaseAradon{

	@Test
	public void scriptRun() throws Exception {
		Aradon aradon = testAradon();
		SectionService section = aradon.attach(SectionConfiguration.createBlank("test"));
		section.attach(PathConfiguration.create("test", "/test", HelloWorldLet.class));

		section.getConfig().addAfterFilter(ScriptFactory.createRhinoFilter(new File("./script-test/rhino/ChartFilter.js")));
		Request request = new Request(Method.GET, "riap://component/test/test?aradon.result.format=xml");
		Response response = aradon.handle(request);
		
		
		assertEquals(200, response.getStatus().getCode()) ;
	}
	
	@Test
	public void checkParam() throws Exception {
		Aradon aradon = testAradon();
		SectionService section = aradon.attach(SectionConfiguration.createBlank("test"));
		section.attach(PathConfiguration.create("test", "/test", HelloWorldLet.class));

		section.getConfig().addAfterFilter(ScriptFactory.createRhinoFilter(new File("./script-test/rhino/CheckParam.js")));
		assertEquals(501, sendRequest(aradon, "not_allowed").getStatus().getCode());
		assertEquals(200, sendRequest(aradon, "allowed").getStatus().getCode());
	}
	
	private Response sendRequest(Aradon aradon, String value) {
		Request request = new Request(Method.GET, "riap://component/test/test");
		Form form = new Form() ;
		form.add("key", value) ;
		
		request.setEntity(form.getWebRepresentation()) ;
		return aradon.handle(request);
	}
}
