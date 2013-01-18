package net.ion.nradon.helpers;

import java.util.List;

import net.ion.framework.util.ListUtil;

public class InboundCookieParser {
    public static List<HttpCookie> parse(List<String> headerValues) {
        List<HttpCookie> result = ListUtil.newList() ;
        for (String headerValue : headerValues) {
            String[] nvPairs = headerValue.split(";");
            for (String nvPair : nvPairs) {
                String[] nameAndValue = nvPair.split("=");
                if (nameAndValue[1].startsWith("\"")) {
                    nameAndValue[1] = nameAndValue[1].substring(1);
                }
                if (nameAndValue[1].endsWith("\"")) {
                    nameAndValue[1] = nameAndValue[1].substring(0, nameAndValue[1].length() - 1);
                }
                result.add(new HttpCookie(nameAndValue[0], nameAndValue[1]));
            }
        }
        return result;
    }

}