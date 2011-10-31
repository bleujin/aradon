package net.ion.radon.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.server.TestAradonServer;

public class TestLogging extends TestAradon{

	public void xtestViewRecentLog() throws Exception {
		initAradon();
		Logger log = aradon.getLogger();
		Debug.line(log.getClass(), log.getResourceBundle(), log.getResourceBundleName());
		
		log.info("Hello") ;
		
		Request request = new Request(Method.GET, "riap://component/") ;
		Response response = aradon.handle(request) ;
		
	}

	public void xtestLogManager() throws Exception {
		FileInputStream input = new FileInputStream(new File("resource/config/log4j.properties")) ;
		LogManager.getLogManager().readConfiguration(input) ;
		
		Logger logger = Logger.getLogger(this.getClass().getName()) ;
		
		assertEquals(false, logger.isLoggable(Level.FINE)) ;
		
		logger.warning("Hello") ;
	}
	
	public void testBoolean() throws Exception {
		assertTrue(Boolean.valueOf("true")) ;
	}
	
	
	public void xtestLogger() throws Exception {

		// System.setProperty("java.util.logging.config.file", "/eclipse/workspace/ARadon/resource/config/log4.properties") ;

		Logger theLogger = Logger.getLogger(getClass().getName());
		System.out.println("Current Logger level:" + theLogger.getLevel()); // I still dont know why its null
		System.out.println("Current Resource Bundle Name:" + theLogger.getResourceBundle());

		theLogger.log(Level.SEVERE, "I AM SEVERE");
		theLogger.log(Level.WARNING, "I AM WARNING");
		theLogger.log(Level.INFO, "I AM INFO ");
		theLogger.log(Level.CONFIG, "I AM CONFIG ");
		theLogger.log(Level.FINE, "I AM FINE");
		theLogger.log(Level.FINER, "I AM FINER ");
		theLogger.log(Level.FINEST, "I AM FINEST ");

		Handler[] handlers = theLogger.getHandlers();
		Debug.debug(handlers, theLogger.isLoggable(Level.FINE));
	}
//
//	public static Logger prepareLogging() {
//		File loggingConfigurationFile = new File("resource/imsi/log4j.properties.temp");
//		
//
//		Logger logger = Logger.getLogger(TestAradonServer.class.getName());
//
//		// it only generates the configuration file
//		// if it really doesn't exist.
//		if (!loggingConfigurationFile.exists()) {
//			Writer writer = null;
//			try {
//				
//				OutputStream output = new FileOutputStream(loggingConfigurationFile) ;
//				writer = new BufferedWriter(new OutputStreamWriter(output));
//
//				// The configuration file is a property file.
//				// The Properties class gives support to
//				// define and persist the logging configuration.
//				Properties logConf = new Properties();
//				logConf.setProperty("handlers", "java.util.logging.FileHandler," + "java.util.logging.ConsoleHandler");
//				logConf.setProperty(".level", "ALL");
//				logConf.setProperty("java.util.logging.ConsoleHandler.level", "ALL");
//				logConf.setProperty("java.util.logging.ConsoleHandler.formatter", "java.util.logging.SimpleFormatter");
//				
//				logConf.setProperty("java.util.logging.FileHandler.level", "ALL");
//				logConf.setProperty("java.util.logging.FileHandler.pattern", "/aradon/log/application.log");
//				logConf.setProperty("java.util.logging.FileHandler.limit", "1000000");
//				logConf.setProperty("java.util.logging.FileHandler.count", "4");
//				logConf.setProperty("java.util.logging.FileHandler.formatter", "java.util.logging.XMLFormatter");
//				logConf.store(output, "Generated");
//			} catch (IOException ex) {
//				logger.log(Level.WARNING, "Logging configuration file not created", ex);
//			} finally {
//				try {
//					writer.close();
//				} catch (IOException ex) {
//					logger.log(Level.WARNING, "Problems to save " + "the logging configuration file in the disc", ex);
//				}
//			}
//		}
//		// This is the way to define the system
//		// property without changing the command line.
//		// It has the same effect of the parameter
//		// -Djava.util.logging.config.file
//		Properties prop = System.getProperties();
//		prop.setProperty("java.util.logging.config.file", "resource/imsi/log4j.properties.temp");
//
//		// It creates the log directory if it doesn't exist
//		// In the configuration file above we specify this
//		// folder to store log files:
//		// logConf.setProperty(
//		// "java.util.logging.FileHandler.pattern",
//		// "log/application.log");
//		File logDir = new File("log");
//		if (!logDir.exists()) {
//			logger.info("Creating the logging directory");
//			logDir.mkdir();
//		}
//
//		// It overwrites the current logging configuration
//		// to the one in the configuration file.
//		try {
//			LogManager.getLogManager().readConfiguration();
//		} catch (IOException ex) {
//			logger.log(Level.WARNING, "Problems to load the logging " + "configuration file", ex);
//		}
//		return logger;
//	}
}
