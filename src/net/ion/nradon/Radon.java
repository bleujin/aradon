package net.ion.nradon;

import java.io.IOException;

import net.ion.nradon.config.RadonConfiguration;

public abstract class Radon {

	public abstract Radon start() throws IOException;

	public abstract Radon stop() throws IOException;

	public abstract Radon join() throws InterruptedException;

	public abstract RadonConfiguration getConfig() ;
	
//	public abstract URI getUri();
//
//	public abstract int getPort();
//
//	public abstract Executor getExecutor();
}
