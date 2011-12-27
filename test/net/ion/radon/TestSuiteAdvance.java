package net.ion.radon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.ion.radon.config.TestResourceExceptionHandler;
import net.ion.radon.core.TestLogging;
import net.ion.radon.core.TestPlugin;
import net.ion.radon.core.let.TestAbstractLet;
import net.ion.radon.core.let.TestGeneral;
import net.ion.radon.core.let.TestInboundLet;
import net.ion.radon.core.let.TestPathInfo;
import net.ion.radon.core.let.TestSection;
import net.ion.radon.util.TestAradonTester;
import junit.framework.Test;
import junit.framework.TestSuite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestGeneral.class, TestResourceExceptionHandler.class, TestSection.class,  TestPathInfo.class, TestInboundLet.class, TestAradonTester.class, TestLogging.class, TestPlugin.class, TestAbstractLet.class})
public class TestSuiteAdvance {

}
