
import org.restlet.data.* 
import org.restlet.*

import net.ion.radon.core.filter.IFilterResult;

request = request ?: new Request(Method.POST, "http://localhost/");

println "HelloWorld Groovy $request.method"

return IFilterResult.CONTINUE_RESULT 