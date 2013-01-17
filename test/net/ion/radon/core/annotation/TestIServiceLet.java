package net.ion.radon.core.annotation;

import java.io.File;
import java.util.concurrent.Future;

import junit.framework.Assert;
import net.ion.nradon.Radon;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.HttpMultipartEntity;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;

import org.apache.commons.fileupload.FileItem;
import org.junit.Before;
import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestIServiceLet {

	private Aradon aradon = null;
	@Before
	public void setUp() throws Exception{
		this.aradon = Aradon.create(Configuration.newBuilder()
				.aradon()
					.addAttribute("cacheName", "c1") 
				.sections()
					.restSection("").path("hello").addUrlPattern("/hello").handler(SimpleLet.class)
				.aradon().sections()
					.restSection("other").path("hello").addUrlPattern("/hello").handler(SimpleLet.class).build());
	}
	
	@Test
	public void startUp() throws Exception{
		
		Future<Radon> future = aradon.toRadon(9000).start();
		future.get() ;
		
		
	}
	
	@Test
	public void testHelloLet() throws Exception {
		Aradon myaradon = Aradon.create("resource/config/readonly-config.xml") ;  // load HiLet
	}
	
	@Test
	public void formBean() throws Exception {

		AradonClient ac = AradonClientFactory.create(aradon) ;
		HttpMultipartEntity rf = new HttpMultipartEntity() ;
		rf.addParameter("name", "bleujin") ;
		rf.addParameter("file", new File("./build.xml")) ;
		rf.addParameter("age", "20") ;
		rf.addParameter("age", "25") ;
		
		Representation result =  ac.createRequest("/hello").setEntity(rf.makeRepresentation()).post() ;
		
		Assert.assertEquals("bleujin2025application/octet-stream", result.getText()) ;
		
	}
	
}


class SimpleLet implements IServiceLet {
	
	@Get
	public String hello(@FormParam("name") String name){
		return "hello " + name ; 
	}
	
	@Post
	public String fileName(@FormBean Bean bean, @FormParam("file") FileItem file){
		return bean.getName() + bean.getAge()[0] + bean.getAge()[1] + file.getContentType();
	}
}

class Bean {
	private String name ;
	private int[] age ;
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setAge(int[] age) {
		this.age = age;
	}
	public int[] getAge() {
		return age;
	}
}
