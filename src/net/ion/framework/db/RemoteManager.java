package net.ion.framework.db;

import net.ion.radon.client.IAradonRequest;

public interface RemoteManager {

	public IAradonRequest getQueryRequest() ;

	public IAradonRequest getUpdateRequest()  ;

	public IAradonRequest getHandlerRequest() ; 

}
