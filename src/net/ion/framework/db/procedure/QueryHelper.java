package net.ion.framework.db.procedure;

import java.sql.Statement;

import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.RemoteManager;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.impl.let.rdb.SerializedHandlerQuery;

public class QueryHelper {

	static int myUpdate(Queryable cmd) {
		RemoteManager dbm = (RemoteManager) cmd.getDBController().getDBManager() ;
		IAradonRequest request = dbm.getUpdateRequest() ;
		return request.execute(cmd, Integer.class) ;
	}

	static Rows myQuery(Queryable cmd) {
		
		RemoteManager dbm = (RemoteManager) cmd.getDBController().getDBManager() ;
		IAradonRequest request = dbm.getQueryRequest() ;
		
		return request.execute(cmd, Rows.class) ;
	}

	public static Object myHandlerQuery(Queryable cmd, ResultSetHandler handler) {
		RemoteManager dbm = (RemoteManager) cmd.getDBController().getDBManager() ;
		IAradonRequest request = dbm.getHandlerRequest() ;
		
		return request.execute( SerializedHandlerQuery.create(cmd, handler) , Object.class) ;
	}

	static Statement getStatement() {
		throw new UnsupportedOperationException("this method not supported in remote") ;
	}

}
