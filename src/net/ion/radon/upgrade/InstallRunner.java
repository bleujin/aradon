package net.ion.radon.upgrade;

import java.io.File;
import java.io.IOException;

import net.ion.framework.parse.html.HTag;
import net.ion.radon.Options;

public class InstallRunner {

	private File configFile ;
	private String target ;
	public InstallRunner(String configPath, String target) {
		this.configFile = new File(configPath) ;
		this.target = target ;
	}

	private void install() throws IOException {
		AntCommand cmd = new AntCommand() ;
		cmd.init(new InstallContext(), HTag.EMPTY_HTAG) ;
		
		cmd.runTarget(configFile, target) ;
		
	}
	
	public static void main(String[] args) throws Exception{
		Options option = new Options(args) ;
		String configPath = option.getString("config", "./resource/install/build_install.xml") ;
		String target = option.getString("target", "hello") ;
		
		new InstallRunner(configPath, target).install() ;
	}

}
