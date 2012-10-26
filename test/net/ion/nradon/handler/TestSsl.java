package net.ion.nradon.handler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.ion.framework.util.Debug;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.netty.NettyWebServer;
import net.ion.nradon.testutil.HttpClient;

import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.data.Protocol;

public class TestSsl {


    @BeforeClass
    public static void disableCertValidationSetUp() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    @Test
    public void setsSecureHttpsServerHeader() throws Exception {
    	
        NettyWebServer radon = startHttps();
        radon.start();

        try {
            HttpsURLConnection urlConnection = HttpClient.httpsGet(radon, "/");
            assertEquals("My Server", urlConnection.getHeaderField("Server"));
            assertEquals("body", HttpClient.contents(urlConnection));
        } finally {
            radon.stop();
        }
    }
    
    @Test
    public void testProtocol() throws Exception {
		Debug.line(Protocol.HTTPS.getSchemeName()) ;
	}
    
    
    

	private NettyWebServer startHttps() throws FileNotFoundException, IOException {
        NettyWebServer radon = RadonConfiguration.newBuilder(10443)
                .setupSsl(new File("resource/keystore/webbit.keystore"), "webbit")
                .add(new ServerHeaderHandler("My Server"))
                .add(new StringHttpHandler("text/plain", "body")).createRadon();

		return radon;
	}

    
    
}
