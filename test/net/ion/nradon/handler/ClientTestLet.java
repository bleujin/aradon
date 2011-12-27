package net.ion.nradon.handler;


import org.restlet.data.ClientInfo;
import org.restlet.resource.Get;
import org.restlet.security.User;

import net.ion.framework.util.Debug;
import net.ion.radon.core.let.AbstractServerResource;

public class ClientTestLet extends AbstractServerResource {

	
	@Get
	public User getClient(){
		ClientInfo ci = getInnerRequest().getClientInfo();
		
		Debug.line(ci.getUser());
		Debug.line(ci.getPort(), ci.getAddress()) ;
		Debug.line(getInnerRequest().getResourceRef().getHostDomain()) ;
		Debug.line(getInnerRequest().getResourceRef()) ;
		
		return ci.getUser() ;
	}
}
