package net.ion.radon.impl.let;



import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import net.ion.framework.parse.html.HTag;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.impl.let.monitor.MonitorLet;
import net.ion.radon.impl.section.PathInfo;

import org.apache.commons.beanutils.PropertyUtils;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Status;

public class TestMonitorLet extends TestAradon{
	

	public void testMonitorLoad() throws Exception {
		loadMonitorLet();

		Request request = new Request(Method.GET, "riap://component/another/monitor?aradon.result.format=xml");
		Response response = aradon.handle(request);
		
		String xmlString = response.getEntityAsText(); 
		assertNotNull(xmlString);
		HTag htag = HTag.createGeneral(new StringReader(xmlString), "result").getChild("nodes") ;
		assertTrue(htag.getChildren().size() > 0);
	}


	private void loadMonitorLet() throws Exception {
		initAradon();
		SectionService section = aradon.getChildService("another");
		PathInfo pathInfo = PathInfo.create("mylet", "/monitor, /monitor/{section}, /monitor/{section}/{path}", "", "test let", MonitorLet.class);
		section.attach(pathInfo);
	}
	
	
	public void testPut() throws Exception {
		loadMonitorLet() ;
		Request request = new Request(Method.PUT, "riap://component/another/monitor/another/hello?aradon.result.format=xml");
		Response response = aradon.handle(request);
		
		assertEquals(Status.SUCCESS_OK, response.getStatus());
		assertTrue(response.getEntityAsText().startsWith("restart"));
	}
	
	public void testDelete() throws Exception {
		initAradon();
		SectionService section = aradon.getChildService("another");
		
		assertEquals(4, section.getChildren().size()) ;
		
		section.attach(PathInfo.create("mylet", "/monitor, /monitor/{section}, /monitor/{section}/{path}", "", "test let", MonitorLet.class));
		assertEquals(5, section.getChildren().size()) ;
		
		Request request = new Request(Method.DELETE,  "riap://component/another/monitor/another/hello");
		Response response =  aradon.handle(request);
		
		assertEquals(Status.SUCCESS_OK, response.getStatus());
		assertTrue(response.getEntityAsText().startsWith("suspend"));
	}
	
	public void beanToMap(java.lang.Object bean, java.util.Map properties) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map map = PropertyUtils.describe(bean);
		map.remove("class");
		properties.putAll(map);
		
	}
}
