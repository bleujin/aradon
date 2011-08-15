package net.ion.radon.impl.util;

import net.ion.framework.util.Debug;

import org.apache.commons.collections.Closure;

public class DebugPrinter implements Closure{
	public void execute(Object obj) {
		Debug.debug(obj, obj.getClass()) ;
	}
}