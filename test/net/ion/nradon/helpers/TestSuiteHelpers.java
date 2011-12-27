package net.ion.nradon.helpers;

import junit.framework.TestCase;
import net.ion.nradon.handler.TestAliasHandler;
import net.ion.nradon.handler.TestCompression;
import net.ion.nradon.handler.TestCookie;
import net.ion.nradon.handler.TestEmbeddedResourceHandler;
import net.ion.nradon.handler.TestPathMatchHandler;
import net.ion.nradon.handler.TestPost;
import net.ion.nradon.handler.TestServerHeaderHandler;
import net.ion.nradon.handler.TestStaleConnection;
import net.ion.nradon.handler.TestStaticFileHandler;
import net.ion.nradon.handler.authentication.TestInMemoryPasswords;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestBase64.class, TestHex.class, TestQueryParameters.class, TestUTF8Output.class})
public class TestSuiteHelpers extends TestCase {
	

}
