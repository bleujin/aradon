package net.ion.radon.core.filter;

import net.ion.radon.core.let.AbstractLet;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Filter;

public class IFilterResult {

	private static final ResourceException dft = new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED) ; 
	
	public static final IFilterResult CONTINUE_RESULT = new IFilterResult(Filter.CONTINUE, AbstractLet.EMPTY_REPRESENTATION);
	public static final IFilterResult STOP_RESULT = new IFilterResult(Filter.STOP, dft); // when exception occured
	public static final IFilterResult SKIP_RESULT = new IFilterResult(Filter.SKIP, AbstractLet.EMPTY_REPRESENTATION); // when replaced
	
	
	private int filterResult ;
	private ResourceException cause ;
	private Representation replace ;
	
	private IFilterResult(int filterResult, ResourceException cause) {
		this.filterResult = filterResult ;
		this.cause = (cause == null) ? new ResourceException(Status.SERVER_ERROR_INTERNAL) : cause ;
	}
	
	private IFilterResult(int filterResult, Representation replace){
		this.filterResult = filterResult ;
		this.replace = replace ;
	}

	public boolean isAllowNextProcess() {
		return filterResult == Filter.CONTINUE;
	}

	public int getResult() {
		return filterResult;
	}
	
	public ResourceException getCause(){
		return this.cause ;
	}

	public Representation getReplaceRepresentation(){
		return this.replace ;
	}
	
	public static IFilterResult stopResult(ResourceException cause) {
		return new IFilterResult(Filter.STOP, cause);
	}

	public static IFilterResult skipResult(Representation replaceRepresentation) {
		return new IFilterResult(Filter.SKIP, replaceRepresentation);
	}

}
