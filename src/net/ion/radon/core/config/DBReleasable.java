package net.ion.radon.core.config;

import java.sql.SQLException;

import net.ion.framework.db.IDBController;

public class DBReleasable implements Releasable {

	private IDBController dc;

	public DBReleasable(IDBController dc) {
		this.dc = dc;
	}

	public final static DBReleasable create(IDBController dc) {
		return new DBReleasable(dc);
	}

	public void doRelease() {
		try {
			dc.destroySelf();
		} catch (SQLException ignore) {
			ignore.printStackTrace();
		}
	}
}
