package net.ion.framework.db;

import java.io.ObjectStreamException;
import java.sql.Connection;
import java.sql.SQLException;

import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.procedure.AradonUserCommand;
import net.ion.framework.db.procedure.RepositoryService;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.core.Aradon;

public class AradonDBManager extends DBManager implements RemoteManager {

	private AradonClient aclient;
	private AradonRepositoryService rservice = new AradonRepositoryService() ; 
	
	public AradonDBManager(String hostAddress){
		this(AradonClientFactory.create(hostAddress)) ;
	}
	
	private AradonDBManager(AradonClient aclient) {
		this.aclient = aclient;
	}

	public final static AradonDBManager create(Aradon aradon) {
		return new AradonDBManager(AradonClientFactory.create(aradon));
	}

	public final static AradonDBManager create(String hostAddress) {
		return new AradonDBManager(AradonClientFactory.create(hostAddress));
	}

	
	public Connection getConnection() throws SQLException {
		return AradonConnection.Fake;
	}

	@Override
	public int getDBManagerType() {
		// DBType.Aradon -- not defined
		return 17;
	}

	@Override
	public String getDBType() {
		return "aradon";
	}

	public RepositoryService getRepositoryService() {
		return rservice;
	}

	@Override
	protected void myDestroyPool() throws Exception {
		; // no action
	}

	@Override
	protected void myInitPool() throws SQLException {
		; // no action
	}

	public ISerialRequest getQueryRequest() {
		return aclient.createSerialRequest("/rdb/query");
	}

	public ISerialRequest getUpdateRequest() {
		return aclient.createSerialRequest("/rdb/update");
	}

	public ISerialRequest getHandlerRequest() {
		return aclient.createSerialRequest("/rdb/handler");
	}

	protected void heartbeatQuery(IDBController dc) throws SQLException {
		; // no action
	}

}
