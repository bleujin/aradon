package net.ion.framework.util;

import net.ion.framework.db.mongo.UnSupportedOSSystem;

import org.apache.commons.lang.SystemUtils;

public class SystemConfigLoader {

	public final static String PREFIX = "sx-8-3-2-" ; 
	
	
	public String getFilterPath() throws UnSupportedOSSystem{
		return PREFIX + getFilterName() ;
	}
	
	
	public String getFilterName() throws UnSupportedOSSystem {
		StringBuilder result = new StringBuilder(64) ;
		
		if (SystemUtils.IS_OS_AIX) {
			result.append("aix-ppc-32") ;
		} else if (SystemUtils.IS_OS_LINUX) {
			result.append("linux") ;
			if ("x86".equals(SystemUtils.OS_ARCH)){
				result.append("-x86-").append(System.getProperty("sun.arch.data.model")) ;
			} else {
				result.append("-ia-64") ;
			}
		} else if (SystemUtils.IS_OS_HP_UX) {
			result.append("hpux") ;
			if ("risc".equals(SystemUtils.OS_ARCH)){
				result.append("-risc-32") ;
			} else {
				result.append("-ia-").append(System.getProperty("sun.arch.data.model")) ;
			}
		} else if (SystemUtils.IS_OS_SUN_OS) {
			result.append("sun") ;
			if ("x86".equals(SystemUtils.OS_ARCH)){
				result.append("-x86-32") ;
			} else {
				result.append("-sparc-32") ;
			}
		} else if (SystemUtils.IS_OS_WINDOWS) {
			result.append("win") ;
			if ("x86".equals(SystemUtils.OS_ARCH)){
				result.append("-x86-").append(System.getProperty("sun.arch.data.model")) ;
			} 
		} else if (SystemUtils.IS_OS_MAC) {
			throw new UnSupportedOSSystem() ;
		} else if (SystemUtils.IS_OS_MAC_OSX) {
			throw new UnSupportedOSSystem() ;
		} else if (SystemUtils.IS_OS_SOLARIS) {
			throw new UnSupportedOSSystem() ;
		} else {
			result.append("zos-s390-31") ;
		}
		
		return result.toString() ;
	}

}
