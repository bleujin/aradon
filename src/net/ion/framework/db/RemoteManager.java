package net.ion.framework.db;

import net.ion.radon.client.ISerialRequest;

public interface RemoteManager {

	public ISerialRequest getQueryRequest() ;

	public ISerialRequest getUpdateRequest()  ;

	public ISerialRequest getHandlerRequest() ; 

}
