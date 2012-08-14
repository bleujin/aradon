package net.ion.radon.util;

import net.ion.framework.util.InstanceCreationException;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.EnumClass.ILocation;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.core.filter.IRadonFilter;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.resource.ServerResource;

public class FakeSection {

	private String sectionName ;
	private AradonTester at ;
	
	private FakeSection(String sectionName, AradonTester at) {
		this.sectionName = sectionName ;
		this.at = at ;
	}

	static FakeSection load(String sectionName, AradonTester at) {
		return new FakeSection(sectionName, at);
	}

	public FakeSection addLet(String urls, String letName, Class<? extends ServerResource> handlerClz) throws Exception {
		at.register(sectionName, urls, letName, IMatchMode.EQUALS, handlerClz) ;
		return this ;
	}

	public FakeSection addLet(String urls, String letName, IMatchMode mmode, Class<? extends ServerResource> handlerClz) throws Exception {
		at.register(sectionName, urls, letName, mmode, handlerClz) ;
		return this ;
	}

	public AradonTester getAradonTester(){
		return at ;
	}
	
	public Aradon getAradon() {
		return at.getAradon() ;
	}

	public FakeSection addFilter(ILocation loc, IRadonFilter filter) throws ConfigurationException, InstanceCreationException {
		SectionService ss = mergeSection();
		
		if (loc == ILocation.PRE) ss.getConfig().addPreFilter(filter) ;
		else if (loc == ILocation.AFTER) ss.getConfig().addAfterFilter(filter) ;
		
		return this;
	}

	private SectionService mergeSection() throws ConfigurationException, InstanceCreationException {
		SectionService ss ;
		try {
			ss = getAradon().getChildService(sectionName) ;
		} catch(IllegalArgumentException notfoud){
			ss = getAradon().attach(SectionConfiguration.createBlank(sectionName)) ;
		}
		return ss;
	}

	public FakeSection putAttribute(String key, Object value) throws ConfigurationException, InstanceCreationException {
		mergeSection().getServiceContext().putAttribute(key, value) ;
		return this;
	}

}
