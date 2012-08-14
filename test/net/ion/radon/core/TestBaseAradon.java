package net.ion.radon.core;

import net.ion.framework.util.InstanceCreationException;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestBaseAradon {

	protected Aradon testAradon() throws ConfigurationException, InstanceCreationException{
		return testAradon("./resource/config/readonly-config.xml") ;
	}
	
	protected Aradon testAradon(String configPath) throws ConfigurationException, InstanceCreationException{
		Aradon aradon = Aradon.create(configPath) ;
		aradon.start() ;
		return aradon ;
	}

	protected Response handle(Aradon aradon, String path, Method method) {
		return aradon.handle(new Request(method, "riap://component" + path)) ;
	}
}
