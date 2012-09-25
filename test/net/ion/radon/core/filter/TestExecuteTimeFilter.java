package net.ion.radon.core.filter;

import net.ion.bleujin.HelloWorldLet2;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.core.let.PathService;
import net.ion.radon.impl.filter.ExecuteTimeFilter;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestExecuteTimeFilter extends TestBaseAradon{
	

	@Test
	public void testPrefilter() throws Exception {
		Aradon aradon = testAradon();
		
		SectionService section = aradon.attach(SectionConfiguration.createBlank("test"));
		section.attach(PathConfiguration.create("test", "/test", HelloWorldLet2.class));
		
		PathService pservice =  section.path("test");
		ExecuteTimeFilter filter = new ExecuteTimeFilter();
		pservice.getConfig().addPreFilter(filter);

		
		Request request = new Request(Method.GET, "riap://component/test/test");
		Response response = aradon.handle(request);
		Debug.line(response.getEntityAsText());
		
		
	}

}
