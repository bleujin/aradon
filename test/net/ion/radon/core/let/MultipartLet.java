package net.ion.radon.core.let;

import java.io.InputStream;
import java.util.Map.Entry;

import net.ion.framework.util.IOUtil;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class MultipartLet extends DefaultLet{

	

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
			JSONObject jso = new JSONObject() ;
			for (Entry<String, Object> entry : getInnerRequest().getFormParameter().entrySet()) {
				
				if (entry.getValue() instanceof FileItem) {
					FileItem fitem = (FileItem) entry.getValue();
					InputStream is = fitem.getInputStream();
					String output = IOUtil.toString(is) ;
					jso.put(entry.getKey(), output) ;
				} else {
					jso.put(entry.getKey(), entry.getValue()) ;
				}
			}
			return new JsonRepresentation(jso) ;
		}
		return new StringRepresentation("not found uploadfile ")  ;
	}

}
