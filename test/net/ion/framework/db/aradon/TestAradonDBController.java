package net.ion.framework.db.aradon;

import java.util.List;
import java.util.Map;

import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Page;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.handlers.MapListHandler;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.db.procedure.ICombinedUserProcedures;
import net.ion.framework.db.procedure.IUserCommand;
import net.ion.framework.db.procedure.IUserProcedure;
import net.ion.framework.db.procedure.IUserProcedureBatch;
import net.ion.framework.db.procedure.IUserProcedures;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.rdb.HandlerLet;
import net.ion.radon.impl.let.rdb.QueryLet;
import net.ion.radon.impl.let.rdb.UpdateLet;
import net.ion.radon.impl.section.PathInfo;

public class TestAradonDBController extends TestAbstractRemoteQuery{

	

	@Override
	protected void tearDown() throws Exception {
		this.remoteDc.destroySelf();
		this.localDc.destroySelf();
		super.tearDown();
	}
	
	
	public void testCreate() throws Exception {
		assertEquals(true, remoteDc.createUserCommand("select 1 from dual")  != null) ;
		assertEquals(true, remoteDc.createUserProcedure("base@hello")  != null) ;
		assertEquals(true, remoteDc.createUserProcedureBatch("base@hello")  != null) ;
		assertEquals(true, remoteDc.createUserCommandBatch("select 1 from dual")  != null) ;
	}
	
	
	public void testQuery() throws Exception {
		Rows rows = remoteDc.createUserCommand("select * from dept_sample where deptno = 10").execQuery() ;
		assertEquals(1, rows.getRowCount()) ;
	}

	
	public void testLocalQuery() throws Exception {
		Rows rows = localDc.createUserCommand("select * from dept_sample where deptno = 10").execQuery() ;
		
		List<Map> results = (List<Map>) rows.toHandle(new MapListHandler()) ;
		Debug.debug(results) ;

		List<Map> remoteResult = (List<Map>) localDc.createUserCommand("select * from dept_sample where deptno = 10").execHandlerQuery(new MapListHandler()) ;
		Debug.debug(remoteResult) ;

		
		
	}
	
	public void testUpdate() throws Exception {
		int result = remoteDc.createUserCommand("update dept_sample set dname = 'test', loc = 'test' where deptno = 10").execUpdate();
		assertEquals(1, result);
	}

	
	public void testUpdateProcedure() throws Exception {
		IUserProcedure upt = remoteDc.createUserProcedure("dept@updateWith(?,?,?)");
		upt.addParam(0, 10);
		upt.addParam(1, "heeya");
		upt.addParam(2, "heeya");
		int result = upt.execUpdate();
		assertEquals(1, result);
	}

	public void testNamedUpdateProcedure() throws Exception {
		IUserProcedure upt = remoteDc.createUserProcedure("dept@updateWith(:id, :name, :exp)");
		upt.addParam("id", 10);
		upt.addParam("name", "heeya");
		upt.addParam("exp", "heeya");
		// upt.toString() ;
		int result = upt.execUpdate();
		assertEquals(1, result);
	}

	
	//-------------------------------------
	public void testRemoteProcedureBatch() throws Exception {
		remoteDc.createUserCommand("delete from dept_sample where deptno < 10").execUpdate() ;
		
		
		IUserProcedureBatch upt = remoteDc.createUserProcedureBatch("dept@createWith(?,?,?)");
		upt.addParam(0, new int[] {5, 6} );
		upt.addParam (1, new String[] {"5dept", "6dept"});
		upt.addParam(2, new String[] {"5loc", "6loc"});
		assertEquals(2, upt.execUpdate());
	}
	
	
	public void testUserProcedures() throws Exception {
		IUserProcedures upts = remoteDc.createUserProcedures("dept");
		
		IUserProcedure upt1 = remoteDc.createUserProcedure("dept@updateWith(?,?,?)");
		upt1.addParam(0, 10);
		upt1.addParam(1, "11");
		upt1.addParam(2, "22");
		upts.add(upt1);
		
		IUserProcedure upt2 = remoteDc.createUserProcedure("dept@updateWith(?,?,?)");
		upt2.addParam(0, 20);
		upt2.addParam(1, "11");
		upt2.addParam(2, "22");
		upts.add(upt2);
		
		int result = upts.execUpdate();
		assertEquals(2, result);
	}

	public void testUserProcedures2() throws Exception {
		IUserProcedures upts1 = remoteDc.createUserProcedures("dept1");
		upts1.add(remoteDc.createUserProcedure("dept@updateWith(?,?,?)").addParam(10).addParam("11").addParam("22"));

		IUserProcedures upts2 = remoteDc.createUserProcedures("dept2");
		upts2.add(remoteDc.createUserProcedure("dept@updateWith(?,?,?)").addParam(10).addParam("22").addParam("33"));
		
		
		IUserProcedures upts = remoteDc.createUserProcedures("all") ;
		int result = upts.add(upts1).add(upts2).execUpdate() ;

		assertEquals(2, result);
	}
	
	public void testUserProcedures3() throws Exception {
		IUserProcedures upts = remoteDc.createUserProcedures("dept1");
		upts.add(remoteDc.createUserCommand("select * from dept_sample where deptno = 10"));
		upts.add(remoteDc.createUserCommand("select * from dept_sample where deptno = 20"));
		Rows rows = upts.execQuery();
		assertEquals(10, rows.firstRow().getInt("deptno"));
		assertEquals(20, rows.getNextRows().firstRow().getInt("deptno"));
	}
	
	public void testCombinedUserProcedures() throws Exception {
		ICombinedUserProcedures cupts = remoteDc.createCombinedUserProcedures("combine");
		cupts.add(remoteDc.createUserProcedure("dept@updateWith(?,?,?)").addParam(10).addParam("11").addParam("22"));
		cupts.add(remoteDc.createUserCommand("select * from dept_sample where deptno=10"), "select", ICombinedUserProcedures.QUERY_COMMAND);
		int result = cupts.execUpdate();
		Rows rows = (Rows) cupts.getResultMap().get("select");
		
		assertEquals(1, rows.getRowCount());
		assertEquals("11", rows.firstRow().getString("dname"));
	}
	
	public void testPaging() throws Exception {
		IUserCommand cmd = remoteDc.createUserCommand("select * from emp_sample");
		cmd.setPage(Page.TEN) ;
		
		Rows rows = cmd.execPageQuery() ;
		assertEquals(10, rows.getRowCount()) ;
		assertEquals(16, rows.getScreenInfo().getRowCount()) ;
	}
	
	
	
}
