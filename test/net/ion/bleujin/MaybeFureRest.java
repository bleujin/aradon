package net.ion.bleujin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;

public class MaybeFureRest {
//
//	public void handle(Request request, Response response) {
//		final MessageHandler handler = new MessageHandler(response);
//		BlockingQueueRepresentation entity = new BlockingQueueRepresentation(MediaType.TEXT_HTML, handler.getQueue());
//		response.setEntity(entity);
//		response.suspend(60L * 1000L); // suspend for 60seconds maximum timeout
//
//		new Thread("Message-Pulse") {
//			public void run() {
//				// start the message stream
//				handler.getQueue().add(new StringRepresentation("<html><body><h1>Testing...</h1>"));
//				handler.getResponse().resume(false);
//
//				try {
//					// send 10 messages, one every 3 seconds
//					for (int i = 0; i < 10; i++) {
//						sleep(3000L);
//						handler.getQueue().add(new StringRepresentation(String.format("<div>%d %TT</div>", i, new Date())));
//						handler.getResponse().resume(false);
//					}
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} finally {
//					// complete the response, roughly 30 seconds after response initiated, therefore before 60 seconds timeout
//					handler.getQueue().add(new StringRepresentation("</body></html>"));
//					handler.getResponse().resume(true);
//				}
//			}
//		}.start();
//	}

	public class MessageHandler {
		public MessageHandler(Response response) {
			_response = response;
			_queue = new LinkedBlockingQueue<Representation>();
		}

		public BlockingQueue<Representation> getQueue() {
			return _queue;
		}

		public Response getResponse() {
			return _response;
		}

		private final Response _response;
		private final BlockingQueue<Representation> _queue;
	}

	public class BlockingQueueRepresentation extends OutputRepresentation {
		public BlockingQueueRepresentation(MediaType mediaType, BlockingQueue<Representation> representations) {
			super(mediaType);
			_representations = representations;
		}

		@Override
		public void write(OutputStream outputStream) throws IOException {
			List<Representation> representations = new LinkedList<Representation>();

			_representations.drainTo(representations);

			if (!representations.isEmpty()) {
				for (Representation representation : representations) {
					representation.write(outputStream);
				}
			}
		}

		private final BlockingQueue<Representation> _representations;
	}
}
