package net.ion.radon.client;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { TestAradonClientFactory.class, TestAradonClient.class, TestRequestParameter.class, TestRequestHeader.class, TestSerialRequest.class, TestMultiPartRequest.class, TestClientCache.class })
public class TestSuiteClient {

}
