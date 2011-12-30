package net.ion.nradon;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.restlet.data.Cookie;

public class TestInboundCookieParser {
    @Test
    public void testParseEmpty() throws Exception {
        List<String> values = new ArrayList<String>();
        assertEquals(new ArrayList<Cookie>(), InboundCookieParser.parse(values));
    }

    @Test
    public void testParse() throws Exception {
        List<String> values = new ArrayList<String>();
        values.add("test=me");
        values.add("testing=\"now\"");

        List<Cookie> expected = new ArrayList<Cookie>();
        expected.add(new Cookie("test", "me"));
        expected.add(new Cookie("testing", "now"));

        assertEquals(expected, InboundCookieParser.parse(values));
    }
}
