package net.ion.nradon.netty;

import net.ion.nradon.netty.contrib.TestEventSourceMessage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { TestEventSourceMessage.class, TestFlashPolicyFile.class, TestNettyWebServer.class })
public class TestSuiteNetty {

}
