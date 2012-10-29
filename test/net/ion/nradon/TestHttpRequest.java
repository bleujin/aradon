package net.ion.nradon;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.List;

import net.ion.nradon.stub.StubHttpRequest;

import org.junit.Test;

public class TestHttpRequest {
	private static final List<String> EMPTY = Collections.emptyList();

	@Test
	public void extractsSingleQueryParameter() throws Exception {
		HttpRequest req = new StubHttpRequest("http://host.com:8080/path?fish=cod&fruit=orange");
		assertEquals("cod", req.queryParam("fish"));
	}

	@Test
	public void extractsMultipleQueryParameters() throws Exception {
		HttpRequest req = new StubHttpRequest("http://host.com:8080/path?fish=cod&fruit=orange&fish=smørflyndre");
		assertEquals(asList("cod", "smørflyndre"), req.queryParams("fish"));
	}

	@Test
	public void alwaysReturnsEmptyListWhenThereIsNoQueryString() throws Exception {
		HttpRequest req = new StubHttpRequest("http://host.com:8080/path");
		assertEquals(EMPTY, req.queryParams("fish"));
		assertNull(req.queryParam("fish"));
	}

	@Test
	public void returnsEmptyListWhenThereIsNoSuchParameter() throws Exception {
		HttpRequest req = new StubHttpRequest("http://host.com:8080/path?poisson=cabillaud");
		assertEquals(EMPTY, req.queryParams("fish"));
		assertNull(req.queryParam("fish"));
	}


}
