importPackage(net.ion.radon.client) ;
importPackage(net.ion.radon.core) ;
importPackage(net.ion.radon.core.let) ;
importPackage(net.ion.radon.core.filter) ;

importPackage(org.restlet) ;
importPackage(net.ion.framework.rest) ;
importPackage(org.restlet.representation) ;


	var client = AradonClientFactory.create("http://chart.apis.google.com") ; 
	var ir = client.createRequest("/chart?cht=p3&chs=500x250&chd=s:JMBJZ&chl=Open+Source|J2EE|Web+Service|Ajax|Other") ;
	var result =  ir.get();

	response.setEntity(result);
//	response.getEntity().setMediaType(MediaType.IMAGE_ALL) ;
	
	IFilterResult.CONTINUE_RESULT;  // return 
