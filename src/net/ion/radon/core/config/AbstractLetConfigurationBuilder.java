package net.ion.radon.core.config;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.ion.framework.db.IDBController;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.Scope;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.impl.section.RDBConnection;

public abstract class AbstractLetConfigurationBuilder<V extends AbstractLetConfigurationBuilder, T> extends AbstractConfigurationBuilder<T>{

	private List<IRadonFilter> prefilters = ListUtil.newList() ;
	private List<IRadonFilter> afterfilters = ListUtil.newList() ;
	private Map<String, AttributeValue> attributes = MapUtil.newCaseInsensitiveMap() ;
	
	protected AbstractLetConfigurationBuilder(IConfigurationChildBuilder builder){
		super(builder) ;
	}
	
	public V addPreFilter(IRadonFilter filter) {
		prefilters.add(filter) ;
		return (V) this ;
	}
	
	public V addAfterFilter(IRadonFilter filter){
		afterfilters.add(filter) ;
		return (V) this ;
	}

	public V addAttribute(String id, Object value){
		return addAttribute(id, XMLConfig.BLANK, value);
	}

	public V addAttribute(String id, XMLConfig config, Object value){
		attributes.put(id, new ApplicationAttributeValue(config, value)) ;
		return (V) this ;
	}
	

	protected V initContextFilter(XMLConfig config) throws InstanceCreationException{
		for(IRadonFilter filter : FilterUtil.getFilters(config.children("prefilter"))){
			addPreFilter(filter) ;
		}
		for(IRadonFilter filter : FilterUtil.getFilters(config.children("afterfilter"))){
			addAfterFilter(filter) ;
		}
		
		parseContextAttribute(config.firstChild("context")) ;
		return (V) this ;
	}
	


	public V addAttribute(String id, XMLConfig config, Scope scope) throws InstanceCreationException{
		if (StringUtil.isBlank(id)) {
			Debug.warn("not found attribute id : blank id") ;
			return (V)this ;
		}
		if (attributes.containsKey(id)){
			Debug.warn("duplicate attribute id : " + id + " ignored") ;
			return (V)this ;
		}
		
		
		if (scope == Scope.Application){
			Object applicationObject = ConfigCreator.createConfiguredInstance(config);
			addAttribute(id, applicationObject) ;
		} else { // request
			attributes.put(id, new RequestAttributeValue(config)) ;
		}
		
		return (V)this ;
	}

	public List<IRadonFilter> getPreFilters(){
		return prefilters ;
	}
	
	public List<IRadonFilter> getAfterFilters(){
		return afterfilters ;
	}
	
	public Map<String, AttributeValue> getAttributes(){
		return attributes ;
	}

	private void parseContextAttribute(XMLConfig config) throws InstanceCreationException {
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
				ex.printStackTrace();
				Debug.warn(id + " not initialized...");
			}
		}
	}

}



