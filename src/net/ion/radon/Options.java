package net.ion.radon;

import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;

public class Options {

	private String[] args ;
	public Options(String[] args) {
		this.args = ObjectUtil.coalesce(args, new String[0]) ;
	}
	public String getString(String optionName, String defaultValue) {
		for (int i = 0, last = args.length; i < last; i++) {
			String prefix = "-" + optionName + ":";
			if (args[i].startsWith(prefix)) {
				return StringUtil.substringAfterLast(args[i], prefix).trim() ;
			}
		}
		return defaultValue ;
	}

	public int getInt(String optionName, int defaultValue){
		String strValue = getString(optionName, String.valueOf(defaultValue)) ;
		return StringUtil.isNumeric(strValue) ? Integer.parseInt(strValue) : defaultValue ;
	}


}
