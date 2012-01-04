package net.ion.nradon.handler;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;

public class StaticFileHandler extends AbstractResourceHandler {

	private final File dir;

	public StaticFileHandler(File dir, Executor ioThread) {
		super(ioThread);
		this.dir = dir;
	}

	public StaticFileHandler(String dir, Executor ioThread) {
		this(new File(dir), ioThread);
	}

	public StaticFileHandler(File dir) {
		this(dir, newFixedThreadPool(4));
	}

	public StaticFileHandler(String dir) {
		this(new File(dir));
	}

	@Override
	protected StaticFileHandler.IOWorker createIOWorker(HttpRequest request, HttpResponse response, HttpControl control) {
		return new StaticFileHandler.FileWorker(request, response, control);
	}

	protected class FileWorker extends IOWorker {
		private File file;

		private FileWorker(HttpRequest request, HttpResponse response, HttpControl control) {
			super(request.uri(), request, response, control);
		}

		@Override
		protected boolean exists() throws IOException {
			file = resolveFile(path);
			return file != null && file.exists();
		}

		@Override
		protected ByteBuffer fileBytes() throws IOException {
			return file.isFile() ? read(file) : null;
		}

		@Override
		protected ByteBuffer welcomeBytes() throws IOException {
			File welcome = new File(file, welcomeFileName);
			return welcome.isFile() ? read(welcome) : null;
		}

		private ByteBuffer read(File file) throws IOException {
			return read((int) file.length(), new FileInputStream(file));
		}

		private File resolveFile(String path) throws IOException {
			// Find file, relative to roo
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
