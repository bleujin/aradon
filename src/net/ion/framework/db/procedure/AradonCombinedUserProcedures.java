package net.ion.framework.db.procedure;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Page;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.framework.util.MapUtil;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.impl.let.rdb.QueryResult;

public class AradonCombinedUserProcedures extends CombinedUserProcedures{

	private Map resultMap = MapUtil.EMPTY ;
	private AradonCombinedUserProcedures(IDBController dc, String name) {
		super(dc, name);
	}

	public static AradonCombinedUserProcedures create(IDBController dc, String name) {
		return new AradonCombinedUserProcedures(dc, name);
	}

	public int myUpdate(Connection conn) throws SQLException {
		AradonDBManager dbm = (AradonDBManager) getDBController().getDBManager() ;
		IAradonRequest request = dbm.getUpdateRequest() ;
		
		QueryResult qr = request.execute(this, QueryResult.class) ; 
		resultMap = qr.getData() ; 
		return resultMap.size() ;
	}
	
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

}
