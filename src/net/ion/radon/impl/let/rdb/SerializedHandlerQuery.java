package net.ion.radon.impl.let.rdb;

import java.io.Serializable;
import java.sql.SQLException;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.framework.db.procedure.Queryable;
import net.ion.framework.db.procedure.SerializedQuery;

public class SerializedHandlerQuery  implements Serializable{
	
	private Queryable cmd;
	private ResultSetHandler handler;
	
	private SerializedHandlerQuery(Queryable cmd, ResultSetHandler handler) {
		this.cmd = cmd;
		this.handler = handler;
	}
	
	public  static SerializedHandlerQuery create(Queryable cmd, ResultSetHandler handler){
		return new SerializedHandlerQuery(cmd, handler);
	}
	
	
	public Object remoteQuery(IDBController dc) throws SQLException {
		SerializedQuery squery =  (SerializedQuery)cmd;
		
		
		return squery.deserializable(dc).execHandlerQuery(handler);
		
		
	}
}
