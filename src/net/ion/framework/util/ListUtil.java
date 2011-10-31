package net.ion.framework.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;

public class ListUtil extends ListUtils{

	public final static List EMPTY = Collections.EMPTY_LIST ;
	
	
	public final static <T> List<T> newList(){
		return new ArrayList<T>() ;
	}
	
	public final static <T> List<T> newSyncList(){
		return Collections.synchronizedList(new ArrayList<T>()) ;
	}
	
	public static <T> List<T> create(T obj) {
		return toList(obj) ;
	}

	public static List<Map<String, ?>> create(Map<String, ?> obj) {
		List<Map<String, ?>> result = new ArrayList<Map<String, ?>>() ;
		result.add(obj) ;
		
		return result ;
	}
	
	public final static <T> List<T> toList(T... objs){
		return Arrays.asList(objs) ;
	}

	public final static <T> List<T> syncList(T... objs){
		List<T> result = new ArrayList<T>() ;
		for (T obj : objs) {
			result.add(obj) ;
		}
		return Collections.synchronizedList(result) ;
	}
	
	public final static List<Integer> rangeNum(int... range){
		int from = (range.length == 1) ? 0 : range[0] ;
		int to = (range.length == 1) ? range[0] : range[1] ;
		List<Integer> result = newList() ;
		for (int i = from ; i < to ; i++) {
			result.add(i) ;
		}
		return result ;
	}
	
	public static Object[] toArray(Object object) {
		if (object instanceof List) {
			return ((List)object).toArray() ;
		} else if (object.getClass().isArray()){
			return (Object[])object ;
		} else if (object instanceof Collection){
			return ((Collection)object).toArray() ;
		} else { // 
			return new Object[]{object} ;
		}
	}
}
