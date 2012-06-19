package net.ion.radon.core.filter;

import java.util.List;

import net.ion.framework.util.ListUtil;

import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;

public class IFilterResults {

	private List<IFilterResult> results ;
	public IFilterResults(List<IFilterResult> results) {
		this.results = results ;
	}
	
	public static IFilterResults create(IFilterResult result){
		return new IFilterResults(ListUtil.create(result));
	}
	public static IFilterResults create(List<IFilterResult> result) {
		return new IFilterResults(result);
	}

	public boolean isAllowNextProcess() {
		for (IFilterResult result : results) {
			if (! result.isAllowNextProcess()) return false ;
		}
		return true;
	}

	public Representation getRepresentation() {
		if(results.size() > 1) return new EmptyRepresentation();
		return  results.get(0).getReplaceRepresentation();
	}

}
