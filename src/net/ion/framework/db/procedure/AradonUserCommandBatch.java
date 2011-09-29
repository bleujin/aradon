package net.ion.framework.db.procedure;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import net.ion.framework.db.IDBController;

public class AradonUserCommandBatch extends UserCommandBatch {

	protected AradonUserCommandBatch(IDBController dc, String strSQL) {
		super(dc, strSQL);
	}
	public final static AradonUserCommandBatch create(IDBController dc, String strSQL) {
		return new AradonUserCommandBatch(dc, strSQL) ;
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
