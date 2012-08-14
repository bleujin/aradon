package net.ion.radon.core.security;

import junit.framework.TestCase;
import net.ion.framework.util.InfinityThread;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.impl.let.HelloWorldLet;

import org.restlet.representation.Representation;

public class TestSimpleVerify extends TestCase {

	public void testFilter() throws Exception {
		Aradon aradon = Aradon.create() ;

		aradon.attach(SectionConfiguration.createBlank("test"));
		aradon.getChildService("test").attach(PathConfiguration.create("hello", "/hello", HelloWorldLet.class));
		aradon.getChildService("test").getConfig().addPreFilter(new ChallengeAuthenticator("test", new SimpleVerifier("haha", "haha")));

		AradonClient client = AradonClientFactory.create(aradon);
		Representation repr = client.createRequest("/test/hello", "haha", "haha").get();

		aradon.startServer(9000);
		new InfinityThread().startNJoin();
	}
}
