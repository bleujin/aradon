package net.ion.radon.impl.util;

import net.ion.framework.util.Closure;
import net.ion.framework.util.Debug;

public class DebugPrinter<T> implements Closure<T>{
	public void execute(T obj) {
		Debug.debug(obj, obj.getClass()) ;
	}
}