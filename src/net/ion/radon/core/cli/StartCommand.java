package net.ion.radon.core.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.ListUtil;
import net.ion.radon.core.except.AradonException;

public class StartCommand extends ICommand {

	private String[] cs ;
	public StartCommand(String[] cs) {
		this.cs = cs ;
	}

	@Override
	public void execute(PrintWriter pwriter) throws AradonException {

		
		try {
			List<String> cmds = ListUtil.toList("cmd.exe", "/c", "dir") ;
			// cmds.add("java -cp lib/aradon/embed/iframework_2.1.jar;lib/aradon/embed/apache_extend_fat.jar;lib/aradon/embed/core_fat.jar;lib/aradon/embed/jci_fat.jar;lib/aradon/embed/jetty_fat.jar;lib/aradon/embed/rest_fat.jar;bin/ net.ion.radon.core.cli.AradonMain  -Xms128m -Xmx256m -jar aradon_0.4.jar -port:9002 -config:./resource/config/aradon-config.xml");
			File file = new File("./bin");
			ProcessBuilder pb = new ProcessBuilder(cmds) ;
			Map<String, String> env = pb.environment() ;
			pb.directory(file) ;
			Process process = pb.start();

		    BufferedReader stdOut   = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		    String s = null ;
			while ((s =   stdOut.readLine()) != null) pwriter.println(s);
		    while ((s = stdError.readLine()) != null) pwriter.println(s);
		    
			//Process p = Runtime.getRuntime().exec("java", ) ;
		    
		    pwriter.write("Exit Code: " + process.exitValue());
		    
		    pwriter.flush() ;
		    pwriter.close() ;
			// ARadonServer.main(args) ;
		} catch (Exception e) {
			throw AradonException.create(e) ;
		}
	}
	
	public static void main(String[] args) throws Exception {
		new StartCommand(new String[0]).execute(new PrintWriter(System.out)) ;
	}

}
