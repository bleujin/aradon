package net.ion.radon.core.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import net.ion.radon.core.except.AradonException;

public class AradonMain {

	public static void main(String[] args){
		
		ICommand command = ICommand.BLANK ;
		while(command.hasContinue()){
			try {
				System.out.print(command.getPrefix()) ;
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) ;
				command = CommandFactory.createCommand(reader.readLine().trim()) ;
				command.execute(new PrintWriter(System.out)) ;
				
			} catch (AradonException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
