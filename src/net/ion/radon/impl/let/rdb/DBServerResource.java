package net.ion.radon.impl.let.rdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.procedure.IQueryable;
import net.ion.framework.db.procedure.SerializedQuery;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;

public class DBServerResource extends AbstractServerResource {

	protected IDBController getDBController() {
		IDBController dc = getContext().getAttributeObject("my.db.id", IDBController.class);
		return dc;
	}

	protected IQueryable makeQuery(IDBController dc, SerializedQuery squery) {
		IQueryable result = squery.deserializable(dc);
		return result;
	}
	
	protected Representation toRepresentation(final Rows rows) throws IOException {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(bos);
		output.writeObject(rows);

		return new InputRepresentation(new ByteArrayInputStream(bos.toByteArray()));
	}

}
