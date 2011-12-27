package net.ion.radon.script;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class) 
@Suite.SuiteClasses({TestGroovyFilter.class, TestRhinoFilter.class})
public class TestScriptAll {
	
}
