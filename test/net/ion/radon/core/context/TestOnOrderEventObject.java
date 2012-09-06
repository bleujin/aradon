package net.ion.radon.core.context;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.ion.framework.util.ListUtil;
import net.ion.radon.core.IService;

import org.junit.Test;


public class TestOnOrderEventObject {

	
	@Test
	public void order() throws Exception {
		List<OnEventObject> list = ListUtil.toList(new BlankObject("alice"), new BlankObject("brabo"), new OrderObject("tango", 1), new OrderObject("zebra", 3), new OrderObject("chali", 2), new OrderObject("ego", 101));
	
		Collections.sort(list, new Comparator<OnEventObject>(){
			
			private int order(OnEventObject o){
				if (o instanceof OnOrderEventObject){
					return ((OnOrderEventObject)o).order() ;
				} else {
					return 100 ;
				}
			}
			
			public int compare(OnEventObject o1, OnEventObject o2) {
				return order(o1) - order(o2) ;
			}}) ;
		
		assertEquals("tango", list.get(0).toString()) ;
		assertEquals("chali", list.get(1).toString()) ;
		assertEquals("zebra", list.get(2).toString()) ;
		assertEquals("ego", list.get(5).toString()) ;
	}
}


class BlankObject implements OnEventObject {

	private String name ;
	public BlankObject(String name){
		this.name = name ;
	}
	
	public void onEvent(AradonEvent event, IService service) {
	}

	public String toString(){
		return name ;
	}
}

class OrderObject implements OnOrderEventObject {
	private int order;
	private String name ;
	public OrderObject(String name, int order){
		this.name = name ;
		this.order = order ;
	}
	
	public int order() {
		return order;
	}

	public void onEvent(AradonEvent event, IService service) {
	}
	
	public String toString(){
		return name ;
	}
}