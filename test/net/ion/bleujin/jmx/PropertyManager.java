package net.ion.bleujin.jmx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
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

public class PropertyManager implements DynamicMBean {
	private final String propertyFileName;
	private final Properties properties;

	public PropertyManager() throws IOException{
		this("./resource/ptest.prop") ;
	}
	
	public PropertyManager(String propertyFileName) throws IOException {
		this.propertyFileName = propertyFileName;
		properties = new Properties();
		load();
	}

	public synchronized String getAttribute(String name) throws AttributeNotFoundException {
		String value = properties.getProperty(name);
		if (value != null)
			return value;
		else
			throw new AttributeNotFoundException("No such property: " + name);
	}

	public synchronized void setAttribute(Attribute attribute) throws InvalidAttributeValueException, MBeanException, AttributeNotFoundException {
		String name = attribute.getName();
		if (properties.getProperty(name) == null)
			throw new AttributeNotFoundException(name);
		Object value = attribute.getValue();
		if (!(value instanceof String)) {
			throw new InvalidAttributeValueException("Attribute value not a string: " + value);
		}
		properties.setProperty(name, (String) value);
		try {
			save();
		} catch (IOException e) {
			throw new MBeanException(e);
		}
	}

	public synchronized AttributeList getAttributes(String[] names) {
		AttributeList list = new AttributeList();
		for (String name : names) {
			String value = properties.getProperty(name);
			if (value != null)
				list.add(new Attribute(name, value));
		}
		return list;
	}

	public synchronized AttributeList setAttributes(AttributeList list) {
		Attribute[] attrs = (Attribute[]) list.toArray(new Attribute[0]);
		AttributeList retlist = new AttributeList();
		for (Attribute attr : attrs) {
			String name = attr.getName();
			Object value = attr.getValue();
			if (properties.getProperty(name) != null && value instanceof String) {
				properties.setProperty(name, (String) value);
				retlist.add(new Attribute(name, value));
			}
		}
		try {
			save();
		} catch (IOException e) {
			return new AttributeList();
		}
		return retlist;
	}

	public Object invoke(String name, Object[] args, String[] sig) throws MBeanException, ReflectionException {
		if (name.equals("reload") && (args == null || args.length == 0) && (sig == null || sig.length == 0)) {
			try {
				load();
				return null;
			} catch (IOException e) {
				throw new MBeanException(e);
			}
		}
		throw new ReflectionException(new NoSuchMethodException(name));
	}

	public synchronized MBeanInfo getMBeanInfo() {
		SortedSet<String> names = new TreeSet<String>();
		for (Object name : properties.keySet())
			names.add((String) name);
		MBeanAttributeInfo[] attrs = new MBeanAttributeInfo[names.size()];
		Iterator<String> it = names.iterator();
		for (int i = 0; i < attrs.length; i++) {
			String name = it.next();
			boolean isReadable = true ;
			boolean isWritable = true ;
			boolean isIs = false ;
			attrs[i] = new MBeanAttributeInfo(name, "java.lang.String", "Property " + name, isReadable, isWritable, isIs); 
		}
		MBeanParameterInfo[] params = null ;
		MBeanOperationInfo[] opers = { new MBeanOperationInfo("reload", "Reload properties from file", params, "void", MBeanOperationInfo.ACTION) };
		MBeanConstructorInfo[] cons = null ;
		return new MBeanInfo(this.getClass().getName(), "Property Manager MBean", attrs, cons, opers, null); // notifications
	}

	private void load() throws IOException {
		InputStream input = new FileInputStream(propertyFileName);
		properties.load(input);
		input.close();
	}

	private void save() throws IOException {
		String newPropertyFileName = propertyFileName + "$$new";
		File file = new File(newPropertyFileName);
		OutputStream output = new FileOutputStream(file);
		String comment = "Written by " + this.getClass().getName();
		properties.store(output, comment);
		output.close();
		if (!file.renameTo(new File(propertyFileName))) {
			throw new IOException("Rename " + newPropertyFileName + " to " + propertyFileName + " failed");
		}
	}
}
