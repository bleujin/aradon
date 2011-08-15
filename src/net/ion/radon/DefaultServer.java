package net.ion.radon;

import java.io.File;
import java.io.FileWriter;

import net.ion.framework.db.mongo.MongoRunner;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.XMLConfig;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;

public class DefaultServer {

	private DefaultServer() {
	}

	
	
	private static void shutdownHook(final Aradon aradon) {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					aradon.stop() ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		// System.setProperty("MONGO.POOLSIZE", "2") ;
	}

	public static void main(final String[] args) throws Exception {
		try {
			Options options = new Options(args) ;
			
			String configFileName = options.getString("config", "resource/config/aradon-config.xml") ; 
			int port = options.getInt("port", 9002) ;
			XMLConfig config = XMLConfig.create(configFileName) ;
			
			final String cmd = config.getString("repository[@cmd]") ;
			String content = config.getString("repository.properties") ;

			//File tfile = File.createTempFile("aradon", "repository") ;
			File tfile = new File("./aradon_repository_config") ;
			if (tfile.exists()) tfile.delete() ;
			// Debug.line("repository-config-file", configFileName,  tfile.getCanonicalPath(), content) ;
			
			final String mongoConfigFileName = tfile.getAbsolutePath() ;
			FileWriter writer = new FileWriter(tfile) ;
			IOUtils.write(content, writer) ;
			writer.flush() ;
			writer.close() ;
			tfile.deleteOnExit() ;
			
			PropertiesConfiguration pc = new PropertiesConfiguration(tfile) ;
			
			File path = new File(pc.getString("dbpath")) ;
			if (! path.exists()) path.mkdirs() ;
			
			new Thread(){
				public void run(){
					new MongoRunner().run(cmd, mongoConfigFileName) ;
				}
			}.start() ;
			Debug.debug("Mongo Start") ;
			
			Aradon aradon = new LocalServer().start(configFileName, port) ;
			DefaultServer.shutdownHook(aradon) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
