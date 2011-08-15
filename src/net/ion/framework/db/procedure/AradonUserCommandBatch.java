package net.ion.framework.db.procedure;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.Page;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.framework.db.procedure.IParameterQueryable;
import net.ion.framework.db.procedure.IUserCommandBatch;
import net.ion.framework.db.procedure.UserCommandBatch;

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
