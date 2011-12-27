package net.ion.radon.upgrade;

import net.ion.framework.parse.html.HTag;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.TreeContext;

public abstract class ICommand {

	private TreeContext context ;
	private HTag config ;
	public abstract void run() throws Exception;

	public void init(TreeContext context, HTag config){
		this.context = context ;
		this.config = config ;
	}
	
	protected TreeContext getContext(){
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
