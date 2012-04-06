package net.ion.radon.core;

import static org.junit.Assert.*;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;
import org.restlet.data.Method;
import org.restlet.resource.Get;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InstanceCreationException;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.EnumClass.PlugInApply;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.util.AradonTester;
import junit.framework.TestCase;

public class TestSectionAttach {

	@Test
	public void ignore() throws Exception {
		Aradon aradon = newAradon();
		
		RouterSection s1 = SectionFactory.createSection(aradon, "s1", XMLConfig.BLANK) ;
		aradon.attach(s1) ;

		RouterSection s2 = SectionFactory.createSection(aradon, "s1", XMLConfig.BLANK) ;
		aradon.attach(s2) ;

		assertEquals(1, aradon.getChildren().size()) ;
	}


	@Test
	public void bestMatch() throws Exception {
		Aradon aradon = AradonTester.create()
			.mergeSection("")
			.addLet("/", "test", IMatchMode.STARTWITH, Case1Let.class).getAradonTester()
			.mergeSection("new")
			.addLet("/", "test", IMatchMode.STARTWITH, Case2Let.class).getAradonTester()
			.getAradon() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		assertEquals(200, ac.createRequest("/").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/hello").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/hello/ged").handle(Method.GET).getStatus().getCode()) ;
		assertEquals("1", ac.createRequest("/hello/ged").handle(Method.GET).getEntityAsText()) ;
		assertEquals("2", ac.createRequest("/new/ged").handle(Method.GET).getEntityAsText()) ;
	}
	
	
	@Test
	public void overwrite() throws Exception {
		Aradon aradon = newAradon();

		RouterSection s1 = SectionFactory.createSection(aradon, "s1", XMLConfig.BLANK) ;
		aradon.attach(s1) ;

		RouterSection s2 = SectionFactory.createSection(aradon, "s1", XMLConfig.BLANK) ;
		s2.attach(PathInfo.create("p1", "/p1", HelloWorldLet.class)) ;
		aradon.attach(s2, PlugInApply.OVERWRITE) ;
		
		assertEquals(1, aradon.getChildService("s1").getChildren().size()) ; 
	}
	
	
	@Test
	public void merge() throws Exception {
		Aradon aradon = newAradon();

		RouterSection s1 = SectionFactory.createSection(aradon, "s1", XMLConfig.BLANK) ;
		s1.attach(PathInfo.create("p1", "/p1", HelloWorldLet.class)) ;
		aradon.attach(s1) ;

		RouterSection s2 = SectionFactory.createSection(aradon, "s1", XMLConfig.BLANK) ;
		s2.attach(PathInfo.create("p2", "/p2", HelloWorldLet.class)) ;
		aradon.attach(s2, PlugInApply.MERGE) ;
		
		assertEquals(2, aradon.getChildService("s1").getChildren().size()) ; 
	}
	
	
	@Test
	public void apply() {
		PlugInApply pa = PlugInApply.create("overwrite") ;
		Debug.line(pa) ;
	}
	
	
	
	
	private Aradon newAradon() throws ConfigurationException, InstanceCreationException {
		Aradon aradon = new Aradon() ;
		aradon.init(XMLConfig.BLANK) ;
		return aradon;
	}
}


class Case1Let extends AbstractServerResource {
	@Get
	public String myName(){
		return "1" ;
	}
}

class Case2Let extends AbstractServerResource {
	@Get
	public String myName(){
		return "2" ;
	}
}
