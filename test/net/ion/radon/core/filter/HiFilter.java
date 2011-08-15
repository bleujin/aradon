package net.ion.radon.core.filter;

import java.util.List;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.core.IService;

import org.restlet.Request;
import org.restlet.Response;

public class HiFilter extends IRadonFilter{

	
	static List<Object> result = ListUtil.newList() ;
	
	private int num ;
	private IFilterResult expectResult ;
	
	public HiFilter(){
		this(0) ;
	}
	public HiFilter(int num){
		this(num, IFilterResult.CONTINUE_RESULT) ;
	}
	
	public HiFilter(int num, IFilterResult expect){
		this.num = num ;
		this.expectResult = expect ;
	}
	
	
	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {
		Debug.line("Hi AFTER!............", num, iservice.getServiceContext().getZone()) ;
		result.add(this.num) ;
		return expectResult;
	}

	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		Debug.line("Hi PRE!............", num, iservice.getServiceContext().getZone()) ;
		result.add(this.num) ;
		return expectResult;
	}
}
