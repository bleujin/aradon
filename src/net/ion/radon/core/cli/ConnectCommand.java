package net.ion.radon.core.cli;

import java.io.PrintWriter;

public class ConnectCommand extends ICommand {

	private String address = "http://localhost:9002/"; // id/pwd@hostname:port
	public ConnectCommand(String[] cs) {
		// this.address = cs.length == 1 ? "localhost" : "localhost" ; 
	}

	@Override
	public void execute(PrintWriter printWriter) {
		// TODO Auto-generated method stub

	}

	public String getPrefix() {
		return address + ":";
	}

	
}
