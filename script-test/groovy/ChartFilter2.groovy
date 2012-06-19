package groovy

import org.restlet.data.MediaType;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.*;

import net.ion.radon.core.*;
import net.ion.radon.core.let.*;
import net.ion.radon.core.filter.*;
import org.restlet.*;
import net.ion.framework.rest.* ;
import org.restlet.representation.*;


	StdObject sto = request.getContext().getAttributeObject(StdObject.class.getCanonicalName()) ;
	
	
	AradonClient client = AradonClientFactory.create("http://chart.apis.google.com") 
	IAradonRequest ir = client.createRequest("/chart?chf=bg,s,EAF7FE&chs=440x200&cht=t&chco=FFFFFF,FF0000,FFFF00,00FF00&chld=AOBWCFCGCVDJDZEGGHKEMGMZNGSNTZZM&chd=t:32,60,43,14,54,17,0,100,76,12,50,18,40,98,70,29&chtm=africa")
	Representation result =  ir.get();
	
	
	response.setEntity(result);
	// response.getEntity().setMediaType(MediaType.IMAGE_ALL) ;
	
	return IFilterResult.CONTINUE_RESULT;