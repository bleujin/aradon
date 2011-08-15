package net.ion.bleujin.jci ;

import net.ion.framework.util.Debug;
import net.ion.framework.util.StringUtil;


public class HelloWorldOri {

	
	public void hello() throws Exception{
		Debug.debug("Hello World Original !!! ") ;
//		ISearchRequest request = SearchRequest.test("bleujin") ;
//		Debug.debug(request.toXML().toString()) ;
		Debug.debug(StringUtil.substring("ABCDEFG", 3)) ;
	}
	
	
}
