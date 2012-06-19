package net.ion.radon.client;

import java.util.List;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.CacheDirective;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.header.CacheDirectiveReader;

public class TestClientCache {

	
	@Ignore(" if needed")
	@Test
	public void testCallDaum() throws Exception {

		AradonClient ac = AradonClientFactory.create("http://icon.daumcdn.net");

		Client cli = new Client(Protocol.HTTP);

		for (int i : ListUtil.rangeNum(10)) {
			Request req = new Request(Method.GET, "http://icon.daumcdn.net/w/c/10/12/37691415937798319.png");

			req.setCacheDirectives(ListUtil.toList(CacheDirective.publicInfo(), CacheDirective.maxAge(24 * 60 * 60 * 30)));

			// Debug.debug(CacheDirective.publicInfo(), CacheDirective.maxAge(24 * 60 * 60 * 30));
			

			// Form headerForm = new Form() ;
			// headerForm.add("If-Modified-Since", "Fri, 10 Dec 2010 01:59:10 GMT") ;
			// headerForm.add("Cache-Control", "max-age=0") ;
			//
			// req.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, headerForm);

			Response res = cli.handle(req);
			Debug.debug(res.getStatus(), res.getEntity().getSize());

		}
		
		

	}

	@Ignore(" if needed")
	@Test
	public void testCD() throws Exception {
		CacheDirectiveReader cdr1 = new CacheDirectiveReader("no-cache, private, no-store");

		List<CacheDirective> vals1 = cdr1.readValues();
		for (CacheDirective cd : vals1) {
			Debug.info(cd.toString());
		}

		// directive with semicolon as a separator fails, reads only 1st value
//		CacheDirectiveReader cdr2 = new CacheDirectiveReader("no-cache; private; no-store");
//
//		List<CacheDirective> vals2 = cdr2.readValues();
//		for (CacheDirective cd : vals2) {
//			Debug.info(cd.toString());
//		}
	}

}
