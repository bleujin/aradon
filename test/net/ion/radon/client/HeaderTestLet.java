package net.ion.radon.client;

import net.ion.framework.util.StringUtil;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Put;

public class HeaderTestLet extends AbstractServerResource{
	
	@Put
	public int testPut() throws Exception {
		String nameHeader = getInnerRequest().getHeader("name");
		return StringUtil.length(nameHeader) ;
	}
}
