package net.ion.radon.impl.let.rdb;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

import net.ion.framework.db.IDBController;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Post;

public class HandlerLet extends AbstractServerResource {

	@Post
	public Serializable execQuery(SerializedHandlerQuery squery) throws IOException, SQLException {
		
		IDBController dc = getContext().getAttributeObject(IDBController.class.getCanonicalName(), IDBController.class) ;
		return squery.remoteQuery(dc) ;
	}

}