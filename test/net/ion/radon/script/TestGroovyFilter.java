package net.ion.radon.script;

import static org.junit.Assert.assertEquals;
import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.io.FileOutputStream;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.core.script.GroovyRunFilter;
import net.ion.radon.core.script.ScriptFactory;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.representation.Representation;

public class TestGroovyFilter extends TestBaseAradon {

	@Test
	public void objectRun() throws Exception {
		Aradon aradon = testAradon();
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		section.attach(PathInfo.create("test", "/test", HelloWorldLet.class));

		section.addAfterFilter(GroovyRunFilter.create(new File("./script-test/groovy/ObjectFilter.groovy")));

		for (int i = 0; i < 5; i++) {
			long start = System.currentTimeMillis();
			Request request = new Request(Method.GET, "riap://component/test/test?aradon.result.format=xml");
			Response response = aradon.handle(request);
			Debug.debug(response.getEntityAsText());

			if (i > 0)
				assertEquals(true, System.currentTimeMillis() - start < 100);
		}
	}

	@Test
	public void scriptRun() throws Exception {
		Aradon aradon = testAradon();
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		section.attach(PathInfo.create("test", "/test", HelloWorldLet.class));

		section.addAfterFilter(ScriptFactory.createGroovyFilter(new File("./script-test/groovy/ScriptFilter.groovy")));

		for (int i = 0; i < 5; i++) {
			long start = System.currentTimeMillis();
			Request request = new Request(Method.GET, "riap://component/test/test?aradon.result.format=xml");
			Response response = aradon.handle(request);
			Debug.debug(response.getEntityAsText());
			assertEquals(200, response.getStatus().getCode());
			if (i > 0)
				assertEquals(true, System.currentTimeMillis() - start < 100);
		}
	}

	@Test
	public void runSpeed() throws Exception {
		Aradon aradon = testAradon();
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		section.attach(PathInfo.create("test", "/test", HelloWorldLet.class));

		Request request = new Request(Method.GET, "riap://component/test/test");
		Response response = aradon.handle(request);

		InnerRequest req = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		PathService iservice = req.getPathService(aradon);

		GroovyScriptEngine gse = new GroovyScriptEngine(new String[] { "./script-test/groovy" }, this.getClass().getClassLoader());
		for (int i = 0; i < 5; i++) {
			long start = System.currentTimeMillis();
			Script script = gse.createScript("ScriptFilter.groovy", new Binding());

			Binding binding = new Binding();
			binding.setProperty("service", iservice);
			binding.setProperty("request", req);
			binding.setProperty("response", response);
			script.setBinding(binding);

			IFilterResult result = (IFilterResult) script.run();
			Debug.line(System.currentTimeMillis() - start);
		}

		Debug.debug(response.getEntityAsText());
	}

	@Test
	public void chartFilter() throws Exception {
		Aradon aradon = testAradon();

		final SectionService section = aradon.getChildService("another");
		section.attach(PathInfo.create("chart", "/chart", HelloWorldLet.class));
		section.getChildService("chart").addAfterFilter(ScriptFactory.createGroovyFilter(new File("./script-test/groovy/ChartFilter.groovy")));
		// section.getChildService("chart").addAfterFilter(new TestChartFilter());

		Request request = new Request(Method.GET, "riap://component/another/chart");
		Response response = aradon.handle(request);

		FileOutputStream os = new FileOutputStream(new File("./resource/imsi/chart.png"));
		IOUtil.copyNClose(response.getEntity().getStream(), os);

	}

	@Test
	public void aradonClient() throws Exception {

		AradonClient client = AradonClientFactory.create("http://chart.apis.google.com");
		IAradonRequest ir = client.createRequest("/chart?cht=p3&chs=500x250&chd=s:JMBJD&chl=Open+Source|J2EE|Web+Service|Ajax|Other");
		Representation result = ir.get();

		FileOutputStream os = new FileOutputStream(new File("./resource/imsi/chart.png"));
		IOUtil.copyNClose(result.getStream(), os);

	}

}
