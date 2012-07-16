package net.ion.radon.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.db.procedure.IUserProcedures;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Ignore;
import org.junit.Test;
import org.restlet.data.Method;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public class TestSerialRequest {

	@Test
	public void basicGet() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test?name=bleujin") ;
		
		MyUser muser = request.get(MyUser.class) ;
		assertEquals("bleujin", muser.getName()) ;
	}

	@Test
	public void opps() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class);
		at.startServer(9000);

		IAradonRequest pr = AradonClientFactory.create("http://127.0.0.1:9000").createRequest("/test");
		pr.addParameter("name", "hero");

		ObjectInputStream ois = new ObjectInputStream(pr.get().getStream());
		MyUser m = (MyUser) ois.readObject();
		assertEquals("hero", m.getName());
		at.getAradon().stop() ;
	}
	
	@Ignore
	@Test
	public void xtestPostSelect() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;
		
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test") ;
		Rows rows = request.post(new String[]{"select 1 from dual", "select 2 from dual"}, Rows.class) ;
		
		assertEquals(1, rows.firstRow().getInt(1)) ;
		assertEquals(2, rows.getNextRows().firstRow().getInt(1)) ;
		at.getAradon().stop() ;
	}

	@Test
	public void basicPut() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;
		
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test?name=bleujin") ;
		List<String> names = request.put("bleujin,hero", ArrayList.class) ;
		
		assertEquals(3, names.size()) ;
		assertEquals("bleujin", names.get(0)) ;
		at.getAradon().stop() ;
	}
	
	@Test
	public void aradonGet() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;

		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test?name=bleujin") ;
		MyUser muser = request.get(MyUser.class) ;
		assertEquals("bleujin", muser.getName()) ;
	}
	@Test
	public void aradonPut() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;

		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test") ;
		List<String> names = request.put("bleujin,hero", ArrayList.class) ;
		
		assertEquals(2, names.size()) ;
		assertEquals("bleujin", names.get(0)) ;
	}

	
	public void aradonPost() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;
		
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test") ;
		Rows rows = request.post(new String[]{"select 1 from dual", "select 2 from dual"}, Rows.class) ;
		
		assertEquals(1, rows.firstRow().getInt(1)) ;
		assertEquals(2, rows.getNextRows().firstRow().getInt(1)) ;
	}

	@Test
	public void basicHeader() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test2", HeaderTestLet.class) ;
		
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test2") ;
		request.addHeader("name", "bleujin") ;
		
		
		Integer ivalue = request.put(null, Integer.class) ;
		assertEquals("bleujin".length(), ivalue.intValue()) ;
		at.getAradon().stop() ;
	}

	@Test
	public void aradonHeader() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", HeaderTestLet.class) ;
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test") ;
		request.addHeader("name", "bleujin") ;
		
		
		Integer ivalue = request.put(null, Integer.class) ;
		assertEquals("bleujin".length(), ivalue.intValue()) ;
	}
	
	
	@Test
	public void entitySerialObject() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", SerialLet.class) ;
		Aradon aradon = at.getAradon();
		aradon.startServer(9150) ;
		
		ISerialRequest request = AradonClientFactory.create("http://127.0.0.1:9150").createSerialRequest("/test") ;
		request.addHeader("name", "jin") ;
		
		MyUser user = request.handle(Method.POST, new MyUser("bleu"), MyUser.class) ;
		assertEquals(21, user.getAge()) ;
		assertEquals("bleujin", user.getName()) ;
		
		aradon.stop() ;
	}
	
	
	
	
	
	
	
	
	
	
}


class SerialLet extends AbstractServerResource {
	
	@Post
	public MyUser getUser(MyUser user){
		MyUser result = new MyUser(user.getName() + getInnerRequest().getHeader("name")) ;
		return result.oneYearLater() ;
	}
}


class ParameterTestLet extends AbstractServerResource {

	@Get
	public MyUser getUser() {
		return new MyUser(getInnerRequest().getParameter("name"));
	}

	@Put
	public ArrayList<String> myPut(String names) throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(ListUtil.toList(StringUtil.split(names, ",")));
		String pname = getInnerRequest().getParameter("name");
		if (StringUtil.isNotBlank(pname))
			result.add(pname);
		return result;
	}

	@Post
	public Rows select(String[] sqls) throws IOException, SQLException {

		DBManager dbm = new OracleDBManager("jdbc:oracle:thin:@dev-sql.i-on.net:1521:devSql", "bleu", "redf");
		IDBController dc = new DBController(dbm);
		dc.initSelf();

		IUserProcedures upts = dc.createUserProcedures("all");
		for (String sql : sqls) {
			upts.add(dc.createUserCommand(sql));
		}

		Rows rows = dc.getRows(upts);

		dc.destroySelf();
		return rows;
	}
}

class HeaderTestLet extends AbstractServerResource{
	
	@Put
	public int testPut() throws Exception {
		String nameHeader = getInnerRequest().getHeader("name");
		return StringUtil.length(nameHeader) ;
	}
}

