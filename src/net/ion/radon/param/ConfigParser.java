package net.ion.radon.param;

import net.ion.framework.parse.gson.JsonParser;

public class ConfigParser{

	public static <T> T parse(String parameter, Class<T> type) {
		final T bean = JsonParser.fromString(parameter).getAsJsonObject().getAsObject(type);
		return bean ;
	}

}
