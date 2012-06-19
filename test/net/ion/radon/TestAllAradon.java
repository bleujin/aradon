package net.ion.radon;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.nradon.TestAllNaradon;
import net.ion.radon.client.TestSuiteClient;
import net.ion.radon.core.TestSuiteCore;
import net.ion.radon.core.config.TestSuiteServerRunner;
import net.ion.radon.core.filter.TestSuiteFilter;
import net.ion.radon.impl.TestSuiteLet;
import net.ion.radon.util.TestSuiteCommon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { TestSuiteCore.class, TestSuiteLet.class, TestSuiteClient.class, TestSuiteCommon.class, TestSuiteFilter.class, TestSuiteServerRunner.class, TestSuiteAdvance.class, TestAllNaradon.class })
public class TestAllAradon {

	@org.junit.Test
	public static Test suite() {
		System.setProperty(Debug.PROPERTY_KEY, "off");
		TestSuite ts = new TestSuite("Test Aradon All");

		// 
		// ts.addTestSuite(TestAllNaradon.class) ;
		return ts;
	}

}
