package net.ion.radon.core;

import static org.junit.Assert.assertEquals;

import java.io.Closeable;
import java.io.IOException;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InstanceCreationException;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.context.OnEventObject;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;
import org.restlet.resource.Get;

public class TestContext{


	@Test
	public void get() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ContextLet.class) ;
		at.getAradon().getServiceContext().putAttribute("context.id", "Hello") ;
		
		
		String resText = AradonClientFactory.create(at.getAradon()).createRequest("/test").get().getText() ;
		assertEquals("Hello", resText) ;
	}
	
	@Test
	public void removeLet() throws Exception {
		AradonTester at = AradonTester.create().register("abc", "/test", ContextLet.class) ;
		SectionService section = at.getAradon().getChildService("abc");
		TreeContext scontext = section.getServiceContext();
		scontext.putAttribute("context.id", "Hello") ;

		
		String resText = AradonClientFactory.create(at.getAradon()).createRequest("/abc/test").get().getText() ;
		assertEquals("Hello", resText) ;
		
		assertEquals(true, scontext.removeAttribute("context.id")) ;
		assertEquals(true, AradonClientFactory.create(at.getAradon()).createRequest("/abc/test").get() == null) ;
	}
	
	
	@Test
	public void load() throws Exception {
		AradonTester at = AradonTester.create().register("abc", "/test", ContextLet.class) ;
		SectionService section = at.getAradon().getChildService("abc");
		TreeContext scontext = section.getServiceContext();
		scontext.putAttribute("context.id", "Hello") ;

		AradonClient ac = AradonClientFactory.create(at.getAradon());
		String resText = ac.createRequest("/abc/test").get().getText() ;
		assertEquals("Hello", resText) ;

		int attrSize = scontext.getAttributes().size() ;
		loadConfig(section, scontext);
		
		
		Debug.debug(scontext.getAttributes()) ;
		assertEquals(attrSize + 2, scontext.getAttributes().size()) ; 
		
		assertEquals("Hello", ac.createRequest("/abc/test").get().getText()) ; // duplicate..
		ac.stop() ;
	}

	
	private void loadConfig(SectionService section, TreeContext scontext) throws IOException, ConfigurationException, InstanceCreationException {
		String contextConfig = "<context>" +
		"<attribute id=\"let.contact.email\">bleujin@i-on.net</attribute> " +
		"<attribute id=\"let.contact.help.doc\">/help/doc</attribute> " +
		"<attribute id=\"context.id\">Bye</attribute> " +
		"</context>" ;
		
		scontext.loadAttribute(section, XMLConfig.load(contextConfig)) ;
		
	}
	
	
	@Test
	public void closeContextAttribute() throws Exception {
		ClosableObject obj = new ClosableObject();
		Configuration config = ConfigurationBuilder.newBuilder().
			aradon().addAttribute("aradon", obj).
				sections().restSection("").addAttribute("section", obj)
					.path("abcd").addUrlPattern("/hello").addAttribute("path", obj).handler(HelloWorldLet.class)
					.path("efgh").addUrlPattern("/hi").addAttribute("hi", obj).handler(HelloWorldLet.class)
		.build() ; 
		Aradon aradon = Aradon.create(config) ;
		aradon.start() ;
		
		aradon.destorySelf() ;
		assertEquals(4, obj.calledCount) ;
	}
	
	@Test
	public void eventContextAttribute() throws Exception {
		EventObject eobj = new EventObject();
		Configuration config = ConfigurationBuilder.newBuilder().
			aradon().addAttribute("aradon", eobj).
				sections().restSection("").addAttribute("section", eobj)
					.path("abcd").addUrlPattern("/hello").addAttribute("path", eobj).handler(HelloWorldLet.class)
					.path("efgh").addUrlPattern("/hi").addAttribute("hi", eobj).handler(HelloWorldLet.class)
		.build() ; 
		Aradon aradon = Aradon.create(config) ;
		aradon.start() ;
		assertEquals(4, eobj.startEventCount) ;
		
		aradon.destorySelf() ;
		assertEquals(4, eobj.stopEventCount) ;
	}
	


}

class EventObject implements OnEventObject {

	int startEventCount = 0 ;
	int stopEventCount = 0 ;
	public void onEvent(AradonEvent event, IService service) {
		if (event == AradonEvent.START){
			startEventCount++ ;
		} else if (event == AradonEvent.STOP){
			stopEventCount++ ;
		}
	}
	
}

class ClosableObject implements Closeable {
	int calledCount = 0;
	
	public void close() throws IOException {
		this.calledCount++;
	}
	
}

class ContextLet extends AbstractServerResource {

	@Get
	public String loadGet() {
		return getContext().getAttributeObject("context.id", String.class);
	}

}

