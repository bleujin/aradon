package net.ion.radon.upgrade;

import net.ion.framework.parse.html.HTag;
import net.ion.framework.util.StringUtil;

public abstract class ICommand {

	private InstallContext context ;
	private HTag config ;
	public abstract void run() throws Exception;

	public void init(InstallContext context, HTag config){
		this.context = context ;
		this.config = config ;
	}
	
	protected InstallContext getContext(){
		return context ;
	}
	
	protected HTag getConfig(){
		return config ;
	}

	public String getType() {
		final String clsName = StringUtil.substringAfterLast(getClass().getName(), ".");
		return StringUtil.substringBeforeLast(clsName, "Command");
	}

}
