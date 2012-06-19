package net.ion.framework.db.aradon;

import junit.framework.TestCase;
import net.ion.framework.db.AradonDBManager;
import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.IONXmlWriter;
import net.ion.framework.db.Page;
import net.ion.framework.db.Rows;
import net.ion.framework.db.bean.ResultSetHandler;
import net.ion.framework.db.bean.handlers.ScalarHandler;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.db.procedure.ICombinedUserProcedures;
import net.ion.framework.db.procedure.IUserCommand;
import net.ion.framework.db.procedure.IUserProcedure;
import net.ion.framework.db.procedure.IUserProcedureBatch;
import net.ion.framework.db.procedure.IUserProcedures;
import net.ion.framework.util.InfinityThread;
import net.ion.radon.impl.let.rdb.HandlerLet;
import net.ion.radon.impl.let.rdb.QueryLet;
import net.ion.radon.impl.let.rdb.UpdateLet;
import net.ion.radon.util.AradonTester;

public class TestAradonDBController extends TestCase{
	
	private IDBController remoteDc;
	@Override public void setUp() throws Exception {
		this.remoteDc = new DBController(AradonDBManager.create("http://127.0.0.1:9000")) ;
		remoteDc.initSelf() ;
	}

	@Override
	protected void tearDown() throws Exception {
		this.remoteDc.destroySelf();
		super.tearDown();
	}

	public void xtestRun() throws Exception {
		DBManager dbm = new OracleDBManager("jdbc:oracle:thin:@DEV-SQL.I-ON.NET:1521:DEVSQL", "bleu", "redf") ;
		IDBController localDc = new DBController(dbm) ;
		localDc.initSelf() ;
		
		AradonTester at = AradonTester.create().register("rdb", "/query", QueryLet.class) ;
		at.register("rdb", "/update", UpdateLet.class) ;
		at.register("rdb", "/handler", HandlerLet.class) ;
		at.getAradon().getServiceContext().putAttribute(IDBController.class.getCanonicalName(), localDc) ;
		at.getAradon().startServer(9000) ;
		new InfinityThread().startNJoin() ;
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

	
	public void testProcedure() throws Exception {
		Rows rows =  remoteDc.createUserProcedure("dept@infoBy(?)").addParam(10).execQuery();
		rows.setXmlWriter(new IONXmlWriter()) ;
		assertEquals(1, rows.getRowCount());
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
	
	
	public void testHadnlerQuery() throws Exception {
		ResultSetHandler handler = new ScalarHandler(1);
		Object result = remoteDc.createUserCommand("select deptno, dname from dept_sample where deptno = 10").execHandlerQuery(handler) ;
		assertEquals("10", result.toString()) ;
	}

	public void testPaging() throws Exception {
		IUserCommand cmd = remoteDc.createUserCommand("select * from emp_sample");
		cmd.setPage(Page.TEN) ;
		
		Rows rows = cmd.execPageQuery() ;
		assertEquals(10, rows.getRowCount()) ;
	}
	
	public void xtestCombinedUserProcedures() throws Exception {
		ICombinedUserProcedures cupts = remoteDc.createCombinedUserProcedures("combine");
		cupts.add(remoteDc.createUserProcedure("dept@updateWith(?,?,?)").addParam(10).addParam("11").addParam("22"));
		cupts.add(remoteDc.createUserCommand("select * from dept_sample where deptno=10"), "select", ICombinedUserProcedures.QUERY_COMMAND);
		int result = cupts.execUpdate();
		Rows rows = (Rows) cupts.getResultMap().get("select");
		
		assertEquals(1, rows.getRowCount());
		assertEquals("11", rows.firstRow().getString("dname"));
	}
	
	
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
