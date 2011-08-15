package net.ion.framework.db.aradon;

import net.ion.framework.db.Rows;
import net.ion.framework.db.procedure.IUserProcedure;
import net.ion.framework.util.Debug;

public class TestRemoteQuery extends TestAbstractRemoteQuery{

	
	public void testSerilized() throws Exception {
		Rows rows = localDc.createUserProcedure("dept@infoBy(?)").addParam(10).execQuery() ;
		Rows another = rows.toClone();
		Debug.debug(another) ;
		
	}
	
	
	public void testProcedure() throws Exception {
		Rows rows =  remoteDc.createUserProcedure("dept@infoBy(?)").addParam(10).execQuery();
		assertEquals(1, rows.getRowCount());
	}
}
