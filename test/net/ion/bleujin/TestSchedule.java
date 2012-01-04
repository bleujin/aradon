package net.ion.bleujin;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import net.ion.framework.message.MessageChannel;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

public class TestSchedule extends TestCase{

	
	public void testHello() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", HelloWorldLet.class) ;
		at.getAradon().startServer(9000) ;
		

		MessageChannel mc = new MessageChannel() ;
		ScheduledExecutorService ses = mc.loadScheduleService() ;
		
		Runnable command = new Runnable() {
			
			AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000") ;
			public void run() {
				Debug.line(ac.createRequest("/test").get()) ;
			}
		};
		ses.scheduleAtFixedRate(command, 5, 5, TimeUnit.SECONDS) ;
		
		

		new InfinityThread().startNJoin() ;
	}
}
