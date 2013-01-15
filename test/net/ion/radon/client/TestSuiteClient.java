package net.ion.radon.client;

import net.ion.radon.core.annotation.TestAnnotation;
import net.ion.radon.core.server.TestClientFactory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { TestClientFactory.class, TestAradonClient.class, TestRequestParameter.class,  TestRequestHeader.class, TestRequestCookie.class, TestSerialRequest.class, TestJsonRequest.class, TestMultiPartRequest.class, TestAnnotation.class,  TestClientCache.class, TestAsyncClient.class })
public class TestSuiteClient {

}
