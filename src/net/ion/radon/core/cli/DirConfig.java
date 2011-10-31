package net.ion.radon.core.cli;

import java.io.File;

public class DirConfig {

	File baseDir ;
	private DirConfig(File baseDir) {
		this.baseDir = baseDir ;
	}

	public static DirConfig create(File baseDir) {
		return new DirConfig(baseDir);
	}
	
	public File getDir(){
		return baseDir ;
	}

}
