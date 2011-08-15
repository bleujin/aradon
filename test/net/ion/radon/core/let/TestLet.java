package net.ion.radon.core.let;

import java.util.Arrays;
import java.util.Comparator;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.impl.section.PathInfo;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestLet extends TestAradon {

	public void testFirst() throws Exception {
		initAradon();
		SectionService section = aradon.getChildService("another");

		PathInfo pathInfo = PathInfo.create("mylet", "/bleujin", "", "test let", GreenLet.class);
		section.attach(pathInfo);

		PathService pservice = section.getChildService("mylet");
		assertEquals(true, pservice != null);

		Request request = new Request(Method.GET, "riap://component/another/bleujin?param=abcd");
		Response response = aradon.handle(request);

		assertEquals(GreenLet.class.getCanonicalName(), response.getEntityAsText());
	}
	

	public void testRequestAttribute() throws Exception {
		initAradon();
		SectionService section = aradon.getChildService("another");

		PathInfo pathInfo = PathInfo.create("mylet", "/bleujin, /bleujin/{greeting}", "", "test let", GreenLet.class);
		section.attach(pathInfo);

		PathService pservice = section.getChildService("mylet");
		assertEquals(true, pservice != null);

		Request request = new Request(Method.GET, "riap://component/another/bleujin/hi");
		Response response = aradon.handle(request);

		InnerRequest ir = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		
		Debug.debug(request.getAttributes()) ;
		Debug.debug(ir.getAttributes());
	}


	
	public void testSort() throws Exception {
		String[] urls = new String[]{"a", "abcd", "123" , "98"} ;
		Arrays.sort(urls, 0, urls.length, new Comparator<String>() {
			public int compare(String left, String right) {
				return left.length() - right.length();
			}
		}) ;
		
		assertTrue(Arrays.equals(urls, new String[]{"a", "98", "123", "abcd"})) ;
	}
}
