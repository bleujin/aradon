package net.ion.radon.core.jmx;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.core.IService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.filter.IRadonFilter;
import net.sf.json.JSONArray;

public class ContextMBean implements DynamicMBean {

	private IService service;
	private TreeContext tcontext;

	public ContextMBean(IService service, TreeContext tcontext) {
		this.service = service;
		this.tcontext = tcontext;
	}

	public Object getAttribute(String aname) throws AttributeNotFoundException, MBeanException, ReflectionException {
		if ("zone".equals(aname)) {
			return tcontext.getZone();
		} else if ("logger".equals(aname)) {
			return tcontext.getLogger();
		} else if ("parentContext".equals(aname)) {
			return tcontext.getParentContext();
		} else if ("attributes".equals(aname)) {
			return tcontext.getAttributes().toString();
		}
		return null;
	}

	public AttributeList getAttributes(String[] attributes) {
		AttributeList result = new AttributeList();

		result.add(new Attribute("zone", tcontext.getZone()));
		result.add(new Attribute("logger", tcontext.getLogger()));
		result.add(new Attribute("attributes", tcontext.getAttributes().toString()));
		result.add(new Attribute("parentContext", tcontext.getParentContext()));
		return result;
	}

	public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
		try {
			if ("addAttribute".equals(actionName)) {
				tcontext.loadAttribute(service, loadConfig(ObjectUtil.toString(params[0])));
			} else if ("removeAttribute".equals(actionName)) {
				tcontext.removeAttribute(ObjectUtil.toString(params[0]));
			}
		} catch (ConfigurationException e) {
			throw new MBeanException(e);
		} catch (InstanceCreationException e) {
			throw new MBeanException(e);
		} catch (IOException e) {
			throw new MBeanException(e);
		}
		return null;
	}

	private XMLConfig loadConfig(String configString) throws IOException, ConfigurationException {
		return XMLConfig.load(configString);
	}

	public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		// TODO Auto-generated method stub

	}

	public AttributeList setAttributes(AttributeList attributes) {
		AttributeList result = new AttributeList();

		result.add(new Attribute("zone", tcontext.getZone()));
		result.add(new Attribute("logger", tcontext.getLogger()));
		result.add(new Attribute("attributes", tcontext.getAttributes().toString()));
		result.add(new Attribute("parentContext", tcontext.getParentContext()));
		return result;
	}

	public MBeanInfo getMBeanInfo() {
		MBeanConstructorInfo[] cons = null;
		return new MBeanInfo(this.getClass().getName(), "Context Manager MBean", makeAttributeInfo(), cons, makeOperatorInfo(), null); // notifications
	}

	private MBeanAttributeInfo[] makeAttributeInfo() {
		MBeanAttributeInfo[] result = new MBeanAttributeInfo[] { new MBeanAttributeInfo("zone", "java.lang.String", "zone", true, false, false), new MBeanAttributeInfo("logger", "java.lang.String", "logger", true, false, false),
				new MBeanAttributeInfo("attributes", "java.lang.String", "attributes", true, false, false), new MBeanAttributeInfo("parentContext", "java.lang.String", "parentContext", true, false, false) };
		return result;
	}

	private MBeanOperationInfo[] makeOperatorInfo() {
		try {
			MBeanOperationInfo[] results = new MBeanOperationInfo[] { 
					new MBeanOperationInfo("addAttribute", "ex) &lt;context>\n&lt;attribute id=let.contact.email>bleujin@i-on.net&lt;/attribute>\n&lt;/context>", new MBeanParameterInfo[] { new MBeanParameterInfo("xmlConfig", "java.lang.String", "XmlConfig") }, "void", MBeanOperationInfo.ACTION),
					new MBeanOperationInfo("removeAttribute", "Remove Attribute", new MBeanParameterInfo[] { new MBeanParameterInfo("attrId", "java.lang.String", "attribute KeyId") }, "void", MBeanOperationInfo.ACTION) };

			return results;

		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (SecurityException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
