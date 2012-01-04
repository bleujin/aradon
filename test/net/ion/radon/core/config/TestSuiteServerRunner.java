package net.ion.radon.core.config;

import net.ion.radon.TestAradonMethod;
import net.ion.radon.core.TestAradonInit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestConfigFile.class, TestContextAttribute.class, TestAradonInit.class, TestAradonMethod.class, TestAradonServerRun.class})
public class TestSuiteServerRunner {
	
}
