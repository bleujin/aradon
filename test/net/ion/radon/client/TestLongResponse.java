package net.ion.radon.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ListUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

public class TestLongResponse {

	@Test
	public void testRun() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", LongResponseLet.class).getAradon();

		aradon.startServer(9000);
		new InfinityThread().startNJoin();
	}

	@Test
	public void testSchedule() throws Exception {

		ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

		ClientThread ct = new ClientThread("i");
		ses.scheduleWithFixedDelay(ct, 0, 1, TimeUnit.SECONDS);
		
		new InfinityThread().startNJoin() ;

	}
	
	@Test
	public void testCallNovision() throws Exception {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

		Runnable runn = new Runnable(){
			public void run() {
				AradonClient ac = AradonClientFactory.create("http://novision.i-on.net:8080") ;
				IAradonRequest req = ac.createRequest("/ics/sub/login.do") ;
				req.get() ;
				Debug.line('#') ;
			}
		} ;
		ses.scheduleWithFixedDelay(runn, 0, 1, TimeUnit.SECONDS);
		new InfinityThread().startNJoin() ;
	}
	
	

	@Test
	public void testRequest() throws Exception {

		List<ClientThread> cs = ListUtil.newList();
		for (int i : ListUtil.rangeNum(5)) {
			ClientThread ct = new ClientThread(i + "");
			ct.start();
			cs.add(ct);
		}

		for (ClientThread ct : cs) {
			ct.join();
		}

		for (ClientThread ct : cs) {
			Debug.line(ct.getName(), ct.getResText());
		}

	}

}

class ClientThread extends Thread {

	private String resText;

	public ClientThread(String name) {
		super(name);
	}

	public void run() {
		AradonClient ac = AradonClientFactory.create("http://localhost:9000");
		try {
			ac.createRequest("/test").get() ;
			Debug.line() ;
		} catch (ResourceException e) {
			e.printStackTrace();
		}
	}

	public String getResText() {
		return resText;
	}

}

class LongResponseLet extends AbstractServerResource{

	@Get
	public Representation print() throws FileNotFoundException{
		
		try {
			Thread.sleep(3000) ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FileInputStream fis = new FileInputStream(new File("aradon_lib/imsi/jmxtools-1.2.jar")) ;
		return new InputRepresentation(fis) ;
	}
}

