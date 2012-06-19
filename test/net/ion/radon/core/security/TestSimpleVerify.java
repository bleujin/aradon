package net.ion.radon.core.security;

import junit.framework.TestCase;
import net.ion.framework.util.InfinityThread;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;

import org.restlet.representation.Representation;

public class TestSimpleVerify extends TestCase {

	public void testFilter() throws Exception {
		Aradon aradon = new Aradon();
		aradon.init(XMLConfig.BLANK);

		aradon.attach("test", XMLConfig.BLANK);
		aradon.getChildService("test").attach(PathInfo.create("hello", "/hello", HelloWorldLet.class));
		aradon.getChildService("test").addPreFilter(new ChallengeAuthenticator("test", new SimpleVerifier("haha", "haha")));

		AradonClient client = AradonClientFactory.create(aradon);
		Representation repr = client.createRequest("/test/hello", "haha", "haha").get();

		aradon.startServer(9000);
		new InfinityThread().startNJoin();
	}
}
