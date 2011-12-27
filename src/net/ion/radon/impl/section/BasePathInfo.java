package net.ion.radon.impl.section;

import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.path.URLPattern;

import org.restlet.data.Reference;

public abstract class BasePathInfo {
	
	public abstract Class getHandlerClass() ;
	
	public abstract String[] getUrls() ;

	public abstract String getName()  ;

	public abstract IMatchMode getMatchMode() ;

	public abstract String getDescription()  ;

	// reference.. relative address value
	public boolean isMatchURL(Reference reference) {
		for (String urlPattern : getUrls()) {
			if (getMatchMode() == IMatchMode.STARTWITH) {
				if (StringUtil.startsWith(reference.getPath(), StringUtil.substringBefore(urlPattern, "{"))) {
					return true;
				}
			} if (URLPattern.isMatch(reference.getPath(), urlPattern)) {
				return true;
			}
		}
		return false ;
	}
}
