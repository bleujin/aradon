package net.ion.framework.db;

import java.sql.SQLException;

import net.ion.framework.db.fake.BlankDBManager;
import net.ion.framework.db.procedure.IUserProcedure;
import net.ion.framework.db.procedure.Queryable;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Post;

public class MyQueryLet extends AbstractServerResource {
	
	
	@Post
	public Queryable myQuery() throws SQLException {
		DBController dc = new DBController(new BlankDBManager()) ;
		dc.initSelf() ;
		
		IUserProcedure upt = dc.createUserProcedure("select 1 from dual") ;
		// upt.setPage(Page.TEN) ;
		return upt ;
	}

}
