package net.ion.radon.core;

import junit.framework.TestCase;
import net.ion.radon.core.annotation.TestAnnotation;
import net.ion.radon.core.annotation.TestFormAnnotation;
import net.ion.radon.core.let.TestLet;
import net.ion.radon.core.let.TestParameter;
import net.ion.radon.core.let.TestRequest;
import net.ion.radon.core.let.TestResourceLet;
import net.ion.radon.core.let.TestResponse;
import net.ion.radon.core.let.TestServerResource;
import net.ion.radon.impl.let.TestIServiceLet;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestInitAradon.class, TestSectionAttach.class, TestLet.class, TestIServiceLet.class, TestAnnotation.class, TestFormAnnotation.class, TestResourceLet.class, TestRequest.class, TestResponse.class, TestParameter.class, TestContext.class, TestServerResource.class})
public class TestSuiteCore extends TestCase {

}
