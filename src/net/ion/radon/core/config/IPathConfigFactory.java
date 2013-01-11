package net.ion.radon.core.config;

import net.ion.nradon.let.IServiceLet;
import net.ion.radon.core.EnumClass.IMatchMode;

import org.restlet.resource.ServerResource;

public class IPathConfigFactory {

	
	public static IPathConfiguration create(String name, String urls, String description, IMatchMode matchMode, Class<? extends IServiceLet> handlerClz) {
		if (ServerResource.class.isAssignableFrom(handlerClz)) {
			return PathConfiguration.create(name, urls, description, matchMode, (Class<ServerResource>)handlerClz) ; 
		} else if (IServiceLet.class.isAssignableFrom(handlerClz)) {
			return LetPathConfiguration.create(name, urls, description, matchMode, handlerClz) ;
		}
		throw new IllegalArgumentException("exception.config.notsupported.let") ;
	}
}
