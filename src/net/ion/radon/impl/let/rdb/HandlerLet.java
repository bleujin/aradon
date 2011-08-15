package net.ion.radon.impl.let.rdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;

import org.eclipse.jetty.io.ByteArrayBuffer;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Execute;

public class HandlerLet extends DBServerResource {

	@Execute
	public Representation execQuery(SerializedHandlerQuery squery) throws IOException, SQLException {
		
		Object result = squery.remoteQuery(getDBController());
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(result) ;
		
		return new InputRepresentation(new ByteArrayInputStream(bos.toByteArray()));
	}

}