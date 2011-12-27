package net.ion.framework.db.aradon;

import junit.framework.TestCase;
import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.db.procedure.IUserCommand;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.rdb.HandlerLet;
import net.ion.radon.impl.let.rdb.QueryLet;
import net.ion.radon.impl.let.rdb.UpdateLet;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.util.AradonTester;

public class TestAbstractRemoteQuery extends TestCase{

	private IDBController localDc;
	public void setUp() throws Exception {
		super.setUp() ;
		
		DBManager dbm = new OracleDBManager("jdbc:oracle:thin:@DEV-SQL.I-ON.NET:1521:DEVSQL", "bleu", "redf") ;
		this.localDc = new DBController(dbm) ;
		localDc.initSelf() ;
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.localDc.destroySelf();
		super.tearDown();
	}
	
	
	public void xtesgtRows() throws Exception {
		Debug.debug(localDc.getRows("select * from dual")) ;
	}
	
	public void xtestSerilized() throws Exception {
		Rows rows = localDc.createUserProcedure("dept@infoBy(?)").addParam(10).execQuery() ;
		Debug.debug(rows) ;
		assertEquals(1, rows.getRowCount());
	}
}
