package net.ion.nradon.handler.authentication;

import java.util.Map;
import java.util.concurrent.Executor;

import net.ion.framework.util.MapUtil;
import net.ion.nradon.HttpRequest;

/**
 * Implementation of PasswordAuthenticator that verifies usernames and password from a prepopulated hashmap.
 */
public class InMemoryPasswords implements PasswordAuthenticator {

	private final Map<String, String> usernameToPasswords = MapUtil.newMap() ;

	public InMemoryPasswords add(String username, String password) {
		usernameToPasswords.put(username, password);
		return this;
	}

	public void authenticate(HttpRequest request, String username, String password, ResultCallback callback, Executor handlerExecutor) {
		String expectedPassword = usernameToPasswords.get(username);
		if (expectedPassword != null && password.equals(expectedPassword)) {
			callback.success();
		} else {
			callback.failure();
		}
	}
}
