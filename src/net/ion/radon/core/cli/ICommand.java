package net.ion.radon.core.cli;

import java.io.PrintWriter;

import net.ion.radon.core.except.AradonException;

public abstract class ICommand {

	public final static ICommand BLANK = new ICommand(){

		@Override
		public void execute(PrintWriter printWriter) {
		}} ;

	public boolean hasContinue() {
		return true;
	}

	public abstract void execute(PrintWriter printWriter) throws AradonException  ;

	public String getPrefix() {
		return "aradon:";
	}
	
}
