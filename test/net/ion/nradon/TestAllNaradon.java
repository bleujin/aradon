package net.ion.nradon;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.nradon.handler.TestSuiteHandler;
import net.ion.nradon.helpers.TestSuiteHelpers;
import net.ion.nradon.netty.TestSuiteNetty;
import net.ion.nradon.stub.TestSuiteStub;
import net.ion.radon.server.TestContinusResponse;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestSuiteHandler.class, TestSuiteHelpers.class, TestSuiteNetty.class, TestSuiteStub.class, TestContinusResponse.class}) 
public class TestAllNaradon extends TestCase{

	@org.junit.Test
	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Test NAradon All") ;
		

		return ts ;
	}
}
