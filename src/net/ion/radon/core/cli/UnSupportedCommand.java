package net.ion.radon.core.cli;

import java.io.PrintWriter;

public class UnSupportedCommand extends ICommand {

	private String[] cs ;
	public UnSupportedCommand(String[] cs) {
		this.cs = cs ;
	}
	
	public void execute(PrintWriter writer){
		writer.println("UnSupported Command : " + cs[0]) ;
		writer.flush() ;
	}

}
