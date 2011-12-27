package net.ion.radon.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.core.let.TestLet;
import net.ion.radon.core.let.TestRequest;
import net.ion.radon.core.let.TestResourceLet;
import net.ion.radon.core.let.TestResponse;
import net.ion.radon.core.let.TestServerResource;
import net.ion.radon.server.TestAradonNetty;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestInitAradon.class, TestLet.class, TestResourceLet.class, TestRequest.class, TestResponse.class, TestContext.class, TestServerResource.class})
public class TestSuiteCore extends TestCase {


}
