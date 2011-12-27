package net.ion.bleujin.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.PathService;
import net.ion.radon.core.RouterSection;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.jmx.ContextMBean;
import net.ion.radon.core.jmx.ServiceMBean;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.util.AradonTester;

import com.sun.jdmk.comm.AuthInfo;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class TestAradonJMX {

	public static void main(String[] args) throws Exception {
		
		Aradon aradon = new Aradon() ;
		SectionService section = aradon.attach("my", XMLConfig.BLANK) ;
		section.attach(PathInfo.create("hello", "/test", HelloWorldLet.class)) ;
		section.attach(PathInfo.create("hello2", "/test2", HelloWorldLet.class)) ;
		section.getServiceContext().putAttribute("my.hello.name", "bleujin") ;

		MBeanServer server = MBeanServerFactory.createMBeanServer();
		for (IService sec : aradon.getChildren()) {
			server.registerMBean(new ServiceMBean(sec), new ObjectName("net.ion.radon.section:Name=" + sec.getNamePath() + ",serverType=net.ion.radon.core.SectionService"));
			server.registerMBean(new ContextMBean(sec, sec.getServiceContext()), new ObjectName("net.ion.radon.section:Name=" + sec.getNamePath() + "Context,serverType=net.ion.radon.core.TreeContext"));
			for (IService ps : sec.getChildren()) {
				server.registerMBean(new ServiceMBean(ps), new ObjectName("net.ion.radon.section." + sec.getName() + ":Name=" + ps.getNamePath() + ",serverType=net.ion.radon.core.PathService"));
				server.registerMBean(new ContextMBean(ps, ps.getServiceContext()), new ObjectName("net.ion.radon.section." + sec.getName() + ":Name=" + ps.getNamePath() + "Context,serverType=net.ion.radon.core.TreeContext"));
			}
		}
		server.registerMBean(new ServiceMBean(aradon), new ObjectName("net.ion.radon.core:Name=" + aradon.getNamePath() + ",serverType=net.ion.radon.core.Aradon"));
		server.registerMBean(new ContextMBean(aradon, aradon.getServiceContext()), new ObjectName("net.ion.radon.core:Name=AradonContext,serverType=net.ion.radon.core.Aradon"));

		
		server.registerMBean(ManagementFactory.getThreadMXBean(), new ObjectName("java.lang:type=Threading")) ;
		server.registerMBean(ManagementFactory.getMemoryMXBean(), new ObjectName("java.lang:type=Memory")) ;
		
		aradon.startServer(8787) ;

		// Create an RMI connector and start it
		// JMXServiceURL serviceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://61.250.201.157:19999/test");
		// JMXConnectorServer connector = JMXConnectorServerFactory.newJMXConnectorServer(serviceURL, null, server);
		// connector.start();

		// -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8999 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
		// -Dcom.sun.management.jmxremote
		// -Dcom.sun.management.jmxremote.port=8999
		// -Dcom.sun.management.jmxremote.ssl=false
		// -Dcom.sun.management.jmxremote.authenticate=false

//		System.setProperty("com.sun.management.jmxremote", "");
//		System.setProperty("com.sun.management.jmxremote.port", "9999");
//		System.setProperty("com.sun.management.jmxremote.ssl", "false");
//		System.setProperty("com.sun.management.jmxremote.authenticate", "false");

		// Crate an MBeanServer and HTML adaptor
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		ObjectName adapterName = new ObjectName("SimpleAgent:name=htmladapter,port=8000");
		server.registerMBean(adapter, adapterName);

		adapter.setPort(8000);
		adapter.addUserAuthenticationInfo(new AuthInfo("bleujin", "redf")) ;
		adapter.start();

		// System.setProperty("com.sun.management.jmxremote.port", "9999") ;

	}

	

}
