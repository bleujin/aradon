package net.ion.radon.client;

import java.io.IOException;
import java.io.Serializable;

import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Execute;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import net.ion.framework.util.Debug;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.let.AbstractServerResource;

public class HeaderTestLet extends AbstractServerResource{
	
	@Put
	public int testPut() throws Exception {
		String nameHeader = getInnerRequest().getHeader("name");
		return StringUtil.length(nameHeader) ;
	}
}
