package net.ion.framework.db;

import net.ion.framework.db.procedure.AradonCombinedUserProcedures;
import net.ion.framework.db.procedure.AradonUserCommand;
import net.ion.framework.db.procedure.AradonUserCommandBatch;
import net.ion.framework.db.procedure.AradonUserProcedure;
import net.ion.framework.db.procedure.AradonUserProcedureBatch;
import net.ion.framework.db.procedure.AradonUserProcedures;
import net.ion.framework.db.procedure.ICombinedUserProcedures;
import net.ion.framework.db.procedure.IUserCommand;
import net.ion.framework.db.procedure.IUserCommandBatch;
import net.ion.framework.db.procedure.IUserProcedure;
import net.ion.framework.db.procedure.IUserProcedureBatch;
import net.ion.framework.db.procedure.IUserProcedures;
import net.ion.framework.db.procedure.RepositoryService;

public class AradonRepositoryService extends RepositoryService {

	@Override
	public IUserCommand createUserCommand(IDBController dc, String sql) {
		return AradonUserCommand.create(dc, sql);
	}

	@Override
	public IUserCommandBatch createUserCommandBatch(IDBController dc, String sql) {
		return AradonUserCommandBatch.create(dc, sql);
	}

	@Override
	public IUserProcedure createUserProcedure(IDBController dc, String proc) {
		return AradonUserProcedure.create(dc, proc);
	}

	@Override
	public IUserProcedureBatch createUserProcedureBatch(IDBController dc, String proc) {
		return AradonUserProcedureBatch.create(dc, proc);
	}

	@Override
	public IUserProcedures createUserProcedures(IDBController dc, String proc) {
		return AradonUserProcedures.create(dc, proc);
	}
	
	@Override
	public ICombinedUserProcedures createCombinedUserProcedures(IDBController dc, String name) {
		return AradonCombinedUserProcedures.create(dc, name);
	}
}
