package net.ion.radon.impl.let.monitor;

import java.util.List;

import net.ion.framework.util.ListUtil;
import net.ion.radon.core.IService;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.let.AbstractLet;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class MonitorLet extends AbstractLet {
	
	private String SECTION = "section";
	private String PATH = "path";
	
	@Override
	protected Representation myPut(Representation entity) throws Exception {
		IService service = findService() ;
		service.restart() ;
		
		return new StringRepresentation("restart : " + service.getName() );
	}
	
	private SectionService findSection(){
		return getMySectionService().getOtherSection(getInnerRequest().getAttribute(SECTION));
	}
	
	@Override
	protected Representation myDelete() throws Exception {
		IService service = findService() ;
		service.suspend() ;
		
		return new StringRepresentation("suspend : " + service.getName() );
	}

	private IService findService() {
		return getInnerRequest().hasAttribute(PATH) ? findSection() : findSection().getChildService(getInnerRequest().getAttribute(PATH));
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		return myPut(entity);
	}
	
	@Override
	protected Representation myGet() throws Exception {
		return BeanListHandler.toRepresentation(newMapListFormatHandler(), getContext(), getAradon(),"key");
	}

	private List<MonitorItem>  getAradon(){
		return  getInnerRequest().hasAttribute(SECTION) ? getTarget() : getSections();
	}
	
	private List<MonitorItem> getSections(){
		List<MonitorItem> list = ListUtil.newList();
		
		for(SectionService section : getMySectionService().getAradon().getChildren()){
			list.addAll(getTargetSection(section));
		}
		return list;
	}
	
	private List<MonitorItem> getTarget(){
		SectionService section = findSection();
		if(getInnerRequest().hasAttribute(PATH)){
//			LetInformation let = section.getInformation().getPathInformation(getInnerRequest().getAttribute(PATH));
			return ListUtil.create(MonitorItem.create(section, section.getChildService(getInnerRequest().getAttribute(PATH)).getPathInfo() ));
		}
		return getTargetSection(section);
	}

	private List<MonitorItem> getTargetSection(SectionService section) {
		List<MonitorItem> list = ListUtil.newList();
		list.add(MonitorItem.create(section));
		list.addAll(getLetList(section));
		return list;
	}

	private List<MonitorItem> getLetList(SectionService section) {
		List<MonitorItem> list = ListUtil.newList();
		for(PathService ps : section.getChildren()){
			list.add( MonitorItem.create(section, ps.getPathInfo()));
		}
		return list;
	}
	
	//===================================================
	
	/*private Map<String, Object> getAradonMap(){
		return  hasRequestAttribute(SECTION) ? getTarget() : getSections();
	}
	
	private Map<String, Object> getTarget(){
		SectionService section = getSection();
		return hasRequestAttribute(PATH) ? getTargetPath(section) : getTargetSection(section); 
	}
	
	private Map getTargetSection(SectionService section) {
		return MapUtil.create(getRequestAttribute(SECTION), sectionToMap(section.getInformation()));
	}

	private Map getTargetPath(SectionService section){
		return pathToMap(section.getInformation().getPathInformation(getRequestAttribute(PATH)));
	}
	
	private Map<String, Object> getSections(){
		Map<String, Object> smap = MapUtil.newLinkedHashMap();
		
		for(SectionService section : getMySectionService().getAradonSections().values())
			smap.put(section.getName(), sectionToMap(section.getInformation()));
		
		return smap;
	}
	
	private Map<String, Object> sectionToMap(SectionInformation section){
		Map<String, Object> result = MapUtil.newLinkedHashMap();
		
		result.put("_information", section.toSimpleMap());
		result.put(RadonAttributeKey.PRE_FILTER, getFiltersNames(section.getPreFilters()));
		result.put(RadonAttributeKey.AFTER_FILTER, getFiltersNames(section.getAfterFilters()));
		
		for(LetInformation pinfo : section.getLetList()){
			result.put(pinfo.getName(), pathToMap(pinfo));
		}
		return result;
	}
	
	private Map<String, Object> pathToMap(LetInformation pinfo) {
		Map<String, Object> pathMap = MapUtil.newLinkedHashMap();

		pathMap.put("_information", pinfo.toSimpleMap());
		pathMap.put(RadonAttributeKey.PRE_FILTER, getFiltersNames(pinfo.getPreFilters()));
		pathMap.put(RadonAttributeKey.AFTER_FILTER, getFiltersNames(pinfo.getAfterFilters()));

		return pathMap;
	}

	private List<String> getFiltersNames(List<IRadonFilter> filters){
		if(filters ==  null)  
			return ListUtil.EMPTY; 
		return ListUtil.toListClassName(filters);
	} */
	
}
