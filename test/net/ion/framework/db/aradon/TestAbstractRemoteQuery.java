package net.ion.framework.db.aradon;

import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.rdb.HandlerLet;
import net.ion.radon.impl.let.rdb.QueryLet;
import net.ion.radon.impl.let.rdb.UpdateLet;
import net.ion.radon.impl.section.PathInfo;

public class TestAbstractRemoteQuery extends TestAradon{

	protected IDBController remoteDc ;
	protected IDBController localDc;
	public void setUp() throws Exception {
		super.setUp() ;
		initAradon() ;
		
		this.remoteDc = new DBController(AradonDBManager.create(aradon)) ;
		remoteDc.initSelf() ;
		
		DBManager dbm = new OracleDBManager("jdbc:oracle:thin:@dev-test.i-on.net:1521:devTest", "bleu", "redf") ;
		this.localDc = new DBController(dbm) ;
		localDc.initSelf() ;
		
		SectionService ss = aradon.attach("rdb", XMLConfig.BLANK);
		ss.getServiceContext().putAttribute("my.db.id", localDc) ;
		ss.attach(PathInfo.create("query", "/query", QueryLet.class));
		ss.attach(PathInfo.create("update", "/update", UpdateLet.class));
		ss.attach(PathInfo.create("handler", "/handler", HandlerLet.class));
	}
	
}
