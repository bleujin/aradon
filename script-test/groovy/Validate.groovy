import net.ion.radon.core.filter.IFilterResult;

import net.ion.radon.core.let.InnerRequest;


import org.restlet.*;
import net.ion.radon.core.let.*;
import net.ion.radon.core.filter.*;
import org.restlet.data.Method;
import groovy.lang.Binding;

request = request ?: InnerRequest.create(new Request(Method.POST, "http://localhost:9002"));

value = request.getFormParameter().get("abcd");

return value ? IFilterResult.CONTINUE_RESULT : IFilterResult.SKIP_RESULT
