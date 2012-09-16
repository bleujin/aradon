package net.ion.radon.core.config;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URI;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.Radon;
import net.ion.nradon.client.websocket.WebSocketClient;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;

import org.junit.Before;
import org.junit.Test;

public class TestWPathConfig {

	
	private ConfigurationBuilder cbuilder ;
	@Before
	public void setUp() throws Exception {
		this.cbuilder = ConfigurationBuilder.load(XMLConfig.create(new File("resource/config/section-config.xml")));
	}
	
	@Test
	public void read() throws Exception {
		WSPathConfigurationBuilder wbuilder = cbuilder.aradon().sections().restSection("async").wspath("broadcast");
		
		assertEquals("broadcast", wbuilder.name()) ;
		assertEquals("sample", wbuilder.description()) ;
		
		
		WSPathConfiguration wconfig = wbuilder.create() ;
		assertEquals("broadcast", wconfig.name()) ;
		assertEquals("sample", wconfig.description()) ;
	}
	
	@Test
	public void build() throws Exception {

		RadonConfigurationBuilder rconfig = RadonConfiguration.newBuilder(9000).add(cbuilder.build());
		for (HttpHandler handler : rconfig.build().handlers()) {
			Debug.line(handler) ;
		}  
		// Assert.fail() ;
	}
	
	
	@Test
	public void running() throws Exception {
		RadonConfigurationBuilder rconfig = RadonConfiguration.newBuilder(9000).add(cbuilder.build());
		Radon raodn = rconfig.startRadon() ;
		
		WebSocketClient wclient = WebSocketClient.create() ;
		wclient.connect(new URI("ws://61.250.201.157:9000/async/broadcast/123/bleujin")) ;
		
		wclient.ping() ;
		
		for (int i : ListUtil.rangeNum(10)) {
			wclient.sendMessage("Message #" + i) ;
		}
	}
}


//<section name="async">
//<path name="hello">
//	<urls>/hello</urls>
//	<description> async</description>
//	<handler class="net.ion.radon.impl.let.HelloWorldLet" />
//</path>
//
//<wspath name="broadcast">
//	<urls>/{roomid}/{userid}</urls>
//	<description>sample</description>
//	<handler class="net.ion.nradon.let.SampleBroadCast" />
//</wspath>
//</section>