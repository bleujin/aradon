package net.ion.radon.core.config;

import net.ion.framework.util.InstanceCreationException;
import net.ion.radon.core.TreeContext;

public class InstanceAttributeValue implements AttributeValue{

	private Object value ;
	private InstanceAttributeValue(Object value) {
		this.value = value ;
	}

	public final static AttributeValue create(Object value){
		return new InstanceAttributeValue(value) ;
	}
	
	public Object get(TreeContext context) throws InstanceCreationException {
		return value ;
	}

}
