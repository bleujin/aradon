package net.ion.radon.client;

import net.ion.radon.core.server.TestAradonClientFactory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { TestAradonClientFactory.class, TestAradonClient.class, TestRequestParameter.class, TestRequestHeader.class, TestSerialRequest.class, TestMultiPartRequest.class, TestClientCache.class })
public class TestSuiteClient {

}
