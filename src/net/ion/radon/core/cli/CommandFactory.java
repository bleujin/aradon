package net.ion.radon.core.cli;

import net.ion.framework.util.StringUtil;

public class CommandFactory {

	public static ICommand createCommand(String cname) {
		
		String[] cs = StringUtil.split(cname, " \t") ;
		final String firstCommandName = cs[0];
		
		if ("exit".equalsIgnoreCase(firstCommandName)){
			return new ExitCommand() ;
		} else if ("startup".equals(firstCommandName)) {
			return new StartCommand(cs) ;
		} else if ("connect".equals(firstCommandName)) {
			return new ConnectCommand(cs) ;
		} else if (StringUtil.isBlank(firstCommandName)) {
			return ICommand.BLANK ;
		} else {
			return new UnSupportedCommand(cs) ;
		}
	}

}
