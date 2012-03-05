package net.ion.radon.core.filter;

import net.ion.bleujin.HelloWorldLet2;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestAradon;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.filter.ExecuteTimeFilter;
import net.ion.radon.impl.section.PathInfo;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestExecuteTimeFilter extends TestBaseAradon{
	

	@Test
	public void testPrefilter() throws Exception {
		Aradon aradon = testAradon();
		
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		section.attach(PathInfo.create("test", "/test", HelloWorldLet2.class));
		
		PathService pservice =  section.getChildService("test");
		ExecuteTimeFilter filter = new ExecuteTimeFilter();
		pservice.addPreFilter(filter);

		
		Request request = new Request(Method.GET, "riap://component/test/test");
		Response response = aradon.handle(request);
		Debug.line(response.getEntityAsText());
		
		
	}

}
