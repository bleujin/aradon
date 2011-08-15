package net.ion.radon.client;

import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeRequest;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.ext.crypto.DigestAuthenticator;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.security.MapVerifier;

/**
 * Simple application code that illustrates the usage of HTTP_DIGEST authentication (Restlet 2.0m6).
 * 
 */
public class TestComponent extends Component {
	public static void main(String[] args) throws Exception {
		// Create the component that listens to the 8182 port.
		Component component = new TestComponent();
		component.getServers().add(Protocol.HTTP, 8182);

		// Restlet that simply replies to requests with an "hello, world" text
		// message
		Restlet restlet = new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				response.setEntity(new StringRepresentation("hello, world", MediaType.TEXT_PLAIN));
			}
		};

		DigestAuthenticator guard = new DigestAuthenticator(null, "TestRealm", "mySecretServerKey");

		// Instantiates a Verifier of identifier/secret couples based on a
		// simple Map.
		MapVerifier mapVerifier = new MapVerifier();
		// Load a single static login/secret pair.
		mapVerifier.getLocalSecrets().put("login", "secret".toCharArray());
		guard.setWrappedVerifier(mapVerifier);

		// Guard the restlet
		guard.setNext(restlet);

		component.getDefaultHost().attachDefault(guard);

		// Start the component
		component.start();
		// Launch the test client
		testClient();
		// Stop the component
		component.stop();
	}

	/**
	 * Client test code.
	 */
	public static void testClient() {
		ClientResource resource = new ClientResource("http://localhost:8182/");

		resource.setChallengeResponse(ChallengeScheme.HTTP_DIGEST, "login", "secret");
		// Send the first request with unsufficient authentication.
		try {
			resource.get();
		} catch (ResourceException re) {
		}
		// Should be 401, since the client needs some data sent by the server in
		// order to complete the ChallengeResponse.
		System.out.println(resource.getStatus());

		// Complete the challengeResponse object according to the server's data
		// 1- Loop over the challengeRequest objects sent by the server.
		ChallengeRequest c1 = null;
		for (ChallengeRequest challengeRequest : resource.getChallengeRequests()) {
			if (ChallengeScheme.HTTP_DIGEST.equals(challengeRequest.getScheme())) {
				c1 = challengeRequest;
				break;
			}
		}

		// 2- Create the Challenge response used by the client to authenticate
		// its requests.
		ChallengeResponse challengeResponse = new ChallengeResponse(c1, resource.getResponse(), "login", "secret".toCharArray());
		resource.setChallengeResponse(challengeResponse);

		// Try authenticated request
		resource.get();
		// Should be 200.
		System.out.println(resource.getStatus());
	}
}
