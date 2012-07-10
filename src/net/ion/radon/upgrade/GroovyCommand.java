package net.ion.radon.upgrade;



import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.script.ScriptFactory;


public class GroovyCommand extends ICommand{

	@Override
	public void run() throws Exception {
		String codebase = getConfig().getAttributeValue("codebase") ;
		InstallContext context = getContext() ;
		
		GroovyClassLoader loader = ScriptFactory.groovyLoader(); 
		if (StringUtil.isNotBlank(codebase)) loader.addClasspath(codebase) ;
		
		GroovyShell s = new GroovyShell(loader);
		s.evaluate(getConfig().getOnlyText());
	}

}
