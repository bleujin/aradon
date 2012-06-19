package net.ion.radon.impl.let.rdb;

import java.io.IOException;
import java.sql.SQLException;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.procedure.ICombinedUserProcedures;
import net.ion.framework.db.procedure.Queryable;
import net.ion.framework.db.procedure.SerializedQuery;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Post;
import org.restlet.resource.Put;


public class UpdateLet extends AbstractServerResource {
	
	@Put
	public QueryResult execCombinedQuery(Queryable squery) throws SQLException {
		IDBController dc = getContext().getAttributeObject(IDBController.class.getCanonicalName(), IDBController.class) ;
		
		ICombinedUserProcedures cups =  (ICombinedUserProcedures)((SerializedQuery)squery).deserializable(dc) ;
		cups.execUpdate() ;
		return QueryResult.create(cups.getResultMap()) ;
	}
	
	@Post
	public int execUpdate(Queryable squery) throws IOException, SQLException {
		
		IDBController dc = getContext().getAttributeObject(IDBController.class.getCanonicalName(), IDBController.class) ;
		return dc.execUpdate(squery) ;
	}

}