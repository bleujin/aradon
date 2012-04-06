package net.ion.radon.client;

import java.io.InputStream;
import java.util.Map.Entry;

import net.ion.framework.util.IOUtil;
import net.ion.radon.core.let.AbstractLet;

import org.apache.commons.fileupload.FileItem;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

/**
 * <p>
 * Title: TestMultipartLet.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: I-ON Communications
 * </p>
 * <p>
 * Date : 2011. 10. 12.
 * </p>
 * 
 * @author novision
 * @version 1.0
 */
public class TestMultipartLet extends AbstractLet {

	@Override
	protected Representation myPut(Representation entity) throws Exception {
		return null;
	}

	@Override
	protected Representation myDelete() throws Exception {
		return null;
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
			StringBuilder sb = new StringBuilder();
			for (Entry<String, Object> entry : getInnerRequest().getFormParameter().entrySet()) {

				if (entry.getValue() instanceof FileItem && "uploadfile".equals(entry.getKey())) {
					FileItem fitem = (FileItem) entry.getValue();
					InputStream is = fitem.getInputStream();
					String output = IOUtil.toString(is);
					sb.append(output);
				} else {
					sb.append(entry.getValue());
				}
			}
			return new StringRepresentation(sb);
		}
		return new StringRepresentation("not found uploadfile ");
	}

	@Override
	protected Representation myGet() throws Exception {
		return null;
	}

}
