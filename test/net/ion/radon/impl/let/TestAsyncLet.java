package net.ion.radon.impl.let;

import java.io.IOException;
import java.io.ObjectInputStream;

import junit.framework.TestCase;
import net.ion.framework.rest.StdObject;
import net.ion.framework.util.Debug;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;

public class TestAsyncLet extends TestCase {

	public void testLocalAsync() throws Exception {
		Client client = new Client(Protocol.HTTP);

		Request request = new Request(Method.GET, "http://localhost:8182/");
		client.handle(request, new Uniform() {
			public void handle(Request request, Response response) {
				try {
					Representation r = response.getEntity();

					final Object readObject = new ObjectInputStream(r.getStream()).readObject();
					StdObject sto = (StdObject) readObject;
					Debug.debug(sto.getRequest().getAttributes(), sto.getDatas(), sto.getResponse());

					Debug.debug(r.getClass(), readObject, readObject.getClass());

					Debug.line(r.getText());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		Debug.debug("END....");
		// Thread.sleep(1000000) ;
	}

}
