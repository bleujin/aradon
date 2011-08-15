package net.ion.framework.db.procedure;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Page;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.framework.db.procedure.IParameterQueryable;
import net.ion.framework.db.procedure.IUserProcedureBatch;
import net.ion.framework.db.procedure.UserProcedureBatch;
import net.ion.radon.client.IAradonRequest;

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
