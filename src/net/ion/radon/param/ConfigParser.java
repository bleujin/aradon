package net.ion.radon.param;

import net.sf.json.JSONObject;

public class ConfigParser{

	public static <T> T parse(String parameter, Class<T> type) {
		final T bean = (T)JSONObject.toBean(JSONObject.fromObject(parameter), type);
		return bean ;
	}

}
