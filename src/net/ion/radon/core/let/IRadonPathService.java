package net.ion.radon.core.let;

import net.ion.nradon.HttpHandler;
import net.ion.radon.core.config.IPathConfiguration;

public interface IRadonPathService {

	public IPathConfiguration getConfig() ;
	
	public HttpHandler toHttpHandler() ;

}
