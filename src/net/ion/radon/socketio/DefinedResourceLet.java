package net.ion.radon.socketio;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.IOUtil;
import net.ion.framework.util.MapUtil;
import net.ion.radon.core.let.AbstractLet;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;

public class DefinedResourceLet extends AbstractLet{
	@Override
	protected Representation myDelete() throws Exception {
		return notImpl();
	}
	@Override
	protected Representation myGet() throws Exception {
		
		String resourceName = getRequest().getResourceRef().getLastSegment() ;
		
		if (resourceName.endsWith(".swf")) {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("net/ion/radon/socketio/" + resourceName);
			return new InputRepresentation(is, MediaType.APPLICATION_FLASH);
		} else if (resourceName.endsWith("socket.io.js")) {
			return new StringRepresentation(replaceScript(resourceName), MediaType.TEXT_JAVASCRIPT) ;
		} else {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, resourceName) ;
		}
		
	}

	private CharSequence replaceScript(String resourceName) throws IOException{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("net/ion/radon/socketio/" + resourceName);
		StringWriter writer = new StringWriter() ;
		IOUtil.copy(is, writer, "UTF-8") ;

		final StringBuffer buffer = writer.getBuffer();
		
		Map<String, String> repMap = MapUtil.newMap() ;
		repMap.put("@{SectionName}", getMySectionService().getName()) ;
		
		for (Entry<String, String> entry : repMap.entrySet()) {
			int start = buffer.indexOf(entry.getKey()) ;
			if (start < 0) continue ;
			
			buffer.replace(start, entry.getKey().length() + start, entry.getValue()) ;
		}

		return buffer;
	}
	@Override
	protected Representation myPost(Representation entity) throws Exception {
		return notImpl();
	}

	@Override
	protected Representation myPut(Representation entity) throws Exception {
		return notImpl();
	}
}
