package net.ion.radon.core.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.TreeContext;

public interface AttributeValue {

	public Object get(TreeContext context) throws InstanceCreationException;
	
}

class ApplicationAttributeValue implements AttributeValue {

	private XMLConfig config;
	private Object value;
	private boolean isCalledMethod = false;

	ApplicationAttributeValue(XMLConfig config, Object value) {
		this.config = config;
		this.value = value;
	}

	public synchronized Object get(TreeContext context) throws InstanceCreationException {
		if (isCalledMethod) {
			return value;
		} else {
			try {
				isCalledMethod = true;
				final String methodName = config.getString("call[@method]");
				if (StringUtil.isNotBlank(methodName)) {
					Method method = value.getClass().getMethod(methodName, TreeContext.class);
					method.invoke(value, context);
				}
				return get(context);
			} catch (Exception ex) {
				throw new InstanceCreationException(ex) ;
			}
		}
	}

}

class RequestAttributeValue implements AttributeValue {

	private XMLConfig config ;
	RequestAttributeValue(XMLConfig config){
		this.config = config ;
	}
	
	public Object get(TreeContext context) throws InstanceCreationException {
		Object result = ConfigCreator.createConfiguredInstance(config);
		try {
			final String methodName = config.getString("call[@method]");
			if (StringUtil.isNotBlank(methodName)) {
				Method method = result.getClass().getMethod(methodName, TreeContext.class);
				method.invoke(result, context);
			}
		} catch (SecurityException ex) {
			throw new InstanceCreationException(ex);
		} catch (IllegalArgumentException ex) {
			throw new InstanceCreationException(ex);
		} catch (NoSuchMethodException ex) {
			throw new InstanceCreationException(ex);
		} catch (IllegalAccessException ex) {
			throw new InstanceCreationException(ex);
		} catch (InvocationTargetException ex) {
			throw new InstanceCreationException(ex);
		}
		return result;
	}
	
}
