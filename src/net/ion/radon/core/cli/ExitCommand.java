package net.ion.radon.core.cli;

import java.io.PrintWriter;

public class ExitCommand extends ICommand {

	public boolean hasContinue() {
		return false ;
	}

	@Override
	public void execute(PrintWriter printWriter) {
		printWriter.println("Bye Aradon....") ;
	}
}
