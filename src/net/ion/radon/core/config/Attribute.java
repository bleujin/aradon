package net.ion.radon.core.config;

import java.util.Map;

import net.ion.framework.util.StringUtil;

public class Attribute {

	private Map<String, String> attrMap ;
	private String elementValue ;
	
	Attribute(Map<String, String> attrMap, String elementValue) {
		this.attrMap = attrMap ;
		this.elementValue = elementValue ;
	}
	
	public final static Attribute testCreate(Map<String, String> attrMap, String elementValue){
		return new Attribute(attrMap, elementValue) ;
	} 

	public String getElementValue() {
		return elementValue;
	}

	public String getValue(String key) {
		if (StringUtil.isBlank(key)) return getElementValue() ;
		return attrMap.get(key);
	}
	
	public String toString(){
		return elementValue ;
	}

}
