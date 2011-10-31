package net.ion.radon.core;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.core.let.TestGeneral;
import net.ion.radon.core.let.TestLet;
import net.ion.radon.core.let.TestRequest;
import net.ion.radon.core.let.TestResourceLet;
import net.ion.radon.core.let.TestResponse;
import net.ion.radon.core.let.TestSection;

public class TestAllCore extends TestSuite {

	public static Test suite() {
		System.setProperty(Debug.PROPERTY_KEY, "off");
		TestSuite ts = new TestSuite("Test Aradon Core");

		ts.addTestSuite(TestInitAradon.class);
		ts.addTestSuite(TestLet.class);
		ts.addTestSuite(TestResourceLet.class);

		ts.addTestSuite(TestRequest.class);
		ts.addTestSuite(TestResponse.class);
		ts.addTestSuite(TestContext.class) ;

		return ts;
	}
}
