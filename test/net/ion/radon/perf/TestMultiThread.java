package net.ion.radon.perf;

import static org.junit.Assert.assertEquals;

import java.util.List;

import junit.framework.Assert;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.RandomUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.TestAradon;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestMultiThread extends TestBaseAradon {

	static int LOOP = 50;

	@Test
	public void testStartAradon() throws Exception {
		Aradon aradon = testAradon() ;

		Request request = new Request(Method.GET, "riap://component/");
		Response response = aradon.handle(request);

		assertEquals(200, response.getStatus().getCode());
	}

	@Test
	public void testSingleThreadGet() throws Exception {
		Aradon aradon = testAradon() ;
		
		Client c = new Client("thread1", "riap://component/hello", aradon);
		c.start();

		c.join();
	}

	@Test
	public void testMuitiThreadGet() throws Exception {
		Aradon aradon = testAradon() ;

		Client[] clients = new Client[20];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = new Client("thread1", "riap://component/hello", aradon);
		}

		for (Client client : clients) {
			client.start();
		}

		for (Client client : clients) {
			client.join();
		}
		// 20thread * 10000 = 44 sec
	}

}

class Client extends Thread {

	private static List<Method> methods = ListUtil.toList(Method.GET, Method.POST, Method.PUT, Method.DELETE);
	private Aradon aradon;
	private String name;
	private String path;

	Client(String name, String path, Aradon aradon) {
		this.name = name;
		this.path = path;
		this.aradon = aradon;
	}

	public void run() {
		for (int i = 0; i < TestMultiThread.LOOP; i++) {
			final String paramValue = name + i;
			final Method method = methods.get(RandomUtil.nextInt(4));

			Request request = new Request(method, path + "?name=" + paramValue);
			Response response = aradon.handle(request);

			if (i % 100 == 0)
				System.out.print('.');
			Assert.assertEquals(200, response.getStatus().getCode());

			InnerRequest req = ((InnerResponse) Response.getCurrent()).getInnerRequest();
			Assert.assertEquals(paramValue, req.getFormParameter().get("name"));
		}
	}

}
