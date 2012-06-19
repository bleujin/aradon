import net.ion.framework.util.Debug;
import net.ion.radon.core.filter.IFilterResult;

import net.ion.radon.core.IService;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.core.TreeContext;
import org.restlet.Response;
import org.restlet.Request;
import org.restlet.representation.StringRepresentation;

class XmlGroovy {
	
	public IFilterResult run(IService service, Request request, Response response){	
//		def myRequest = request ?: InnerRequest.create(new Request(Method.POST, "http://localhost:9002"));
//		def myResponse = response ?: InnerResponse.create(new Response(request),  request);
		
		String xmlString = response.getEntityAsText();
		def matcher =  (xmlString =~ /<(.*)xml(.*)?>/)
		xmlString = matcher.replaceAll("");
		
		def result = new XmlParser().parseText(xmlString).nodes.node.property
		def parsing = result.toList().groupBy {
			it.text().find("type=section")? "SECTION" : "PATH"
		}
		
		StringRepresentation newResult = new StringRepresentation(parsing.get("SECTION").toString());
		response.setEntity(new StringRepresentation("<Hello />"));
		//hmh.....
		
		Debug.line('-', newResult.getText()) ;
		
		return IFilterResult.CONTINUE_RESULT;
	}
}