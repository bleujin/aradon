package net.ion.radon.core.config;

import java.io.File;

import junit.framework.Assert;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;

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
		RadonConfigurationBuilder rconfig = RadonConfiguration.newBuilder(9000).add(cbuilder.build());
		Radon radon = rconfig.startRadon();
		
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000") ;
		Response res = ac.createRequest("/resource/config/aradon-config.xml").handle(Method.GET);
		
		Assert.assertEquals(200, res.getStatus().getCode()) ;
		
		// confirm context attribute
		String pathAttribute = radon.getConfig().aradon().getChildService("resource").spath("file").getServiceContext().getAttributeObject("path.attribute", String.class) ;
		Assert.assertEquals("name : file", pathAttribute) ;
		
		
		// ignore filter
		Assert.assertEquals(0, radon.getConfig().aradon().getChildService("resource").spath("file").getConfig().prefilters().size()) ;
		Assert.assertEquals(0, radon.getConfig().aradon().getChildService("resource").spath("file").getConfig().afterfilters().size()) ;
		Assert.assertEquals(1, radon.getConfig().aradon().getChildService("resource").spath("file").getConfig().filters().size()) ;
		
		radon.stop() ;
	}

	
}
