package net.ion.radon.impl.let;

import java.util.List;
import java.util.Map;

import net.ion.framework.rest.StdObject;
import net.ion.framework.util.ListUtil;
import net.ion.radon.core.let.AbstractLet;
import net.ion.radon.core.let.InboundLet;
import net.ion.radon.core.let.LetResponse;

import org.restlet.data.Form;
import org.restlet.representation.Representation;

public class TestChainLet extends AbstractLet {

	@Override
	public Representation myDelete() throws Exception {
		return null;
	}

	@Override
	public Representation myGet() throws Exception {

		InboundLet let = super.lookupLet("another", "/chain");
		Form param = new Form();
		param.add("abcd", "mvlaue");
		LetResponse response = let.post(param);

//		Assert.assertEquals(1, response.getStdObject().getDatas().get(0).get("count")) ;
		
		return response.getRepresentation();
	}

	@Override
	public Representation myPost(Representation entity) throws Exception {

		LetResponse hi = lookupLet("another", "/hello?abcd=1").get(createForm("hi"));
		LetResponse hello = lookupLet("another", "/hello?abcd=2").get(createForm("hello"));

		StdObject stoHi = hi.getStdObject() ;
		StdObject stoHello = hello.getStdObject() ;
		
		List<Map<String, ?>> datas = ListUtil.newList() ;
		datas.add(stoHi.getDatas().get(0)) ;
		datas.add(stoHello.getDatas().get(0)) ;
		
		return toRepresentation(datas) ;
	}

	private Form createForm(String message) {
		Form param = new Form();
		param.add("greeting", message);
		return param;
	}

	@Override
	public Representation myPut(Representation entity) throws Exception {
		return null;
	}

}
