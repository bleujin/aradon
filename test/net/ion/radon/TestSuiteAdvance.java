package net.ion.radon;

import net.ion.radon.config.TestResourceExceptionHandler;
import net.ion.radon.core.TestLogging;
import net.ion.radon.core.let.TestAbstractLet;
import net.ion.radon.core.let.TestGeneral;
import net.ion.radon.core.let.TestPathInfo;
import net.ion.radon.core.let.TestSection;
import net.ion.radon.util.TestAradonTester;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestGeneral.class, TestResourceExceptionHandler.class, TestSection.class,  TestPathInfo.class, TestAradonTester.class, TestLogging.class, TestAbstractLet.class})
public class TestSuiteAdvance {

}
