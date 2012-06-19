import org.restlet.data.MediaType;

import net.ion.radon.core.*;
import net.ion.radon.core.let.*;
import net.ion.radon.core.filter.*;
import org.restlet.*;
import net.ion.framework.rest.* ;
import org.restlet.representation.*;


	StdObject sto = request.getContext().getAttributeObject(StdObject.class.getCanonicalName()) ;
	
	
	def writer = new StringWriter()
	def builder = new groovy.xml.MarkupBuilder(writer) ;
	def greeting = sto.getDatas() ;
	
	builder.person() { 
       name(first:"bleu", last:"jin") { 
         age("20") 
         gender("male") 
       } 
       greetings() { 
         for (e in greeting) { message(e) } 
       } 
    }
	
	response.setEntity(new StringRepresentation(writer.toString()));
	response.getEntity().setMediaType(MediaType.TEXT_XML) ;
	
	return IFilterResult.CONTINUE_RESULT;
