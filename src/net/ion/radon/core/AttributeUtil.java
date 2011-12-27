package net.ion.radon.core;

import static net.ion.radon.core.RadonAttributeKey.IZONE_ATTRIBUTE_KEY;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.ion.framework.db.IDBController;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.core.EnumClass.Scope;
import net.ion.radon.core.config.Attribute;
import net.ion.radon.core.config.DBReleasable;
import net.ion.radon.core.config.ReferencedObject;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.section.RDBConnection;

import org.apache.commons.configuration.ConfigurationException;

public class AttributeUtil {

	static void load(IService service, XMLConfig parentConfig) throws InstanceCreationException, ConfigurationException {
		//Debug.line(context.getZone(), parentConfig);
		
		XMLConfig contextConfig = null ;
		String name = "" ;
		if (parentConfig.hasChild("context")){
			contextConfig = parentConfig.firstChild("context") ;
			name = StringUtil.toString(parentConfig.getAttributeValue("name"), "aradon");
		} else if ("context".equals(parentConfig.getTagName())){
			contextConfig = parentConfig ;
			name = service.getName() ;
		} else {
			return ;
		}
		
		
		setStringAttribute(service, contextConfig, name);
		setObjectAttribute(service, contextConfig, name) ;
		setConnectionAttribute(service, contextConfig);
	}
	
	private static void setObjectAttribute(IService service, XMLConfig contextConfig, final String name) throws InstanceCreationException {
		TreeContext context = service.getServiceContext() ;
		List<XMLConfig> configs =  contextConfig.children("configured-object") ;
		for (XMLConfig config : configs) {
//			Object created = ConfigCreator.createConfiguredInstance(objOfConfig) ;
			String id = config.getAttributeValue("id");
			Scope scope = Scope.valueOf(StringUtil.capitalize(config.getAttributeValue("scope")));
			ReferencedObject refObj = ReferencedObject.create(context, id, scope, config) ;

			Debug.debug(String.format("%1$15s[%2$s], %3$s : %4$s", context.getAttributeObject(IZONE_ATTRIBUTE_KEY, IZone.class), name, id, refObj)) ;
			context.putAttribute(id, refObj) ;
		}
	}


	private static void setStringAttribute(IService service, XMLConfig config, final String name) {
		TreeContext context = service.getServiceContext() ;
		List<XMLConfig> children = config.children("attribute") ;
		for (XMLConfig child : children) {
			final String id = child.getAttributeValue("id");
			// final String type = child.getString("[@type]"); 
			if (StringUtil.isBlank(id)) {
				Debug.warn("not found attribute id : blank id") ;
				continue ;
			}
			if (context.contains(id)) {
				Debug.warn("duplicate attribute id : " + id + " ignored") ;
				continue ;
			}
			
			
			Object attrValue = ObjectUtil.coalesce(child.getElementValue(), ObjectUtil.NULL) ;
			
			Debug.info(String.format("%1$15s[%2$s], %3$s : %4$s", context.getAttributeObject(IZONE_ATTRIBUTE_KEY, IZone.class), name, id, attrValue)) ;
			context.putAttribute(id, attrValue) ;
		}
	}

	private static void setConnectionAttribute(IService service, XMLConfig config) throws InstanceCreationException, ConfigurationException {
		TreeContext context = service.getServiceContext() ;
		List<XMLConfig> connections = config.children("connection");
		for (XMLConfig cconfig : connections) {
			String connectionId = cconfig.getAttributeValue("id");
			String message = String.format("%1$15s[connection], %2$s init", context.getAttributeObject(IZONE_ATTRIBUTE_KEY, IZone.class), connectionId) ;
			Debug.debug(message) ;
			try {
				final IDBController dc = RDBConnection.createDC(cconfig);
				dc.initSelf();
				context.putAttribute(connectionId, dc);
				if (service.getAradon() != null) service.getAradon().addReleasable(service, DBReleasable.create(dc)) ;
			} catch (SQLException ex) {
				ex.printStackTrace();
				Debug.warn(connectionId + " not initialized...");
			}
		}
	}

	public static Attribute create(Map<String, String> attrMap, String elementValue) {
		return Attribute.testCreate(attrMap, elementValue);
	}

}
