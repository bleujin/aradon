package net.ion.radon.impl.section;

import java.util.List;

import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.servant.ExtraServant;
import net.ion.framework.db.servant.NoneServant;
import net.ion.framework.db.servant.ServantChannel;
import net.ion.framework.util.InstanceCreationException;
import net.ion.radon.core.config.ConfigCreator;
import net.ion.radon.core.config.XMLConfig;

import org.apache.commons.configuration.ConfigurationException;

public class RDBConnection {

	public static IDBController createDC(final XMLConfig xmlConfig) throws InstanceCreationException, ConfigurationException {
		
		XMLConfig dbConfig = xmlConfig.firstChild("database-controller") ;
		
		String name = dbConfig.getString("controller-name") ;
		DBManager dbm = (DBManager) ConfigCreator.createConfiguredInstance(dbConfig.firstChild("database-manager.configured-object")) ;
		
		List<XMLConfig> cconfig = dbConfig.children("extra-servant.configured-object");
		

		ServantChannel echannel = null; 
		ExtraServant eservant = null;
		// None Channel...
		if (cconfig.size() == 0) {
			echannel = new ServantChannel(new NoneServant());
		} else if (cconfig.size() == 1){
			eservant = (ExtraServant) ConfigCreator.createConfiguredInstance(cconfig.get(0));
			echannel = new ServantChannel(eservant);
			eservant.setChannel(echannel);
		} else {
			for (XMLConfig sconfig : cconfig) {
				ExtraServant nextServant = (ExtraServant) ConfigCreator.createConfiguredInstance(sconfig);
				if (eservant != null) eservant.setNext(nextServant);
				eservant = nextServant;
			}
		}
		DBController dc = new DBController(name, dbm) ;
		dc.setServantChannel(echannel) ;
		

		return new DBController(dbm);
	}
}
