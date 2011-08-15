package net.ion.framework.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;

import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.procedure.RepositoryService;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InstanceCreationException;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;

public class AradonDBManager extends DBManager implements RemoteManager {

	private AradonClient client;
	
	private AradonDBManager(Aradon aradon) {
		this.client = AradonClientFactory.create(aradon);
	}

	public final static AradonDBManager create(Aradon aradon) {
		return new AradonDBManager(aradon);
	}

//	public AradonDBManager(String configPath) throws Exception {
//		Aradon aradon = new Aradon();
//		aradon.init(configPath);
//		aradon.start();
//		// new ARadonServer(new Options(new String[0])).loadPlugIn(aradon) ;
//
//		this.client = AradonClientFactory.create(aradon);
//	}

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
		return new AradonRepositoryService();
	}

	@Override
	protected void myDestroyPool() throws Exception {
		; // no action
	}

	@Override
	protected void myInitPool() throws SQLException {
		; // no action
	}

	public IAradonRequest getQueryRequest() {
		return client.createRequest("/rdb/query");
	}

	public IAradonRequest getUpdateRequest() {
		return client.createRequest("/rdb/update");
	}

	public IAradonRequest getHandlerRequest() {
		return client.createRequest("/rdb/handler");
	}

	protected void heartbeatQuery(IDBController dc) throws SQLException {
		; // no action
	}

}
