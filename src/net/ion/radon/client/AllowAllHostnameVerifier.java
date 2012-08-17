package net.ion.radon.client;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AllowAllHostnameVerifier implements HostnameVerifier {
	public boolean verify(String s, SSLSession sslSession) {
		return true;
	}
}