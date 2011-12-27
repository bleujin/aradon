package net.ion.radon.core.jmx;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;

import org.apache.commons.lang.ArrayUtils;
import org.omg.CORBA.ServiceInformation;

import net.ion.framework.util.ObjectUtil;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IRadonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ServiceMBean implements DynamicMBean {

	private final static MBeanParameterInfo[] VOID_PARAM = null;

	private IService service;

	public ServiceMBean(IService service) {
		this.service = service;

	}

	public Object getAttribute(String aname) throws AttributeNotFoundException, MBeanException, ReflectionException {
		if ("name".equals(aname)) {
			return service.getName();
		} else if ("namePath".equals(aname)) {
			return service.getNamePath();
		} else if ("parentName".equals(aname)) {
			return service.getParent().getName();
		} else if ("class".equals(aname)) {
			return service.getClass().getCanonicalName();
		} else if ("context".equals(aname)) {
			return service.getServiceContext().getAttributes().toString() ;
		}
		return null;
	}

	public AttributeList getAttributes(String[] attributes) {
		AttributeList result = new AttributeList();

		result.add(new Attribute("name", service.getName()));
		result.add(new Attribute("parentName", service.getParent().getName()));
		result.add(new Attribute("namePath", service.getNamePath()));
		result.add(new Attribute("class", service.getClass()));
		result.add(new Attribute("context", service.getServiceContext().getAttributes().toString()));
		return result;
	}

	public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
		try {
			if ("addPreFilter".equals(actionName) || "addAfterFilter".equals(actionName) || "removePreFilter".equals(actionName) || "removeAfterFilter".equals(actionName)) {
				IRadonFilter filter = createFilter(params);
				if (filter != null && "addPreFilter".equals(actionName)) service.addPreFilter(filter) ;
				if (filter != null && "addAfterFilter".equals(actionName)) service.addAfterFilter(filter) ;
				if (filter != null && "removePreFilter".equals(actionName)) service.removePreFilter(filter) ;
				if (filter != null && "removeAfterFilter".equals(actionName)) service.removeAfterFilter(filter) ;
			} else {
				Object result = IService.class.getMethod(actionName).invoke(service) ;
				return result ;
			}
		} catch (Exception ex) {
			throw new MBeanException(ex);
		}

		return null;
	}

	private IRadonFilter createFilter(Object[] params) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		String clzName = ObjectUtil.toString(params[0]) ;
		String paramJSON = ObjectUtil.toString(params[1], "[]") ;
		
		JSONArray jso = JSONArray.fromObject(paramJSON) ;
		Object[] clzParam = jso.toArray(new Object[0]) ;
		
		Class clz = Class.forName(clzName) ;
		Constructor<IRadonFilter>[] cons = clz.getDeclaredConstructors();
		for (Constructor<IRadonFilter> con : cons) {
			if (con.getParameterTypes().length == clzParam.length) {
				if (!con.isAccessible())
					con.setAccessible(true);
				return con.newInstance(clzParam);
			}
		}
		return null ;
	}

	public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		// TODO Auto-generated method stub

	}

	public AttributeList setAttributes(AttributeList attributes) {
		AttributeList list = new AttributeList();

		list.add(new Attribute("name", service.getName()));
		list.add(new Attribute("parentName", service.getParent().getName()));
		list.add(new Attribute("namePath", service.getNamePath()));
		list.add(new Attribute("class", service.getClass()));
		list.add(new Attribute("context", service.getServiceContext().getAttributes().toString()));
		
		return list;
	}

	public MBeanInfo getMBeanInfo() {
		MBeanOperationInfo[] opers = makeOperatorInfo();

		MBeanConstructorInfo[] cons = null;
		return new MBeanInfo(this.getClass().getName(), "Service Manager MBean", makeAttributeInfo(), cons, opers, null); // notifications
	}

	private MBeanAttributeInfo[] makeAttributeInfo() {
		MBeanAttributeInfo[] result = new MBeanAttributeInfo[]{
			new MBeanAttributeInfo("name", "java.lang.String", "Name", true, false, false), 
			new MBeanAttributeInfo("parentName", "java.lang.String", "parentName", true, false, false), 
			new MBeanAttributeInfo("namePath", "java.lang.String", "NamePath", true, false, false), 
			new MBeanAttributeInfo("class", "java.lang.String", "class", true, false, false), 
			new MBeanAttributeInfo("context", "java.lang.String", "context", true, false, false)
		} ;
		return result;
	}

	private MBeanOperationInfo[] makeOperatorInfo() {
		try {
			
			MBeanOperationInfo[] results =  new MBeanOperationInfo[] {
				new MBeanOperationInfo("addPreFilter", "Add PreFilter", new MBeanParameterInfo[]{new MBeanParameterInfo("filterClzName", "java.lang.String", "filter Full Name"), new MBeanParameterInfo("constructor params", "java.lang.String", "constructor params(json array expression)")}, "void", MBeanOperationInfo.ACTION),
				new MBeanOperationInfo("addAfterFilter", "Add AfterFilter", new MBeanParameterInfo[]{new MBeanParameterInfo("filterClzName", "java.lang.String", "filter Full Name"), new MBeanParameterInfo("constructor params", "java.lang.String", "constructor params(json array expression)")}, "void", MBeanOperationInfo.ACTION),
				
				new MBeanOperationInfo("getPreFilters", IService.class.getMethod("getPreFilters")),
				new MBeanOperationInfo("getAfterFilters", IService.class.getMethod("getAfterFilters")),
				
				new MBeanOperationInfo("removePreFilter", "Remove PreFilter", new MBeanParameterInfo[]{new MBeanParameterInfo("filterClzName", "java.lang.String", "filter Full Name"), new MBeanParameterInfo("constructor params", "java.lang.String", "constructor params(json array expression)")}, "void", MBeanOperationInfo.ACTION),
				new MBeanOperationInfo("removeAfterFilter", "Remove AfterFilter", new MBeanParameterInfo[]{new MBeanParameterInfo("filterClzName", "java.lang.String", "filter Full Name"), new MBeanParameterInfo("constructor params", "java.lang.String", "constructor params(json array expression)")}, "void", MBeanOperationInfo.ACTION),
				
				new MBeanOperationInfo("suspend", IService.class.getMethod("suspend") ), 
				// new MBeanOperationInfo("reload", IService.class.getMethod("reload")), 
				new MBeanOperationInfo("restart", IService.class.getMethod("restart"))
			} ;
			
			return results ;
			
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage()) ;
		} catch (SecurityException e) {
			throw new IllegalArgumentException(e.getMessage()) ;
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e.getMessage()) ;
		} 
	}

}
