package net.ion.radon.impl.section;

import java.util.List;

import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.servant.ChannelServant;
import net.ion.framework.db.servant.IExtraServant;
import net.ion.framework.util.InstanceCreationException;
import net.ion.radon.core.config.ConfigCreator;
import net.ion.radon.core.config.XMLConfig;

public class RDBConnection {

	public static IDBController createDC(final XMLConfig xmlConfig) throws InstanceCreationException{

		XMLConfig dbConfig = xmlConfig.firstChild("database-controller");

		String name = dbConfig.getString("controller-name");
		DBManager dbm = (DBManager) ConfigCreator.createConfiguredInstance(dbConfig.firstChild("database-manager.configured-object"));

		List<XMLConfig> cconfig = dbConfig.children("extra-servant.configured-object");

		ChannelServant schain = new ChannelServant();
		for (XMLConfig sconfig : cconfig) {
			IExtraServant newServant = (IExtraServant) ConfigCreator.createConfiguredInstance(sconfig);
			schain.add(newServant) ;
		}
		DBController dc = new DBController(name, dbm, schain);
		return dc;
	}
}
