package net.ion.nradon.handler;

import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.httpGet;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;

import org.junit.After;
import org.junit.Test;

public class TestAliasHandler {
    private Radon radon = null;
    private RadonConfigurationBuilder configBuilder = RadonConfiguration.newBuilder(59504) ;
    
    @After
    public void die() throws IOException, InterruptedException, ExecutionException {
        radon.stop().get();
    }

    @Test
    public void forwardsAliasedPath() throws Exception {
        this.radon = configBuilder
                .add("/tomayto", new AliasHandler("/tomato"))
                .add("/tomato", new StringHttpHandler("text/plain", "body"))
                .startRadon();
        assertEquals("body", contents(httpGet(radon, "/tomayto")));
    }

    @Test
    public void forwardsAliasedPath2() throws Exception {
        this.radon = configBuilder
                .add("/tomayto", new AliasHandler("/tomato/304"))
                .add("/tomato/{num}", new StringHttpHandler("text/plain", "body"))
                .startRadon();
        assertEquals("body", contents(httpGet(radon, "/tomayto")));
    }

}
