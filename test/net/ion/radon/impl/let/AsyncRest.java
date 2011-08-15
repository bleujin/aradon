package net.ion.radon.impl.let;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ion.framework.db.Page;
import net.ion.framework.rest.IRequest;
import net.ion.framework.rest.IResponse;
import net.ion.framework.rest.StdObject;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.RandomUtil;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.Uniform;
import org.restlet.data.Protocol;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.StringRepresentation;

public class AsyncRest extends Restlet{

	private static AnonyThread ANONY_THREAD = null;
	static {
		ANONY_THREAD = new AnonyThread() ;
		new Thread(ANONY_THREAD).start() ;
	}
	
	
	public AsyncRest(Context context) {
		super(context) ;
	}

	public void handle(Request request, Response response){
		Uniform uniform = ObjectUtil.coalesce(request.getOnResponse(), new TestUniform(request, response)) ;
		Debug.debug(uniform) ;
		ANONY_THREAD.addUniform(uniform) ;
		// response.setOnSent(uniform) ;
		request.setOnResponse(uniform) ;
		
		try {
			Thread.sleep(1000) ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> reqmap = new HashMap<String, Object>();
		reqmap.put("page", Page.create(10, 1)) ;
		reqmap.put("nots", new Object()) ;
		IRequest req = IRequest.create(reqmap) ;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mykey", "myvalue") ;
		
		StdObject sto = StdObject.create(req, ListUtil.create(map), IResponse.EMPTY_RESPONSE) ;
		
		response.setEntity(new ObjectRepresentation(sto)) ;
	}
	
	public static void main(String[] args) throws Exception{
		// HTTP 서버를 생성하고 8182 포트에서 요청을 청취하기
		new Server(Protocol.HTTP, 8182, new AsyncRest(null)).start();
	}
}

class AnonyThread implements Runnable {

	private List<Uniform> queue = new ArrayList<Uniform>() ;
	
	public void run() {
		while(true){
			int i = 0 ;
			try {
				for (Uniform uniform : queue) {
					
					TestUniform tu = (TestUniform)uniform ;
					Request req = tu.getRequest() ;
					Response res = tu.getResponse() ;
					res.setEntity(new StringRepresentation((i++) + "'th Message")) ;
					if (uniform == null) {
						// on abort
						continue ;
					}
					uniform.handle(req, res) ;
				}
				
				Thread.sleep(RandomUtil.nextRandomInt(2000, 5000)) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addUniform(Uniform uniform){
		queue.add(uniform); 
	}
	
}



class TestUniform implements Uniform {
	
	private Request request ;
	private Response response ;
	TestUniform(Request request, Response response){
		this.request = request ;
		this.response = response ;
	}
	
	
	public void handle(Request request, Response response) {
		try {
			InputStream input = response.getEntity().getStream() ;
			if (input == null) return ;
			LineNumberReader reader = new LineNumberReader(new InputStreamReader(input)) ;
			
			String str = reader.readLine() ;
			Debug.debug(request.getClientInfo().getAddress(), str) ;
			
			request.commit(response) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Request getRequest() {
		return request;
	}
	
	public Response getResponse(){
		return response ;
	}
}

