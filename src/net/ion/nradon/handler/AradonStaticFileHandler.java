package net.ion.nradon.handler;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

import net.ion.framework.util.Debug;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.FileRepresentation;
import org.restlet.service.MetadataService;

public class AradonStaticFileHandler extends AbstractHttpHandler {

	private Executor ioThread;
	private final File dir;
	private MetadataService mservice ;

	public AradonStaticFileHandler(File dir, Executor ioThread) throws Exception {
		this.dir = dir;
		this.ioThread = ioThread;
		this.mservice = new MetadataService() ;
	}

	public AradonStaticFileHandler(String dir, Executor ioThread) throws Exception {
		this(new File(dir), ioThread);
	}

	public AradonStaticFileHandler(File dir) throws Exception {
		this(dir, newFixedThreadPool(4));
	}

	public AradonStaticFileHandler(String dir) throws Exception {
		this(new File(dir));
	}

	public void handleHttpRequest(final HttpRequest request, final HttpResponse response, HttpControl control) throws Exception {

		String path = withoutTrailingSlashOrQuery(request.uri()) ;
		final File file = resolveFile(path) ;
		if (!file.exists()) {
			control.nextHandler(request, response) ;
			return ;
		}
		// @Todo 304 cache..
		ioThread.execute(new Runnable() {
			Request req = new Request(Method.GET, request.uri());
			public void run() {
				Response res = new Response(req);
				MediaType mtype = mservice.getMediaType(StringUtil.substringAfterLast(file.getName(), "."));
				if (mtype == null) mtype = MediaType.ALL ;
				res.setEntity(new FileRepresentation(file, mtype));
				response.write(res);
			}
		});
	}

	private String withoutTrailingSlashOrQuery(String path) {
		int queryStart = path.indexOf('?');
		if (queryStart > -1) {
			path = path.substring(0, queryStart);
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}
	
	private File resolveFile(String path) throws IOException {
		// Find file, relative to roo
		File result = new File(dir, path).getCanonicalFile();

		// For security, check file really does exist under root.
		String fullPath = result.getPath();
		if (!fullPath.startsWith(dir.getCanonicalPath() + File.separator) && !fullPath.equals(dir.getCanonicalPath())) {
			return null; // Prevent paths like http://foo/../../etc/passwd
		}
		return result;
	}

}
