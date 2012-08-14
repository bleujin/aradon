package net.ion.radon.core.config;

import java.util.Map;

import net.ion.framework.util.MapUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.service.GroovyShellService;


public class ServerConfiguration {

	private final ConnectorConfiguration connectorConfig ;
	private final String id;
	private final String logConfigPath ;
	private final int shellPort ;
	private final boolean shellAutoStart ;
	
	private GroovyShellService gshell;
	
	ServerConfiguration(ConnectorConfiguration connectorConfig, String id, String logConfigPath, int shellPort, boolean shellAutoStart) {
		this.connectorConfig = connectorConfig ;
		this.id = id ;
		this.logConfigPath = logConfigPath ;
		this.shellPort = shellPort ;
		this.shellAutoStart = shellAutoStart ;
	}
	
	public String id(){
		return id ;
	}
	
	public int shellPort(){
		return shellPort ;
	}

	public boolean isShellAutoStart(){
		return shellAutoStart ;
	}
	
	public String logConfigPath(){
		return logConfigPath ;
	}

	public ConnectorConfiguration connector(){
		return connectorConfig ;
	}

	public void stopShell() {
		if (gshell != null)
			gshell.destroy();

	}

	public void launchShell(final Aradon aradon) {
		if (gshell != null)
			return;

		if (shellAutoStart == false) {
			aradon.getLogger().warning("aradon manage shell not loaded");
			return;
		}
		aradon.getLogger().warning("aradon manage shell port :" + shellPort);

		Thread shellThread = new Thread() {
			public void run() {
				gshell = new GroovyShellService(shellPort);
				Map bindings = MapUtil.create("aradon", aradon);
				gshell.setBindings(bindings);
				gshell.launch();
			}
		};
		shellThread.start();
	}
	
}
