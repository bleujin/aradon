package net.ion.nradon.let;

import net.ion.framework.util.ListUtil;
import net.ion.nradon.AbstractEventSourceResource;
import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceMessage;

public class SampleEventSource extends AbstractEventSourceResource{

	public void onClose(EventSourceConnection connection) throws Exception {
	}

	public void onOpen(EventSourceConnection connection) throws Exception {
		for (String message : ListUtil.toList("hello", "jin", "bye")) {
            String data = message + " " + connection.httpRequest().queryParam("echoThis");
            connection.send(new EventSourceMessage(data));
        }
	}

}
