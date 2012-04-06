package net.ion.nradon.handler.authentication;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.handler.AbstractHttpHandler;
import net.ion.nradon.helpers.Base64;

/**
 * Adds HTTP Basic authentication to a page. Users should provide an implementation of UsernamePasswordAuthenticator to check the supplied credentials.
 * 
 * See samples.authentication.SimplePasswordsExample in the src/tests directory for a really basic usage. To implement a custom authenticator that performs background IO, see samples.authentication.AsyncPasswordsExample.
 * 
 * @see PasswordAuthenticator
 * @see InMemoryPasswords
 */
public class BasicAuthenticationHandler extends AbstractHttpHandler {

	public static final String USERNAME = "user";

	private static final String BASIC_PREFIX = "Basic ";

	private final String realm;
	private final PasswordAuthenticator authenticator;

	public BasicAuthenticationHandler(PasswordAuthenticator authenticator) {
		this(authenticator, "Secure Area");
	}

	public BasicAuthenticationHandler(PasswordAuthenticator authenticator, String realm) {
		this.realm = realm;
		this.authenticator = authenticator;
	}

	public void handleHttpRequest(final HttpRequest request, final HttpResponse response, final HttpControl control) throws Exception {
		String authHeader = request.header("Authorization");
		if (authHeader == null) {
			needAuthentication(response);
		} else {
			if (authHeader.startsWith(BASIC_PREFIX)) {
				String decoded = new String(Base64.decode(authHeader.substring(BASIC_PREFIX.length())));
				final String[] pair = decoded.split(":", 2);
				if (pair.length == 2) {
					final String username = pair[0];
					final String password = pair[1];
					PasswordAuthenticator.ResultCallback callback = new PasswordAuthenticator.ResultCallback() {
						public void success() {
							request.data(USERNAME, username);
							control.nextHandler();
						}

						public void failure() {
							needAuthentication(response);
						}
					};

					authenticator.authenticate(request, username, password, callback, control);
				} else {
					needAuthentication(response);
				}
			}
		}
	}

	private void needAuthentication(HttpResponse response) {
		response.status(401).header("WWW-Authenticate", "Basic realm=\"" + realm + "\"").content("Need authentication").end();
	}

}
