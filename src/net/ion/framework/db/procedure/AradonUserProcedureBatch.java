package net.ion.framework.db.procedure;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import net.ion.framework.db.IDBController;

public class AradonUserProcedureBatch extends UserProcedureBatch {

	public AradonUserProcedureBatch(IDBController dc, String proc) {
		super(dc, proc) ;
	}

	public final static AradonUserProcedureBatch create(IDBController dc, String strSQL) {
		return new AradonUserProcedureBatch(dc, strSQL) ;
	}

	@Override
	public Statement getStatement() {
		return QueryHelper.getStatement() ;
	}

	@Override
	public int myUpdate(Connection conn) throws SQLException {
		return QueryHelper.myUpdate(this) ;
	}

}
