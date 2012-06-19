package net.ion.radon.core.let;

import java.io.InputStream;
import java.util.Map.Entry;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.core.representation.JsonObjectRepresentation;

import org.apache.commons.fileupload.FileItem;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class MultipartLet extends DefaultLet{

	

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
			JsonObject jso = new JsonObject() ;
			for (Entry<String, Object> entry : getInnerRequest().getFormParameter().entrySet()) {
				
				if (entry.getValue() instanceof FileItem) {
					FileItem fitem = (FileItem) entry.getValue();
					InputStream is = fitem.getInputStream();
					String output = IOUtil.toString(is) ;
					jso.addProperty(entry.getKey(), output) ;
				} else {
					jso.addProperty(entry.getKey(), ObjectUtil.toString(entry.getValue())) ;
				}
			}
			return new JsonObjectRepresentation(jso) ;
		}
		return new StringRepresentation("not found uploadfile ")  ;
	}

}
