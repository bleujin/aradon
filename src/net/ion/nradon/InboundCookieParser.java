package net.ion.nradon;

import java.util.ArrayList;
import java.util.List;

import net.ion.framework.util.StringUtil;

import org.restlet.data.Cookie;

/**
 * A rather simplistic parser of "Cookie:" headers.
 */
public class InboundCookieParser {
	public static List<Cookie> parse(List<String> headerValues) {
		List<Cookie> result = new ArrayList<Cookie>();
		for (String headerValue : headerValues) {
			String[] nvPairs = headerValue.split(";");
			parse(result, nvPairs);
		}
		return result;
	}

	private static void parse(List<Cookie> result, String[] nvPairs) {
		for (String nvPair : nvPairs) {
			String[] nameAndValue = nvPair.split("=");
			if (nameAndValue[1].startsWith("\"")) {
				nameAndValue[1] = nameAndValue[1].substring(1);
			}
			if (nameAndValue[1].endsWith("\"")) {
				nameAndValue[1] = nameAndValue[1].substring(0, nameAndValue[1].length() - 1);
			}
			result.add(new Cookie(nameAndValue[0], nameAndValue[1]));
		}
	}

}
