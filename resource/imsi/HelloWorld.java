package net.ion.bleujin;


import net.ion.framework.util.Debug;
import net.ion.framework.util.StringUtil;

public class HelloWorld {

	public void hello(){
		Debug.debug(this.getClass().getCanonicalName()) ;
		Debug.debug(StringUtil.substring("ABCDEFG", 3)) ;
	}
	
}
