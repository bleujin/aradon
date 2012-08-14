package net.ion.radon.core.config;

import java.io.File;
import java.util.List;

import net.ion.framework.util.ListUtil;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;

public class PlugInsConfigurationBuilder extends AbstractConfigurationBuilder<PlugInsConfiguration> implements IConfigurationChildBuilder{


	private String homePath ;
	private List<String> paths ;
	private String aradonId ;
	protected PlugInsConfigurationBuilder(IConfigurationChildBuilder builder) {
		super(builder);
		this.paths = ListUtil.newList() ;
	}

	@Override
	public PlugInsConfiguration create() {
		return new PlugInsConfiguration(aradonId, homePath, paths);
	}

	public PlugInsConfigurationBuilder formLoad(XMLConfig config) {
		this.aradonId = config.getString("server-config[@id]", "emanon") ;
		
		String pluginPathDirs = config.getString("plugin[@includepath]", "plugin/;") ;
		for (String path : StringUtil.split(pluginPathDirs, ";")) {
			addDir(path) ;
		}
		
		this.homePath = config.getString("plugin[@home]", "") ;
		return this ;
	}

	public File findAradonFile(String remainPath){
		String homeDir = System.getProperty("aradon." + aradonId + ".home.dir") ;
		return new File(PathMaker.getFilePath(homeDir, remainPath)) ;
	}

	
	public PlugInsConfigurationBuilder addDir(String path) {
		paths.add(path) ;
		return this ;
	}

}
