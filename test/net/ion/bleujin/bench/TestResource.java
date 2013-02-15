package net.ion.bleujin.bench;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import junit.framework.TestCase;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.AbstractSingleHttpResource;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.AbstractResourceHandler;
import net.ion.nradon.handler.FileEntry;
import net.ion.nradon.handler.StaticFile;
import net.ion.nradon.handler.TemplateEngine;
import net.ion.nradon.helpers.ClassloaderResourceHelper;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.core.annotation.AnRequest;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.service.MetadataService;

/**
 * <p>Title: TestResource.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: I-ON Communications</p>
 * <p>Date : 2013. 2. 14.</p>
 * @author novision
 * @version 1.0
 */
public class TestResource extends TestCase {

//	@Ignore
//	public void testAradon() throws Exception {
//		Aradon aradon = AradonTester.create().register("", "/view", "resource", IMatchMode.STARTWITH, ResourceLet.class).getAradon() ;
//		aradon.startServer(9000) ;
//		
//		new InfinityThread().startNJoin() ;
//	}
//	
//	@Ignore
//	public void testRadon() throws Exception {
//		File parent = new File("D:/tmp/radon/") ;
//		HttpHandler handler = new AradonStaticFileHandler(parent);
//		
//		Future<Radon> future = RadonConfiguration.newBuilder(9005).add("/view/*", handler).start() ;
//		Radon radon = future.get() ;
//		
//		new InfinityThread().startNJoin() ;
//	}
	
	@Test
	public void testRadonSimple() throws Exception {
		File parent = new File("c:/temp/radon/") ;
		HttpHandler handler = new SimpleStaticFileHandler(parent);
		
		Future<Radon> future = RadonConfiguration.newBuilder(9010).add("/view/*", handler).start() ;
		Radon radon = future.get() ;
		
		new InfinityThread().startNJoin() ;
	}
	
	@Test
	public void testNetty() throws Exception {
		int port = 8080;
		File home = new File("c:/temp/radon/");
		System.getProperties().put("user.dir", home.getCanonicalPath()) ;
		new HttpStaticFileServer(port).run();

		new InfinityThread().startNJoin() ;
	}
	
	
	
	
}


class ResourceLet implements IServiceLet {
	
	@Get
	public Representation viewResource(@AnRequest Request request){
		String remainingPart = request.getResourceRef().getRemainingPart();
		File file = new File("D:/tmp/radon/" + remainingPart); 
		MediaType mediaType = MediaType.IMAGE_ALL;
		return new FileRepresentation(file, mediaType) ;
	}
	
}

class AradonStaticFileHandler extends AbstractSingleHttpResource {

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

	public AradonStaticFileHandler(String dirName, int nThreads) throws Exception{
		this(new File(dirName), newFixedThreadPool(nThreads)) ;
	}

	public AradonStaticFileHandler(String dir) throws Exception {
		this(new File(dir));
	}

	@Override
	public void handleHttpRequest(final HttpRequest request, final HttpResponse response, HttpControl control) throws Exception {

		String path = request.data("*").toString() ;
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

class SimpleStaticFileHandler extends AbstractResourceHandler {

	private final File dir;

    public SimpleStaticFileHandler(File dir, Executor ioThread, TemplateEngine templateEngine) {
        super(ioThread, templateEngine);
        this.dir = dir;
    }

    public SimpleStaticFileHandler(File dir, Executor ioThread) {
        this(dir, ioThread, new StaticFile());
    }

    public SimpleStaticFileHandler(String dir, Executor ioThread, TemplateEngine templateEngine) {
        this(new File(dir), ioThread, templateEngine);
    }

    public SimpleStaticFileHandler(String dir, Executor ioThread) {
        this(dir, ioThread, new StaticFile());
    }

    public SimpleStaticFileHandler(File dir, TemplateEngine templateEngine) {
        this(dir, newFixedThreadPool(4), templateEngine);
    }
    public SimpleStaticFileHandler(File dir) {
        this(dir, new StaticFile());
    }

    public SimpleStaticFileHandler(String dir, TemplateEngine templateEngine) {
        this(new File(dir), templateEngine);
    }

    public SimpleStaticFileHandler(String dir) {
        this(new File(dir));
    }
    

	@Override
	protected SimpleStaticFileHandler.IOWorker createIOWorker(HttpRequest request, HttpResponse response, HttpControl control) {
		return new SimpleStaticFileHandler.FileWorker(request, response, control);
	}

	protected class FileWorker extends IOWorker {
		private File file;

		private FileWorker(HttpRequest request, HttpResponse response, HttpControl control) {
			super(request.data("*").toString(), request, response, control);
		}

		@Override
        protected boolean exists() throws IOException {
			
            file = resolveFile(path);
            return file != null && file.exists();
        }

        @Override
        protected boolean isDirectory() throws IOException {
            return file.isDirectory();
        }

        @Override
        protected byte[] fileBytes() throws IOException {
            return file.isFile() ? read(file) : null;
        }

        @Override
        protected byte[] welcomeBytes() throws IOException {
            File welcome = new File(file, welcomeFileName);
            return welcome.isFile() ? read(welcome) : null;
        }

        @Override
        protected byte[] directoryListingBytes() throws IOException {
            if (!isDirectory()) {
                return null;
            }
            Iterable<FileEntry> files = ClassloaderResourceHelper.fileEntriesFor(file.listFiles());
            return directoryListingFormatter.formatFileListAsHtml(files);
        }

        private byte[] read(File file) throws IOException {
            return read((int) file.length(), new FileInputStream(file));
        }

        protected File resolveFile(String path) throws IOException {
            // Find file, relative to root
            File result = new File(dir, path).getCanonicalFile();

            // For security, check file really does exist under root.
            String fullPath = result.getPath();
            if (!fullPath.startsWith(dir.getCanonicalPath() + File.separator) && !fullPath.equals(dir.getCanonicalPath())) {
                // Prevent paths like http://foo/../../etc/passwd
                return null;
            }
            return result;
        }
	}
}