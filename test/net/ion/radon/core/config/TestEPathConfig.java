package net.ion.radon.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import net.ion.framework.util.ListUtil;
import net.ion.nradon.Radon;
import net.ion.nradon.client.eventsource.EventSource;
import net.ion.nradon.client.eventsource.EventSourceHandler;
import net.ion.radon.core.Aradon;

import org.junit.Before;
import org.junit.Test;

public class TestEPathConfig {

	private ConfigurationBuilder cbuilder;

	@Before
	public void setUp() throws Exception {
		this.cbuilder = ConfigurationBuilder.load(XMLConfig.create(new File("resource/config/section-config.xml")));
	}

	@Test
	public void running() throws Exception {
		final Aradon aradon = Aradon.create(cbuilder.build());
		Radon radon = aradon.toRadon(9000).start().get();

		List<String> messages = ListUtil.toList("hello", "jin", "bye") ;
		
		CountDownLatch messageCountdown = new CountDownLatch(messages.size());
		startClient(messages, messageCountdown, new CountDownLatch(0), 5000);
		assertTrue("Didn't get all messages", messageCountdown.await(1000, TimeUnit.MILLISECONDS));

		
		// confirm context attribute
		String pathAttribute = aradon.getChildService("async").epath("esource").getServiceContext().getAttributeObject("path.attribute", String.class) ;
		Assert.assertEquals("name : esource", pathAttribute) ;
		
		
		
		radon.stop() ;
	}

	private void startClient(final List<String> expectedMessages, final CountDownLatch messageCountdown, final CountDownLatch errorCountdown, long reconnectionTimeMillis) throws InterruptedException {
		EventSource eventSource = new EventSource(Executors.newSingleThreadExecutor(), reconnectionTimeMillis, URI.create("http://localhost:9000/async/event/123?echoThis=yo"), new EventSourceHandler() {
			int n = 0;

			public void onConnect() {
			}

			public void onMessage(String event, net.ion.nradon.client.eventsource.MessageEvent message) {
				assertEquals(expectedMessages.get(n++) + " yo", message.data);
				assertEquals("http://localhost:9000/async/event/123?echoThis=yo", message.origin);
				messageCountdown.countDown();
			}

			public void onError(Throwable t) {
				errorCountdown.countDown();
			}
		});
		eventSource.connect().await();
	}
}
