package net.ion.radon.socketio;

import static org.junit.Assert.*;
import net.ion.framework.util.Debug;
import net.ion.radon.InfinityThread;
import net.ion.radon.TestAradon;
import net.ion.radon.core.NotificationSection;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.impl.section.ServletInfo;
import net.ion.radon.socketio.examples.chat.ChatSocketServlet;
import net.ion.radon.socketio.server.SocketIOServlet;

import org.apache.commons.lang.ClassUtils;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestNotificationSection extends TestAradon {

	@Test
	public void testHello() throws Exception {
		initAradon();

		Request request = new Request(Method.GET, "riap://component/another/hello");
		Response response = aradon.handle(request);

		assertEquals(200, response.getStatus().getCode());
	}

	@Test
	public void testSimpleTest() throws Exception {
		initAradon();

		// NotificationSection noti = NotificationSection.create(aradon, "noti", "61.250.201.78", 9050) ;
		//
		// noti.getServiceContext().putAttribute("socket.base.dir", "./resource/socket/") ;
		// noti.attach(ServletInfo.create("chat", "/chat.html", ChatSocketServlet.class)) ;
		// aradon.attach(noti) ;

		aradon.startServer(9002);

		new InfinityThread().startNJoin();
	}

	@Test
	public void testInit() throws Exception {
		initAradon();

		NotificationSection noti = NotificationSection.create(aradon, "noti", "61.250.201.78", 9050);

		noti.getServiceContext().putAttribute("socket.base.dir", "./resource/socket/");
		noti.attach(ServletInfo.create("chat", "/chat.html", ChatSocketServlet.class));

		aradon.attach(noti);
		// noti.attach("/WebSocketMain.swf", "./test/net/ion/bleujin/socketio/examples/chat/chat.html") ;
		// noti.attach("/Common.gwt.xml", "./test/net/ion/bleujin/socketio/examples/chat/chat.html") ;

		for (int i = 0; i < 1; i++) {
			Request request = new Request(Method.GET, "riap://component/noti/chat.html");
			Response response = new Response(request);
			aradon.handle(request, response);

			Debug.debug(response.getEntityAsText());
		}
	}

	@Test
	public void testFindSuperClass() throws Exception {
		Class clz = ChatSocketServlet.class;
		Debug.debug(ClassUtils.getAllSuperclasses(clz).contains(SocketIOServlet.class));
	}
}
