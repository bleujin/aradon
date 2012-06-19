package groovy

import org.restlet.data.MediaType;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.*;

import net.ion.radon.core.*;
import net.ion.radon.core.let.*;
import net.ion.radon.core.filter.*;
import org.restlet.*;
import net.ion.framework.rest.* ;
import net.ion.framework.util.* ;
import org.restlet.representation.*;


	StdObject sto = request.getContext().getAttributeObject(StdObject.class.getCanonicalName()) ;
	Debug.line(response.getClass(), response.getEntity().getClass()) ;
	
	AradonClient client = AradonClientFactory.create("http://chart.apis.google.com") 
	IAradonRequest ir = client.createRequest("/chart?cht=p3&chs=500x250&chd=s:JMBJZ&chl=Open+Source|J2EE|Web+Service|Ajax|Other")
	
	Representation result =  ir.get();
	
	
	response.setEntity(result);
	// response.getEntity().setMediaType(MediaType.IMAGE_ALL) ;
	
	return IFilterResult.CONTINUE_RESULT;
