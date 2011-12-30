package net.ion.nradon.handler.authentication;

import static org.junit.Assert.assertEquals;

import net.ion.nradon.handler.authentication.InMemoryPasswords;
import net.ion.nradon.handler.authentication.PasswordAuthenticator.ResultCallback;

import org.junit.Test;

public class TestInMemoryPasswords {

	@Test
	public void authenticateWithEmptyUsernamePassword() {
		TestResultCallback callback = new TestResultCallback();

		InMemoryPasswords target = new InMemoryPasswords();
		target.authenticate(null, null, null, callback, null);

		assertEquals(false, callback.getResult());
	}
	
	@Test
	public void authenticateWithBadUsernamePassword() {
		TestResultCallback callback = new TestResultCallback();

		InMemoryPasswords target = new InMemoryPasswords();
		target.authenticate(null, "non", "existent", callback, null);

		assertEquals(false, callback.getResult());
	}
	
	@Test
	public void authenticateWithBadPassword() {
		TestResultCallback callback = new TestResultCallback();

		InMemoryPasswords target = new InMemoryPasswords();
		target.add("non", "empty");
		target.authenticate(null, "non", "existent", callback, null);

		assertEquals(false, callback.getResult());
	}
	
	@Test
	public void authenticateWithGoodUsernamePassword() {
		String username = "top";
		String password = "secret";
		TestResultCallback callback = new TestResultCallback();
		
		InMemoryPasswords target = new InMemoryPasswords();
		target.add(username, password);
		target.authenticate(null, username, password, callback, null);

		assertEquals(true, callback.getResult());
	}

	class TestResultCallback implements ResultCallback {
		private boolean result = false;
		
		public void success() {
			result = true;
		}

		public void failure() {
			result = false;
		}
		
		public boolean getResult() {
			return result;
		}
	}
}
