package net.ion.radon;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.context.ContextSample;
import net.ion.radon.filter.FilterSample;
import net.ion.radon.let.FirstServer;
import net.ion.radon.let.LetSample;
import net.ion.radon.representation.RepresentationSample;
import net.ion.radon.script.SampleScript;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { FirstServer.class, LetSample.class, FilterSample.class, ContextSample.class, RepresentationSample.class, SampleScript.class })
public class SampleAll {

	@org.junit.Test
	public static Test suite() {
		System.setProperty(Debug.PROPERTY_KEY, "off");
		TestSuite ts = new TestSuite("Test SampleAll All");

		// 
		// ts.addTestSuite(TestAllNaradon.class) ;
		return ts;
	}

}

