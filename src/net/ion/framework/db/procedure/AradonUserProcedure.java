package net.ion.framework.db.procedure;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;

public class AradonUserProcedure extends UserProcedure {

	protected AradonUserProcedure(IDBController dc, String procSQL) {
		super(dc, procSQL);
	}
	
	public final static AradonUserProcedure create(IDBController dc, String strSQL) {
		return new AradonUserProcedure(dc, strSQL) ;
	}


	@Override
	public Statement getStatement() {
		return QueryHelper.getStatement() ;
	}

	@Override
	public int myUpdate(Connection conn) throws SQLException {
		return QueryHelper.myUpdate(this) ;
	}
	
	@Override
	public Rows myQuery(Connection conn) throws SQLException {
		return QueryHelper.myQuery(this) ;
	}
	

	@Override
	public Serializable myHandlerQuery(Connection conn, ResultSetHandler handler) throws SQLException {
		return QueryHelper.myHandlerQuery(this, handler) ;
	}

}
