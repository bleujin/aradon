package net.ion.radon.impl.let.rdb;

import java.io.IOException;
import java.sql.SQLException;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.procedure.CombinedUserProcedures;
import net.ion.framework.db.procedure.Queryable;
import net.ion.framework.db.procedure.SerializedQuery;
import net.ion.framework.db.procedure.SerializedQuery.SerialType;

import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Execute;


public class UpdateLet extends DBServerResource {

	@Execute
	public Representation execQuery(SerializedQuery squery) throws IOException, SQLException {
		
		IDBController dc = getDBController();
		final Queryable query = squery.deserializable(dc);
		int count = query.execUpdate();
		if (squery.getSerialType() == SerialType.CombinedUserProcedures){
			return QueryResult.create(((CombinedUserProcedures)query).getResultMap()).toRepresentation();
		}
		
		return new ObjectRepresentation(count);
	}

}