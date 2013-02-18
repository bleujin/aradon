package net.ion.radon.core.annotation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import net.ion.framework.util.Debug;
import net.ion.framework.util.StringUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.HttpMultipartEntity;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.apache.commons.fileupload.FileItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestFormAnnotation {

	private Aradon aradon ;
	private AradonClient ac ;
	
	@Before
	public void setUp() throws Exception {
		this.aradon = AradonTester.create()
			.register("", "/form", FormLet.class).getAradon() ;
		this.ac = AradonClientFactory.create(aradon);
	}
	
	@After
	public void tearDown() throws Exception {
		ac.stop() ;
		aradon.stop() ;
	}
	
	@Test
	public void queryOrder() throws Exception {
		Response res = ac.createRequest("/form?p=myparam&m=queryorder").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("queryOrder", res.getEntityAsText()) ;
	}
	
	@Test
	public void formParamArray() throws Exception {
		Response res = ac.createRequest("/form?m=array").addParameter("a", "v1").addParameter("a", "v2").handle(Method.POST);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("v1,v2", res.getEntityAsText()) ;
	}
	

	@Test
	public void formParamIntArray() throws Exception {
		Response res = ac.createRequest("/form?m=arrayint").addParameter("a", "1").addParameter("a", "2").handle(Method.POST);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("1,2", res.getEntityAsText()) ;
	}
	
	@Test
	public void formMultiPart() throws Exception {
		HttpMultipartEntity rf = new HttpMultipartEntity() ;
		rf.addParameter("name", "bleujin") ;
		rf.addParameter("uploadfile", new File("./build.xml")) ;
		Response res = ac.createRequest("/form?m=multipart").setEntity(rf.makeRepresentation()).handle(Method.POST) ;
		
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("bleujinbleujin", res.getEntityAsText()) ;
	}
	
	@Test
	public void formFormData() throws Exception {
		HttpMultipartEntity rf = new HttpMultipartEntity() ;
		rf.addParameter("name", "bleujin") ;
		rf.addParameter("uploadfile", new File("./resource/favicon.ico")) ;
		Response res = ac.createRequest("/form?m=formdata").setEntity(rf.makeRepresentation()).handle(Method.POST) ;
		
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("favicon.ico/application/octet-stream/25214", res.getEntityAsText()) ;
	}
	
	@Test
	public void formParamArray2() throws Exception {
		Response res = ac.createRequest("/form?m=array2").addParameter("a", "v1").addParameter("a", "v2").handle(Method.POST);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("v1,v2", res.getEntityAsText()) ;
	}


	@Test
	public void formFormData2() throws Exception {
		HttpMultipartEntity rf = new HttpMultipartEntity() ;
		rf.addParameter("name", "bleujin") ;
		rf.addParameter("uploadfile", new File("./resource/favicon.ico")) ;
		Response res = ac.createRequest("/form?m=formdata2").setEntity(rf.makeRepresentation()).handle(Method.POST) ;
		
		assertEquals(200, res.getStatus().getCode()) ;
		Debug.line(res.getEntityAsText()) ;
//		assertEquals("favicon.ico/application/octet-stream/25214", res.getEntityAsText()) ;
	}
	
	
	@Test
	public void formMultipartBean() throws Exception {
		
	}

}


class FormLet extends AbstractServerResource{
	
	@Get("?m=queryorder")
	public String queryOrder(){
		return "queryOrder" ;
	}
	
	@Post("?m=array")
	public String array(@FormParams("a") String[] array){
		return StringUtil.join(array, ",") ;
	}

	@Post("?m=array2")
	public String array2(@FormParam("a") String[] array){
		return StringUtil.join(array, ",") ;
	}


	@Post("?m=arrayint")
	public String arrayInt(@FormParams("a") int[] array){
		return StringUtil.join(array, ',') ;
	}
	
	@Post("?m=multipart")
	public String multiPart(@FormParam("name") String name){
		return name + name;
	}

	@Post("?m=formdata")
	public String formData(@FormDataParam("uploadfile") FileItem file) throws IOException{
		String contentType = file.getContentType() ;
		long size = file.getSize() ;
		file.getInputStream().close() ;
		return file.getName() + "/"+ contentType + "/" + size ;
	}

	@Post("?m=formdata2")
	public String formData2(@FormParam("uploadfile") FileItem file, @FormParam("m") String m, @FormParam("name") String name) throws IOException{
		String contentType = file.getContentType() ;
		long size = file.getSize() ;
		file.getInputStream().close() ;
		return file.getName() + "/"+ contentType + "/" + size + m + name;
	}

}
