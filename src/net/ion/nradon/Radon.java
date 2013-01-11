package net.ion.nradon;

import java.util.concurrent.Future;

import net.ion.nradon.config.RadonConfiguration;

public abstract class Radon implements Endpoint<Radon>{

	public abstract Future<Radon> start() ;

	public abstract Future<Radon> stop() ;

	public abstract RadonConfiguration getConfig() ;
	
//	public abstract URI getUri();
//
//	public abstract int getPort();
//
//	public abstract Executor getExecutor();
}
