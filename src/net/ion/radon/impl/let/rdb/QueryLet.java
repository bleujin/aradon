package net.ion.radon.impl.let.rdb;

import java.io.IOException;
import java.sql.SQLException;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.procedure.IParameterQueryable;
import net.ion.framework.db.procedure.SerializedQuery;

import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Execute;

public class QueryLet extends DBServerResource {


	@Execute
	public Representation execQuery(SerializedQuery squery) throws IOException, SQLException {

		IDBController dc = getDBController();
		final Rows rows = squery.deserializable(dc).execQuery();

		return toRepresentation(rows);
	}

}