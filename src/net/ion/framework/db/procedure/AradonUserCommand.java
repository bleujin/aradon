package net.ion.framework.db.procedure;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;

public class AradonUserCommand  extends UserCommand {

	
	protected AradonUserCommand(IDBController dc, String strSQL) {
		super(dc, strSQL);
	}

	public final static AradonUserCommand create(IDBController dc, String strSQL) {
		return new AradonUserCommand(dc, strSQL) ;
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
