package net.ion.radon.core.config;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradonMethod;
import net.ion.radon.core.TestAradonInit;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestConfigFile.class, TestContextAttribute.class, TestAradonInit.class, TestAradonMethod.class, TestAradonServerRun.class})
public class TestSuiteServerRunner {
	
}
