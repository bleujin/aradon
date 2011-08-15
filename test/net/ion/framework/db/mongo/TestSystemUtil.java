package net.ion.framework.db.mongo;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.apache.commons.lang.SystemUtils;

public class TestSystemUtil extends TestCase{

	
	public void testConfirm() throws Exception {
		Debug.debug(SystemUtils.OS_ARCH, SystemUtils.JAVA_VM_INFO ) ;
		
	}
}
