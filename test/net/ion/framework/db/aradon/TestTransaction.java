package net.ion.framework.db.aradon;

import java.sql.SQLException;

import groovy.util.ResourceException;
import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.db.procedure.IUserProcedure;
import net.ion.framework.db.procedure.IUserProcedures;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.rdb.HandlerLet;
import net.ion.radon.impl.let.rdb.QueryLet;
import net.ion.radon.impl.let.rdb.UpdateLet;
import net.ion.radon.impl.section.PathInfo;

public class TestTransaction extends TestAbstractRemoteQuery{
	

	public void testNormal() throws Exception {
		IUserProcedures upts = remoteDc.createUserProcedures("dept");
		IUserProcedure upt1 = remoteDc.createUserProcedure("dept@updateWith(?,?,?)");
		upts.add(remoteDc.createUserProcedure("dept@updateWith(?,?,?)").addParam(10).addParam("11").addParam("22"));
		upts.add(remoteDc.createUserProcedure("dept@updateWith(?,?,?)").addParam(20).addParam("11").addParam("22"));
		
		int result = upts.execUpdate();
		assertEquals(2, result);
	}
	
	public void testTran() throws Exception {
		
		IUserProcedures upts = remoteDc.createUserProcedures("tran") ;
		
		upts.add(remoteDc.createParameterQuery("dept@updateWith(?,?,?)").addParam(10).addParam("my depth").addParam("my loc")) ;
		upts.add(remoteDc.createParameterQuery("dept@updateWith(?,?,?)").addParam(10).addParam("01234567890123456789").addParam("5 loc")) ;
		
		try {
			upts.execUpdate() ;
			fail() ;
		} catch(RuntimeException ignore){
			ignore.printStackTrace() ;
		}
		
	}
	
	
	
	
}
