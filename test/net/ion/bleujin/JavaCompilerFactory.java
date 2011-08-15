package net.ion.bleujin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jci.compilers.JavaCompiler;
import org.apache.commons.jci.utils.ConversionUtils;

// Referenced classes of package org.apache.commons.jci.compilers:
//            JavaCompiler

public final class JavaCompilerFactory {

	public JavaCompilerFactory() {
	}

	/**
	 * @deprecated Method getInstance is deprecated
	 */

	public static JavaCompilerFactory getInstance() {
		return INSTANCE;
	}

	public JavaCompiler createCompiler(String pHint) {
		String className;
		if (pHint.indexOf('.') < 0)
			className = "org.apache.commons.jci.compilers." + ConversionUtils.toJavaCasing(pHint) + "JavaCompiler";
		else
			className = pHint;
		Class clazz = (Class) classCache.get(className);
		if (clazz == null)
			try {
				clazz = Class.forName(className);
				classCache.put(className, clazz);
			} catch (ClassNotFoundException e) {
				clazz = null;
			}
		if (clazz == null)
			return null;
		try {
			return (JavaCompiler) clazz.newInstance();
		} catch (Throwable t) {
			t.printStackTrace() ;
			return null;
		}
	}

	/**
	 * @deprecated Field INSTANCE is deprecated
	 */
	private static final JavaCompilerFactory INSTANCE = new JavaCompilerFactory();
	private final Map classCache = new HashMap();

}