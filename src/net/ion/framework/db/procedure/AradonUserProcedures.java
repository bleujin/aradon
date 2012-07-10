package net.ion.framework.db.procedure;

import java.sql.Connection;
import java.sql.SQLException;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;

public class AradonUserProcedures extends UserProcedures {

	public AradonUserProcedures(IDBController dc, String proc) {
		super(dc, proc);
	}
	
	public final static AradonUserProcedures create(IDBController dc, String proc){
		return new AradonUserProcedures(dc, proc);
	}
	
	@Override
	public int myUpdate(Connection conn) throws SQLException {
		return QueryHelper.myUpdate(this) ;
	}
	
	@Override
	public Rows myQuery(Connection conn) throws SQLException {
		return QueryHelper.myQuery(this) ;
	}
	

}
