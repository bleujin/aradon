package net.ion.radon.core;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import net.ion.framework.util.InstanceCreationException;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
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

		String resText = AradonClientFactory.create(at.getAradon()).createRequest("/abc/test").get().getText() ;
		assertEquals("Hello", resText) ;

		int attrSize = scontext.getAttributes().size() ;
		loadConfig(section, scontext);
		
		assertEquals(attrSize + 2, scontext.getAttributes().size()) ; 
		
		assertEquals("Hello", AradonClientFactory.create(at.getAradon()).createRequest("/abc/test").get().getText()) ; // duplicate..
	}

	
	private void loadConfig(SectionService section, TreeContext scontext) throws IOException, ConfigurationException, InstanceCreationException {
		String contextConfig = "<context>" +
		"<attribute id=\"let.contact.email\">bleujin@i-on.net</attribute> " +
		"<attribute id=\"let.contact.help.doc\">/help/doc</attribute> " +
		"<attribute id=\"context.id\">Bye</attribute> " +
		"</context>" ;

		File tfile = File.createTempFile("aradon", "config") ;
		FileUtils.writeStringToFile(tfile, contextConfig) ;
		
		scontext.loadAttribute(section, XMLConfig.create(tfile)) ;
		
	}
	
	
	
	
}

class ContextLet extends AbstractServerResource {

	@Get
	public String loadGet() {
		return getContext().getAttributeObject("context.id", String.class);
	}

}

