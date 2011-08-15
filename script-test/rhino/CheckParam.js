importPackage(net.ion.radon.client) ;
importPackage(net.ion.radon.core) ;
importPackage(net.ion.radon.core.let) ;
importPackage(net.ion.radon.core.filter) ;

importPackage(org.restlet) ;
importPackage(net.ion.framework.rest) ;
importPackage(org.restlet.representation) ;

	var value = request.getParameter('key')
	if (value == '' || value.startsWith('not')){ 
		println(value == '' || value.startsWith('not')) ;
		response.setStatus(org.restlet.data.Status.SERVER_ERROR_NOT_IMPLEMENTED) ;
		IFilterResult.STOP_RESULT ;
	} else {
		IFilterResult.CONTINUE_RESULT ;
	}