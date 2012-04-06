package net.ion.radon.upgrade;

import java.io.File;
import java.io.IOException;

import net.ion.framework.parse.html.HTag;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.UTF8FileUtils;

import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntCommand extends ICommand{

	
	@Override
	public void run() throws IOException {
		HTag config = getConfig() ;

		File file = File.createTempFile("ws", "script") ;
		file.deleteOnExit() ;
		
		UTF8FileUtils.writeStringToFile(file, config.getOnlyText(), "UTF-8") ;
		
		runTarget(file, null) ;
	}
	
	public void runTarget(File configFile, String target) throws IOException{
		
		BuildLogger logger = new DefaultLogger() ;
		logger.setMessageOutputLevel(Project.MSG_INFO) ;
		logger.setOutputPrintStream(System.out) ;
		logger.setErrorPrintStream(System.out) ;
		logger.setEmacsMode(true) ;
		
		ProjectHelper ph = ProjectHelper.getProjectHelper() ;
		Project p = new Project() ;
		p.setBaseDir(new File("./")) ;
		p.addBuildListener(logger) ;
		p.init() ;
		p.addReference("ant.projectHelper", ph) ;
		ph.parse(p, configFile) ;
		p.executeTarget(ObjectUtil.coalesce(target, p.getDefaultTarget())) ;
	}

}
