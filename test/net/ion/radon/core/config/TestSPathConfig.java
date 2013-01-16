package net.ion.radon.core.config;

import java.io.File;

import junit.framework.Assert;
import net.ion.nradon.Radon;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;

import org.junit.Before;
import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestSPathConfig {

	private ConfigurationBuilder cbuilder;

	@Before
	public void setUp() throws Exception {
		this.cbuilder = ConfigurationBuilder.load(XMLConfig.create(new File("resource/config/section-config.xml")));
	}


	@Test
	public void running() throws Exception {
		final Aradon aradon = Aradon.create(cbuilder.build());
		Radon radon = aradon.toRadon(9000).start().get() ;
		
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000") ;
		Response res = ac.createRequest("/resource/config/aradon-config.xml").handle(Method.GET);
		
		Assert.assertEquals(200, res.getStatus().getCode()) ;
		
		// confirm context attribute
		String pathAttribute = aradon.getChildService("resource").spath("file").getServiceContext().getAttributeObject("path.attribute", String.class) ;
		Assert.assertEquals("name : file", pathAttribute) ;
		
		
		// ignore filter
		Assert.assertEquals(0, aradon.getChildService("resource").spath("file").getConfig().prefilters().size()) ;
		Assert.assertEquals(0, aradon.getChildService("resource").spath("file").getConfig().afterfilters().size()) ;
		Assert.assertEquals(1, aradon.getChildService("resource").spath("file").getConfig().filters().size()) ;
		
		radon.stop() ;
	}

	
}
