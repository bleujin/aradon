package net.ion.radon.core.let;

import java.io.IOException;

import net.ion.framework.rest.StdObject;
import net.ion.framework.util.StringUtil;
import net.ion.radon.param.MyParameter;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class LetResponse {

	private InnerRequest request ;
	private InnerResponse response ;
	private Status status ;
	private LetResponse(InnerRequest request, InnerResponse response) {
		this.request = request ;
		this.response = response ;
		this.status = response.getStatus();
	}

	static LetResponse create(InnerRequest request, InnerResponse response) {
		return new LetResponse(request, response);
	}
	
	public Status getStatus(){
		return this.status ;
	}
	
	public InnerRequest getRequest(){
		return this.request ;
	}
	
	public String toString(){
		return StringUtil.toString(response, "<null/>") ;
	}
	
	public Object getObject(String keyPath) throws IOException{
		Representation entity = getRepresentation() ;
		// Debug.line(entity.getText()) ;
		if (MediaType.APPLICATION_JSON.equals(entity.getMediaType())) {
			return MyParameter.create(entity.getText()).getParam(keyPath) ;
		} else {
			throw new IllegalArgumentException("not supported media type :" + entity.getMediaType()) ;
		}
	}
	
	public String getString(String keyPath) throws IOException{
		return StringUtil.toString(getObject(keyPath)) ;
	}

	public int getInteger(String keyPath) throws NumberFormatException, IOException{
		return Integer.parseInt(getString(keyPath)) ;
	}

	public Representation getRepresentation() {
		return response.getEntity();
	}

	public StdObject getStdObject() throws IOException, ClassNotFoundException {
		return response.getStdObject() ;
	}

}
