package net.ion.radon.socketio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import net.ion.framework.convert.html.CleanerProperties;
import net.ion.framework.convert.html.HtmlCleaner;
import net.ion.framework.convert.html.SimpleHtmlSerializer;
import net.ion.framework.convert.html.TagNode;
import net.ion.framework.convert.html.XPatherException;
import net.ion.framework.util.PathMaker;
import net.ion.radon.core.let.AbstractLet;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class StaticFileLet extends AbstractLet {

	@Override
	protected Representation myDelete() throws Exception {
		return notImpl();
	}

	@Override
	protected Representation myGet() throws Exception {
		String baseDir = getContext().getAttributeObject("socket.base.dir", "./", String.class) ;
		
		StringBuffer buffer = insertScriptTag(new FileInputStream(PathMaker.getFilePath(baseDir, getRequest().getResourceRef().getLastSegment())));
		return new StringRepresentation(buffer, MediaType.TEXT_HTML);
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		return notImpl();
	}

	@Override
	protected Representation myPut(Representation entity) throws Exception {
		return notImpl();
	}
	
	
	private StringBuffer insertScriptTag(InputStream fis) throws IOException, XPatherException {
		
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		TagNode node = cleaner.clean(fis, "utf-8");
		final TagNode head = node.findElementByName("head", true);
		TagNode appendScript = new TagNode("script") ;
		appendScript.setAttribute("type", "text/Javascript") ;
		appendScript.setAttribute("src", "/" + getMySectionService().getName() + "/socket.io.js") ;
		
		head.insertChild(0, appendScript) ;
		
		final StringWriter writer = new StringWriter();
		node.serialize(new SimpleHtmlSerializer(props), writer) ;
		return writer.getBuffer() ;
	}

}
