package net.ion.radon.client;

import net.ion.radon.core.Aradon;

public class AradonClientFactory {

	
	public static AradonClient create(String hostAddress) {
		final AradonClient result = AradonHttpClient.create(hostAddress);
		return result;
	}

	public static AradonClient create(Aradon aradon) {
		final AradonInnerClient result = AradonInnerClient.create(aradon);
		return result;
	}

}
