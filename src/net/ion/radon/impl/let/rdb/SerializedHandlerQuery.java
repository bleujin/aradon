package net.ion.radon.impl.let.rdb;

import java.io.Serializable;
import java.sql.SQLException;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.framework.db.procedure.Queryable;
import net.ion.framework.db.procedure.SerializedQuery;

public class SerializedHandlerQuery  implements Serializable{
	
	private static final long serialVersionUID = 7239940390420836103L;
	private Queryable cmd;
	private ResultSetHandler handler;
	
	private SerializedHandlerQuery(Queryable cmd, ResultSetHandler handler) {
		this.cmd = cmd;
		this.handler = handler;
	}
	
	public  static SerializedHandlerQuery create(Queryable cmd, ResultSetHandler handler){
		return new SerializedHandlerQuery(cmd, handler);
	}
	
	
	public Serializable remoteQuery(IDBController dc) throws SQLException {
		SerializedQuery squery =  (SerializedQuery)cmd;
		
		return (Serializable)squery.deserializable(dc).execHandlerQuery(handler);
		
		
	}
}
