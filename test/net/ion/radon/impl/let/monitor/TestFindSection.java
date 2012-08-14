package net.ion.radon.impl.let.monitor;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.impl.let.HelloWorldLet;

import org.junit.Test;

public class TestFindSection {

	@Test
	public void findSection() throws Exception {
		Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().build())  ;
		aradon.attach(SectionConfiguration.createBlank("s1"))
			.attach(PathConfiguration.create("test", "/hello", HelloWorldLet.class))
			.attach(PathConfiguration.create("monitor", "/monitor/{section}/{path}", MonitorLet.class));
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Debug.line(ac.createRequest("/s1/monitor/s1/test").put()) ;
	}
}
