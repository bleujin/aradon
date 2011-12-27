package net.ion.radon.script;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Status;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.script.ScriptFactory;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;

public class TestRhinoFilter extends TestAradon{

	@Test
	public void scriptRun() throws Exception {
		initAradon();
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		section.attach(PathInfo.create("test", "/test", "", "", HelloWorldLet.class));

		section.addAfterFilter(ScriptFactory.createRhinoFilter(new File("./script-test/rhino/ChartFilter.js")));
		Request request = new Request(Method.GET, "riap://component/test/test?aradon.result.format=xml");
		Response response = aradon.handle(request);
		
		
		assertEquals(200, response.getStatus().getCode()) ;
	}
	
	@Test
	public void checkParam() throws Exception {
		initAradon();
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		section.attach(PathInfo.create("test", "/test", "", "", HelloWorldLet.class));

		section.addAfterFilter(ScriptFactory.createRhinoFilter(new File("./script-test/rhino/CheckParam.js")));
		assertEquals(501, sendRequest("not_allowed").getStatus().getCode());
		assertEquals(200, sendRequest("allowed").getStatus().getCode());
	}

	
	private Response sendRequest(String value) {
		Request request = new Request(Method.GET, "riap://component/test/test");
		Form form = new Form() ;
		form.add("key", value) ;
		
		request.setEntity(form.getWebRepresentation()) ;
		return aradon.handle(request);
	}
}
