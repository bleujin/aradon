package net.ion.radon.core.config;

import static junit.framework.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.EnumClass.Scope;
import net.ion.radon.core.config.ConnectorConfig.EngineType;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.impl.filter.SayHello;

import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;

public class TestConfigLoad {

	private ConfigurationBuilder builder;

	@Before
	public void setUp() throws Exception {
		this.builder = ConfigurationBuilder.load(XMLConfig.create("./resource/config/aradon-config.xml"));
	}

	@Test
	public void load() throws Exception {
		assertEquals(true, builder != null);
	}

	@Test
	public void servers() throws Exception {
		assertEquals("mercury", builder.build().server().id());
		assertEquals("./resource/config/log4j.properties", builder.build().server().logConfigPath());
		assertEquals(4567, builder.build().server().shellPort());
		assertEquals(false, builder.build().server().isShellAutoStart());
	}

	@Test
	public void connector() throws Exception {
		assertEquals(9000, builder.build().server().connector().port());
		assertEquals(Protocol.HTTP, builder.build().server().connector().protocol());
		assertEquals(EngineType.Jetty, builder.build().server().connector().engine());
		assertEquals("5", builder.build().server().connector().properties().get("minThreads"));
		assertEquals("1000", builder.build().server().connector().properties().get("soLingerTime"));
	}

	@Test
	public void aradonFilter() throws Exception {
		assertEquals(1, builder.build().aradon().prefilters().size());
		assertEquals(1, builder.build().aradon().afterfilters().size());

		SayHello prefilter = (SayHello) builder.build().aradon().prefilters().get(0);
	}

	@Test
	public void context() throws Exception {
		assertEquals("bleujin@i-on.net", builder.build().aradon().attributes().get("let.contact.email").get(null));
		assertEquals("/help/doc", builder.build().aradon().attributes().get("let.contact.help.doc").get(null));

	}

	@Test
	public void contextScope() throws Exception {
		// application scope
		StringBuffer sb = (StringBuffer) builder.build().aradon().attributes().get("test.application.sb").get(null);
		assertEquals("hello", sb.toString());
		sb.append(" bleujin");
		assertEquals("hello bleujin", builder.build().aradon().attributes().get("test.application.sb").get(null).toString());
		
		// request scope
		sb = (StringBuffer) builder.build().aradon().attributes().get("test.request.sb").get(null);
		assertEquals("hello", sb.toString());
		sb.append(" bleujin");
		assertEquals("hello", builder.build().aradon().attributes().get("test.request.sb").get(null).toString());
	}

	
	@Test
	public void section() throws Exception {
		SectionConfiguration sconfig = builder.build().aradon().sections().restSection("") ;
		assertEquals(true, sconfig != null) ;
		
	}
	
	@Test
	public void path() throws Exception {
		SectionConfiguration sconfig = builder.build().aradon().sections().restSection("") ;
		PathConfiguration pconfig = sconfig.path("default");
		
		assertEquals("default", pconfig.name()) ;
		
		assertEquals("net.ion.radon.impl.let.HelloWorldLet", pconfig.handlerClz().getCanonicalName()) ;
		assertEquals(Scope.Request, pconfig.scope()) ;
	}
	
	@Test
	public void testAradon() throws Exception {
		Aradon na = Aradon.create(builder.build()) ;
		
		Request req = new Request(Method.GET, "riap://component/") ;
		Response res = na.handle(req) ;
		assertEquals(200, res.getStatus().getCode()) ; 
		
	}
	
	@Test
	public void aradonContext() throws Exception {
		Aradon na = Aradon.create(builder.build()) ;
		assertEquals(4 + 1, na.getContext().getAttributes().size()) ;
		
		Debug.line(na.getContext().getAttributes()) ;
	}
	
	@Test
	public void requestAttribute() throws Exception {
		Configuration config = ConfigurationBuilder.newBuilder().aradon().sections().restSection("").path("hello").addUrlPattern("/hello/{name}").handler(AttributeLet.class).build() ;
		Aradon aradon = Aradon.create(config) ;
		
		Response response = aradon.handle(new Request(Method.GET, "riap://component/hello/bleujin")) ;
		Debug.line(response.getEntityAsText()) ;
	}
	
	
}


class AttributeLet extends AbstractServerResource {
	
	@Get
	public String greeting(){
		return "hello " + getInnerRequest().getAttribute("name") ;
	}
}
