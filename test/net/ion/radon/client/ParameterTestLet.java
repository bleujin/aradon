package net.ion.radon.client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.db.procedure.IUserProcedures;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public class ParameterTestLet extends AbstractServerResource {

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
