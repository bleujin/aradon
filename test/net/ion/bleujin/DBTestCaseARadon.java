package net.ion.bleujin;

import junit.framework.TestCase;
import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.db.servant.StdOutServant;

public class DBTestCaseARadon extends TestCase {

	protected static IDBController dc;

	public void setUp() throws Exception {
		if (dc == null) {
			DBManager dbm = new OracleDBManager("jdbc:oracle:thin:@dev-test.i-on.net:1521:devTest", "bleu", "redf");
			dc = new DBController("TestCase", dbm, new StdOutServant());
			dc.initSelf();
		}
	}
}
