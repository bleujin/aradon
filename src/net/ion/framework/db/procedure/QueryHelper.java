package net.ion.framework.db.procedure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Statement;

import net.ion.framework.db.RemoteManager;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.impl.let.rdb.SerializedHandlerQuery;

public class QueryHelper {

	static int myUpdate(Queryable cmd) {
		RemoteManager dbm = (RemoteManager) cmd.getDBController().getDBManager() ;
		ISerialRequest request = dbm.getUpdateRequest() ;
		return request.post(toSerial(cmd), Integer.class) ;
	}

	static Rows myQuery(Queryable cmd) {
		
		RemoteManager dbm = (RemoteManager) cmd.getDBController().getDBManager() ;
		ISerialRequest request = dbm.getQueryRequest() ;
		
		return request.post(toSerial(cmd) , Rows.class) ;
	}

	static Serializable myHandlerQuery(Queryable cmd, ResultSetHandler handler) {
		RemoteManager dbm = (RemoteManager) cmd.getDBController().getDBManager() ;
		ISerialRequest request = dbm.getHandlerRequest() ;
		
		SerializedHandlerQuery shandler = SerializedHandlerQuery.create(toSerial(cmd), handler);
		return request.post(shandler , Serializable.class) ;
	}

	static Statement getStatement() {
		throw new UnsupportedOperationException("this method not supported in remote") ;
	}
	
	

	static Queryable toSerial(Queryable cmd) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(bout) ;
			oout.writeObject(cmd) ;
			
			ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray())) ;
			return (Queryable) oin.readObject() ;
		} catch (IOException e) {
			throw new IllegalArgumentException(e) ;
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e) ;
		}
	}
	

}
