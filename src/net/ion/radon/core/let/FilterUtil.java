package net.ion.radon.core.let;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.AttributeUtil;
import net.ion.radon.core.EnumClass.FilterLocation;
import net.ion.radon.core.IService;
import net.ion.radon.core.PathService;
import net.ion.radon.core.config.ConfigCreator;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.script.ScriptFactory;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

public class FilterUtil {

	//
	final static IFilterResult handlePreFilter(PathService iservice, Request request, Response response) {
		List<FilterEntry> allPreFilters = ListUtil.newList() ;
		IService current = iservice ;
		while(current != IService.ROOT) {
			List<IRadonFilter> prefilters = current.getPreFilters() ;
			allPreFilters.addAll(0, FilterEntry.toList(prefilters, current) ) ;
			
			current = current.getParent() ;
		}
		
		IFilterResult result=null;
		for (FilterEntry entry : allPreFilters) {
			IFilterResult fresult = entry.preHandle(request, response);
			if (fresult == null) continue ;
			if (fresult.getResult() == Filter.STOP) {
				return fresult ;
			} else if (fresult.getResult() == Filter.SKIP){   //add representation
				result = fresult;
			}
		}
		
		if(result != null)
			return  result; 
		return IFilterResult.CONTINUE_RESULT ;
	}

	final static void handleAfterFilter(PathService pservice, Request request, Response response) {
		List<FilterEntry> allAfterFilters = ListUtil.newList() ;
		IService current = pservice ;
		while(current != IService.ROOT) {
			List<IRadonFilter> afterfilters = current.getAfterFilters() ;
			allAfterFilters.addAll(FilterEntry.toList(afterfilters, current)) ;
			current = current.getParent() ;
		}
		
		for (FilterEntry entry : allAfterFilters) {
			IFilterResult fresult = entry.afterHandle(request, response);
			if (fresult == null) continue ;
			if (fresult.getResult() == Filter.STOP) {
				break ;
			}
		}
	}

	static final String ATTRIBUTE = "attribute";
	private final static void makeFilter(IService service, FilterLocation loc, List<XMLConfig> fconfig) throws ConfigurationException {

		try {
			Set<IRadonFilter> result = new ListOrderedSet() ;
			for (XMLConfig config : fconfig) {
				IRadonFilter filter = null;
				if (config.hasChild("configured-object")) {
					
					filter = (IRadonFilter) ConfigCreator.createConfiguredInstance(config.firstChild("configured-object"));
					// List<String> args = config.childValueList("args.arg");
					// Constructor cons = filterClass.getConstructor(new Class[]{String[].class});
					// // filter = (IRadonFilter) cons.newInstance(args.toArray(new String[0]));
					// filter = (IRadonFilter) cons.newInstance(new Object[]{args.toArray(new String[0])});
				} else if (config.hasAttribute("lang")) {
					// @todo : other language script support ;
					String scriptSource = config.getAttributeValue("script-source") ;
					final File file = new File(scriptSource);
					if (!file.exists()) {
						Logger.getLogger(FilterUtil.class.getName()).warning("not found source-file : " + file.getName())  ;
						continue ;
					}
					filter = ScriptFactory.createFilter(config.getAttributeValue("lang"), file) ;
				} else {
					Class filterClass = Class.forName(config.getAttributeValue("class"));
					filter = (IRadonFilter) filterClass.getConstructor().newInstance();
				}
				
				if (config.hasChild(ATTRIBUTE)) {
					List<XMLConfig> attrs = config.children(ATTRIBUTE);
					for (XMLConfig ac : attrs) {
						if (StringUtil.isBlank(ac.getAttributeValue("id"))) continue ;
						filter.addAttribute(ac.getAttributeValue("id"), AttributeUtil.create(ac.getAttrMap(), ac.getElementValue()));
					}

				}
				result.add(filter);
			}

			 for (IRadonFilter filter : result) {
				 if (loc.equals(FilterLocation.PRE)) service.addPreFilter(filter) ; 
				 else if (loc.equals(FilterLocation.AFTER)) service.addAfterFilter(filter);
			}
		} catch (IllegalAccessException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		} catch (ClassNotFoundException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		} catch (SecurityException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		} catch (NoSuchMethodException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		} catch (IllegalArgumentException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		} catch (InstantiationException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		} catch (InvocationTargetException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		} catch (InstanceCreationException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		} catch (IOException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		}
	}

	public static void setFilter(IService service, XMLConfig config) throws ConfigurationException {
		makeFilter(service, FilterLocation.PRE,  config.children("prefilter")) ;
		makeFilter(service, FilterLocation.AFTER, config.children("afterfilter")) ;
	}
}

class FilterEntry {
	private IRadonFilter filter;
	private IService iservice;
	
	IFilterResult preHandle(Request request, Response response) {
		return filter.preHandle(iservice, request, response);
	}
	
	IFilterResult afterHandle(Request request, Response response) {
		return filter.afterHandle(iservice, request, response);
	}
	
	private FilterEntry(IRadonFilter filter, IService service) {
		this.filter = filter;
		this.iservice = service;
	}
	
	
	static List<FilterEntry> toList(List<IRadonFilter> filters, IService service) {
		List<FilterEntry> result = ListUtil.newList();
		for (IRadonFilter filter : filters) {
			result.add(new FilterEntry(filter, service));
		}
		return result;
	}
	
}
