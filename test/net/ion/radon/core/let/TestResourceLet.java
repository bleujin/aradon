package net.ion.radon.core.let;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import junit.framework.TestCase;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.util.AradonTester;

public class TestResourceLet {

	@Test
	public void useClient() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", GetResourceLet.class);

		IAradonRequest req = AradonClientFactory.create(at.getAradon()).createRequest("/test");
		assertEquals(GetLet.class.getCanonicalName(), req.get().getText());
	}

	@Test
	public void http() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", GetResourceLet.class);
		at.getAradon().startServer(9080);

		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1:9080").createRequest("/test");
		assertEquals(GetLet.class.getCanonicalName(), req.get().getText());

		at.getAradon().stop();
	}

}
