package net.ion.radon.parameter;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public class ParameterSample extends TestCase {

	public void testParameter() throws Exception {
		Aradon aradon = AradonTester.create().register("test", "/", ConfirmLet.class).getAradon();

		AradonClient ac = AradonClientFactory.create(aradon);
		IAradonRequest request = ac.createRequest("/test/");
		request.addParameter("name", "bleujin");

		assertEquals("bleujin", request.get().getText());
	}

	public void testAttribute() throws Exception {
		Aradon aradon = AradonTester.create().register("test", "/{name}", ConfirmLet.class).getAradon();

		AradonClient ac = AradonClientFactory.create(aradon);
		assertEquals("hero", ac.createRequest("/test/hero").post().getText());
	}

	public void testSerialRequest() throws Exception {
		Aradon aradon = AradonTester.create().register("test", "/", ConfirmLet.class).getAradon();

		AradonClient ac = AradonClientFactory.create(aradon);
		ISerialRequest req = ac.createSerialRequest("/test/") ;
		Map<String, String> result = req.put(new String[]{"jin", "hero"}, Map.class) ;
		assertEquals(2, result.keySet().size()) ;
	}

}

class ConfirmLet extends AbstractServerResource {

	@Get
	public String param() {
		return getInnerRequest().getParameter("name");
	}

	@Post
	public String attribute() {
		return getInnerRequest().getAttribute("name");
	}

	@Put
	public Map<String, String> serial(String[] names) {
		Map<String, String> result = MapUtil.newMap();
		for (String name : names) {
			result.put(name, name);
		}
		return result;
	}
}
