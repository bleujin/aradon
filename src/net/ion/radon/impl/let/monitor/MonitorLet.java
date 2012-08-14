package net.ion.radon.impl.let.monitor;

import java.util.List;

import net.ion.framework.util.ListUtil;
import net.ion.radon.core.IService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.let.AbstractLet;
import net.ion.radon.core.let.PathService;

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
		return (SectionService) getApplication().getApplication() ;
	}
	
	@Override
	protected Representation myDelete() throws Exception {
		IService service = findService() ;
		service.suspend() ;
		
		return new StringRepresentation("suspend : " + service.getName() );
	}

	private IService findService() {
		return getInnerRequest().hasAttribute(PATH) ? (IService)getApplication().getApplication() : (IService)getApplication();
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
		
		for(SectionService section : getSectionService().getAradon().getChildren()){
			list.addAll(getTargetSection(section));
		}
		return list;
	}
	
	private List<MonitorItem> getTarget(){
		SectionService section = findSection();
		if(getInnerRequest().hasAttribute(PATH)){
			return ListUtil.toList(MonitorItem.create(section, section.getChildService(getInnerRequest().getAttribute(PATH)).getConfig()));
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
			list.add( MonitorItem.create(section, ps.getConfig()));
		}
		return list;
	}
}
