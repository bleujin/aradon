package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.util.AradonTester;

import org.junit.Test;

public class TestServerResource {

	@Test
	public void contextLevelAtResource() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ConfirmContextLet.class);

		AradonClient ac = AradonClientFactory.create(at.getAradon());
		assertEquals("Request", ac.createRequest("/test?level=2").get().getText());

	}

	@Test
	public void contextLevelAtLet() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ConfirmContextLet2.class);

		AradonClient ac = AradonClientFactory.create(at.getAradon());
		assertEquals("Request", ac.createRequest("/test").get().getText());

	}

}
