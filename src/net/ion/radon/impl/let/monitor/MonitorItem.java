package net.ion.radon.impl.let.monitor;

import java.util.ArrayList;
import java.util.List;

import net.ion.framework.util.ListUtil;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.impl.filter.RevokeServiceFilter;
import net.ion.radon.impl.section.BasePathInfo;

public class MonitorItem {
	
	private String key;
	private String type;
	private String name;
	private String description;
	private boolean start;
	private List<IRadonFilter> preFilters;
	private List<IRadonFilter> afterFilters;
	//private List<MonitorItem> child;
	
	private MonitorItem(String key, String name, String type, String desc, boolean start, List<IRadonFilter> preFilters, List<IRadonFilter> afterFilters) {
		this.key = key;
		this.name = name;
		this.type = type;
		this.description = desc;
		this.start = start;
		this.preFilters = preFilters;
		this.afterFilters = afterFilters;
	}
	
	
	public static MonitorItem create(SectionService si){
		return new MonitorItem(si.getName(), si.getName(), "section", "",  si.isStarted(), si.getPreFilters(), si.getAfterFilters());
	}
	
	public static MonitorItem create(SectionService section, BasePathInfo pinfo) {
		List<IRadonFilter> preFilters  = section.getChildService(pinfo.getName()).getPreFilters() ;
		List<IRadonFilter> afterFilters  = section.getChildService(pinfo.getName()).getAfterFilters() ;
		boolean start = preFilters.contains(RevokeServiceFilter.SELF) ;
		return new MonitorItem(section.getName() + "/" + pinfo.getName(), pinfo.getName(), "path",  pinfo.getDescription(), start, preFilters, afterFilters);
	}


	/*public static MonitorItem create(SectionInformation si){
		return new MonitorItem(si.getName(), si.getName(), "", si.isStarted(), si.getPreFilters(), si.getAfterFilters());
	}

	public List<MonitorItem> getChild() {
		return child;
	}
	
	public void setChild(List<MonitorItem> child) {
		this.child = child;
	}
	*/
	public String getType() {
		return type;
	}
	
	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isStart() {
		return start;
	}

	public List<String> getPreFilters(){
		if(preFilters ==  null)  
			return ListUtil.EMPTY;
		return toListClassName(preFilters);
	}

	public List<IRadonFilter> getAfterFilters() {
		return afterFilters;
	}

	private static<V> List<String> toListClassName(List<V> list){
		  List<String> result = new ArrayList<String>();
		  for(V obj : list) {
			  String value = (String) obj.getClass().getCanonicalName();
			  result.add(value);
		  }
		  return result;
	}
	
	/*@Override
	public String toString() {
		try {
			HashMap map = (HashMap) BeanUtils.describe(this);
			return map.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}*/
}
