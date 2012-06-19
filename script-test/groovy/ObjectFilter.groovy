import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;

import org.restlet.Request;
import org.restlet.Response;

import net.ion.radon.core.*;
import net.ion.radon.core.let.*;
import org.restlet.*;
import net.ion.framework.rest.* ;
import org.restlet.representation.*;

class DummyObject{
	
	public IFilterResult run(IService service, Request request, Response response){	
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
				for (e in greeting) { message(e)
				}
			}
		}
		
		response.setEntity(new StringRepresentation(writer.toString()));
		return IFilterResult.CONTINUE_RESULT;
	}
}