package net.ion.radon;

import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;

public class Options {

	private String[] args ;
	private char preChar = '-';
	
	public Options(String[] args) {
		this(args, '-') ;
	}

	public Options(String[] args, char preChar) {
		this.args = ObjectUtil.coalesce(args, new String[0]) ;
		this.preChar = preChar ;
	}

	public String getString(String optionName, String defaultValue) {
		for (int i = 0, last = args.length; i < last; i++) {
			String prefix = StringUtil.trim(preChar + optionName + ":");
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
