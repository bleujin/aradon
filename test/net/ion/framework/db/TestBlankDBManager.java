package net.ion.framework.db;

import junit.framework.TestCase;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.db.procedure.Queryable;
import net.ion.framework.db.procedure.SerializedQuery;
import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.util.AradonTester;

public class TestBlankDBManager extends TestCase{

	public void testCreate() throws Exception {
		
		AradonTester at = AradonTester.create().register("", "/test", MyQueryLet.class) ;

		Queryable query = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test").post(null, Queryable.class) ;
		Debug.line(query.getClass()) ;
		
		DBController mydc = new DBController(new OracleDBManager("jdbc:oracle:thin:@dev-sql.i-on.net:1521:devSql", "bleu", "redf")) ;
		mydc.initSelf() ;
		Debug.debug(SerializedQuery.deserial(query, mydc).execQuery()) ;
	}
}
