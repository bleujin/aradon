package net.ion.nradon.let;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

import net.ion.framework.util.StringUtil;
import net.ion.nradon.AbstractSingleHttpResource;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.SPathConfiguration;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.FileRepresentation;
import org.restlet.service.MetadataService;

public class SampleHttpResource extends AbstractSingleHttpResource {


	private Executor ioThread;
	private File dir;
	private MetadataService mservice ;

	public SampleHttpResource(){
	}
	
	@Override
	public void init(SectionService parent, TreeContext context, SPathConfiguration econfig){
		super.init(parent, context, econfig) ;
		
		this.dir = new File(context.getAttributeObject("base.dir", "./resource/", String.class)) ;
		this.ioThread = newFixedThreadPool(4) ;
		this.mservice = new MetadataService() ;
	}


	@Override
	public void handleHttpRequest(final HttpRequest request, final HttpResponse response, HttpControl control) throws Exception {

		String path = withoutTrailingSlashOrQuery(request) ;
		final File file = resolveFile(path) ;
		if (file == null || !file.exists()) {
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
