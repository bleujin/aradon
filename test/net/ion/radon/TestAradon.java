package net.ion.radon;

import java.io.ObjectInputStream;
import java.util.Map;

import junit.framework.TestCase;
import net.ion.framework.rest.StdObject;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

public class TestAradon extends TestCase {

	
	protected static String TEST_CONFIG_FILE = "./resource/config/readonly-config.xml" ;
	protected Aradon aradon = null;
	public void setUp() throws Exception {
		aradon = new Aradon() ;
		aradon.getServers().add(Protocol.RIAP);
		
		Debug.setPrintLevel(Debug.Level.Debug) ;
	}
	
	public void xtestHttpAradon() throws Exception {
		aradon = new Aradon() ;
		aradon.getServers().add(Protocol.HTTP, 9002);

		aradon.init(TEST_CONFIG_FILE) ;
		aradon.start() ;
		
		Client client = new Client(Protocol.HTTP);
		Request request = new Request(Method.GET, "http://localhost:9002/hello") ; 
		Response response = client.handle(request);
		
		Debug.line(response.getEntityAsText()) ;
	}
	
	protected InnerRequest getInnerRequest(){
		return ((InnerResponse)Response.getCurrent()).getInnerRequest() ;		
	}
	
	protected InnerResponse getInnerResponse(){
		return (InnerResponse)Response.getCurrent();		
	}


	public void xtestDummyAradon() throws Exception {
		final Request request = new Request(Method.GET, "riap://component/hello?abcd=abcd");
		Response response = handle(TEST_CONFIG_FILE, request);
		
		TreeContext context = (TreeContext)request.getAttributes().get(Context.class.getCanonicalName()) ;
		IZone zone = context.getAttributeObject(IZone.class.getCanonicalName(), IZone.class) ;
		Map param = context.getAttributeObject(Form.class.getCanonicalName(), Map.class) ;
		TreeContext parentContext = context.getParentContext() ;

		Debug.line(zone, param.values(), parentContext) ;
	}

	public void xtestObject() throws Exception {
		final Request request = new Request(Method.GET, "riap://component/hello?abcd=abcd&aradon.result.format=object");
		Response response = handle(TEST_CONFIG_FILE, request);
		
		StdObject sto = (StdObject)(new ObjectInputStream(response.getEntity().getStream()).readObject()) ;
		Debug.debug(sto.getDatas()) ;
	}
	
	protected Response httpHandle(Request request){
		Client client = new Client(Protocol.HTTP);
		return client.handle(request) ;
	}
	
	protected void initAradon() throws Exception{
		initAradon(TEST_CONFIG_FILE) ;
	}
	
	protected void initAradon(String configPath) throws Exception{
		aradon.init(configPath) ;
		aradon.start() ;
		new ARadonServer(new Options(new String[0])).loadPlugIn(aradon) ;
	}
	
	protected Response handle(String configPath,  Request request) throws Exception {
		initAradon(configPath) ;

		Response response = aradon.handle(request) ;
		Debug.line('@', response) ;
		return response ;
	}
	
	protected Response handle(String configPath,  Request request, Response response) throws Exception {
		initAradon(configPath) ;
		
		aradon.handle(request, response) ;
		Debug.line('@', response) ;
		return response ;
	}
	
	public Response handle(Request request) throws Exception {
		return handle(TEST_CONFIG_FILE, request) ;
	}


}

