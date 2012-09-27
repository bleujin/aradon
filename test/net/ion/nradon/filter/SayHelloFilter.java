package net.ion.nradon.filter;

import net.ion.framework.util.Debug;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.radon.core.IService;

public class SayHelloFilter extends DefaultXRadonFilter{

	@Override
	public void init(IService service){
		Debug.line(service) ;
	}
	
	@Override
	public void httpEnd(IService iservice, HttpRequest hreq, HttpResponse hres) {
		Debug.line(hres.status()) ;
	}

	@Override
	public void httpStart(IService iservice, HttpRequest hreq, HttpResponse hres) {
		Debug.line(hreq.method(), hreq.uri()) ;
	}

}
