package net.ion.framework.db.procedure;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Page;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.framework.db.procedure.IParameterQueryable;
import net.ion.framework.db.procedure.IUserCommand;
import net.ion.framework.db.procedure.UserCommand;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.IAradonRequest;

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
	public Object myHandlerQuery(Connection conn, ResultSetHandler handler) throws SQLException {
		return QueryHelper.myHandlerQuery(this, handler) ;
	}
	
	
	
}
