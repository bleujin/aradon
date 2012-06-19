package net.ion.radon.core.path;

import java.util.HashMap;
import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.framework.util.StringUtil;

public class URLPattern<T> {

	private Map<String, T> patterns = new HashMap<String, T>() ;
	static String FIND_PATTERN =  "\\{[^/]+\\}";
	static String TRANS_PATTERN = "[^/\\?]+?";
	static String SPLIT_CHAR = "/?=&";

	public void attach(String urlPattern, T clazz) {
		// if (<T>.clazz != clazz) throw new IllegalArgumentException() ;
		patterns.put(urlPattern, clazz) ;
	}
	
	public boolean exists(String url){
		for (String pattern : patterns.keySet()) {
			if (isMatch(url, pattern)) return true ;
		}
		return false ;
	}
	
	public T getPathContext(String url){
		for (String pattern : patterns.keySet()) {
			if (isMatch(url, pattern)) return patterns.get(pattern) ;
		}
		return null ;
	}
	

    public static boolean isMatch(String url, String urlPattern) {
        String s = urlPattern.replaceAll(FIND_PATTERN, TRANS_PATTERN);
        
        
        
        return url.matches(s);
    }
	
    Map<String, String> extractPathParams(String url, String urlPattern) {
		String[] strings = StringUtil.split(urlPattern, SPLIT_CHAR);
        String[] strings1 = StringUtil.split(url, SPLIT_CHAR);
        Map<String, String> pathParams = new HashMap<String, String>();
        
        Debug.debug(url, urlPattern) ;
        
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            String string1 = strings1[i];
            if (string.matches(FIND_PATTERN)) {
                pathParams.put(string.substring(1, string.length() - 1), string1);
            }
        }
        return pathParams;
    }

	public Map<String, String> extractAttrMap(String url) {
		for (String pattern : patterns.keySet()) {
			if (isMatch(url, pattern)) return extractPathParams(url, pattern) ;
		}	
		return new HashMap<String, String>() ;
	}	

}
