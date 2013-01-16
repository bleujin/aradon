package net.ion.nradon.handler;

import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.httpGet;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;
import net.ion.radon.core.Aradon;

import org.junit.After;
import org.junit.Test;

public class TestFromAradon {
    private Radon radon = null;
    
    @After
    public void die() throws IOException, InterruptedException, ExecutionException {
        if (radon != null) radon.stop().get();
    }

    @Test
    public void forwardsAliasedPath() throws Exception {
        RadonConfigurationBuilder rbuilder = RadonConfiguration.newBuilder(59504) ;
        
        this.radon = Aradon.create().toRadon() ;
        radon.getConfig().add(new PathMatchHandler("/tomayto", new AliasHandler("/tomato"))).add(new PathMatchHandler("/tomato", new StringHttpHandler("text/plain", "body"))) ;
        
        radon.start().get() ;
        assertEquals("body", contents(httpGet(radon, "/tomayto")));
    }
}
