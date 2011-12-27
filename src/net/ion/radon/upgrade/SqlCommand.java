package net.ion.radon.upgrade;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.procedure.IBatchQueryable;
import net.ion.framework.db.procedure.IParameterQueryable;
import net.ion.framework.db.procedure.IUserProcedures;
import net.ion.framework.db.procedure.Queryable;
import net.ion.framework.parse.html.HTag;
import net.ion.framework.util.CaseInsensitiveHashMap;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

public class SqlCommand extends ICommand {

	private static CaseInsensitiveHashMap<Integer> TYPE_MAPPING = new CaseInsensitiveHashMap<Integer>();
	static {
		initSQLType();
	}

	@Override
	public void run() throws SQLException, IOException {
		IDBController dc = getContext().getAttributeObject(IDBController.class.getCanonicalName(), IDBController.class);

		IUserProcedures upts = makeUserProcedures(dc);
		upts.execUpdate() ;
	}

	IUserProcedures makeUserProcedures(IDBController dc) throws IOException {
		HTag config = getConfig() ;
		IUserProcedures upts = dc.createUserProcedures(config.getDefaultValue("@name", "not defined"));
		List<HTag> querys = config.getChildren("query");
		for (HTag query : querys) {
			Queryable newProc = makeQuery(dc, query);
			// newProc.getProcSQL() ; // here is dragon =_=
			upts.add(newProc);
		}
		return upts ;
	}

	private static void initSQLType() {
		try {
			Field[] fields = java.sql.Types.class.getFields();
			for (Field field : fields) {
				TYPE_MAPPING.put(field.getName(), field.getInt(null));
			}
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage(), ex);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

	private int getType(String type) {
		if (!TYPE_MAPPING.containsKey(type))
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Field Type \" " + type + " \" not found");
		return TYPE_MAPPING.get(type);
	}

	private Queryable makeQuery(IDBController dc, HTag qconfig) throws IOException {
		String sql = qconfig.getValue("sql");

		if (qconfig.hasChild("parameters")) { // batch
			List<HTag> params = qconfig.getChildren("parameters");
			IBatchQueryable bquery = dc.createBatchParameterQuery(dc, sql);
			for (HTag paramSet : params) {
				for (HTag param : paramSet.getChildren("parameter")) {
					String paramId = param.getAttributeValue("id");
					int type = getType(param.getAttributeValue("type"));
					String value = param.getOnlyText();
					bquery.addBatchParameter(paramId, value, type);
				}
			}
			return bquery;
		} else {
			IParameterQueryable bquery = dc.createParameterQuery(sql);
			List<HTag> params = qconfig.getChildren("parameter");
			for (HTag param : params) {
				String paramId = param.getAttributeValue("id");
				int type = getType(param.getAttributeValue("type"));
				String value = param.getOnlyText();
				bquery.addParameter(paramId, value, type);
			}
			// Debug.line(bquery.getParams(), bquery.getTypes()) ;

			return bquery;
		}

	}

}

// <query>
// <param id="empno" type="int">5500</param>
// <param id="ename" type="String">bleujin</param>
// <param id="hiredate" type="Date">20000101-212020</param>
// <param id="sal" type="int">5000</param>
// <param id="detpNo" type="int">20</param>
// <sql><![CDATA[insert into emp_sample(empno, ename, hiredate, sal, deptno) values(:empno, :ename, :hiredate, :sal, :deptno)]]></sql>
// </query>