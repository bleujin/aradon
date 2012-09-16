package net.ion.radon.core.config;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.ion.framework.db.IDBController;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.Scope;
import net.ion.radon.impl.section.RDBConnection;

public class AsyncConfigruationBuilder<T extends AsyncConfigruationBuilder> {

	private Map<String, AttributeValue> attributes = MapUtil.newMap() ;

	public T addAttribute(String id, Object value){
		return addAttribute(id, XMLConfig.BLANK, value);
	}
	
	public T addAttribute(String id, XMLConfig config, Object value){
		attributes.put(id, new ApplicationAttributeValue(config, value)) ;
		return (T)this ;
	}
	
	public T addAttribute(String id, XMLConfig config, Scope scope) throws InstanceCreationException{
		if (StringUtil.isBlank(id)) {
			Debug.warn("not found attribute id : blank id") ;
			return (T)this ;
		}
		if (attributes.containsKey(id)){
			Debug.warn("duplicate attribute id : " + id + " ignored") ;
			return (T)this ;
		}
		
		
		if (scope == Scope.Application){
			Object applicationObject = ConfigCreator.createConfiguredInstance(config);
			addAttribute(id, applicationObject) ;
		} else { // request
			attributes.put(id, new RequestAttributeValue(config)) ;
		}
		
		return (T)this ;
	}
	
	public Map<String, AttributeValue> getAttributes(){
		return attributes ;
	}
	
	protected void parseContextAttribute(XMLConfig config) throws InstanceCreationException {
		List<XMLConfig> objectConfig =  config.children("configured-object") ;
		for (XMLConfig xchild : objectConfig){
			String id = xchild.getAttributeValue("id");
			Scope scope = Scope.valueOf(StringUtil.capitalize(xchild.getAttributeValue("scope")));
			addAttribute(id, xchild, scope) ;
		}
		List<XMLConfig> attributesConfig = config.children("attribute") ;
		for (XMLConfig child : attributesConfig) {
			final String id = child.getAttributeValue("id");
			Object attrValue = ObjectUtil.coalesce(child.getElementValue(), ObjectUtil.NULL) ;
			addAttribute(id, child, attrValue) ;
		}
		List<XMLConfig> connectionConfig = config.children("connection");
		for (XMLConfig child : connectionConfig) {
			String id = child.getAttributeValue("id");
			try {
				final IDBController dc = RDBConnection.createDC(child);
				dc.initSelf();
				addAttribute(id, child, dc) ;
			} catch (SQLException ex) {
				throw new IllegalStateException(ex) ;
			}
		}
	}
}
