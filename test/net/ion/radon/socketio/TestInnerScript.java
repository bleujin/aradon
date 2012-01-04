package net.ion.radon.socketio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;
import net.ion.framework.convert.html.CleanerProperties;
import net.ion.framework.convert.html.HtmlCleaner;
import net.ion.framework.convert.html.SimpleHtmlSerializer;
import net.ion.framework.convert.html.TagNode;
import net.ion.framework.convert.html.XPatherException;
import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.MapUtil;

public class TestInnerScript extends TestCase {

	protected void setUp() throws Exception {
	}

	public void testInsertScript() throws IOException, XPatherException {

		final FileInputStream fis = new FileInputStream("resource/socket/chat.html");

		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		TagNode node = cleaner.clean(fis, "utf-8");
		final TagNode head = node.findElementByName("head", true);
		TagNode appendScript = new TagNode("script");
		appendScript.setAttribute("type", "text/Javascript");
		appendScript.setAttribute("src", "/noti/socket.io.js");

		head.insertChild(0, appendScript);

		final StringWriter writer = new StringWriter();
		node.serialize(new SimpleHtmlSerializer(props), writer);
		Debug.debug(writer);
		//
		// assertEquals(node.evaluateXPath("//p[1]/*").length, 0);
		// assertTrue("freestylo".equals(superstar.toString()));
		// assertEquals(node.evaluateXPath("//p[2]/*").length, 1);
	}

	public void testReplaceScript() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("net/ion/radon/socketio/socket.io.js");
		StringWriter writer = new StringWriter();

		IOUtil.copy(is, writer, "UTF-8");

		final StringBuffer buffer = writer.getBuffer();

		Map<String, String> repMap = MapUtil.newMap();
		repMap.put("@{SectionName}", "noti");
		repMap.put("@{PathName}", "chat");

		for (Entry<String, String> entry : repMap.entrySet()) {
			int start = buffer.indexOf(entry.getKey());
			if (start < 0)
				continue;

			buffer.replace(start, entry.getKey().length() + start, entry.getValue());
		}

		Debug.debug(buffer);

		is.close();
		writer.close();

	}

}
