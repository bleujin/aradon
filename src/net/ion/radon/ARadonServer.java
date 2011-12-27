package net.ion.radon;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.framework.util.ZipUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.ClassAppender;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.service.GroovyService;
import net.ion.radon.core.service.GroovyShellService;

import org.apache.commons.collections.CollectionUtils;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;

public class AradonServer {
	private GroovyShellService gshell ;
	private final static class configFilter implements FileFilter {
		public boolean accept(File file) {
			return file.getName().startsWith("plugin") && file.getName().endsWith(".xml");
		}
	}

	private final static class DirFilter implements FileFilter {
		public boolean accept(File file) {
			return file.isDirectory();
		}
	}

	private final static class jarFilter implements FileFilter {
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith(".jar");
		}
	}

	private Options options;
	private Aradon aradon = null;
	public static FileFilter DIR_FILTER = new DirFilter();
	public static FileFilter PLUGIN_CONFIG_FILE_FILTER = new configFilter();
	public static FileFilter JAR_FILTER = new jarFilter();

	public AradonServer(Options options) {
		this.options = options;
	}

	public Aradon start() throws Exception {
		aradon = createAradon();
		aradon.init(options.getString("config", "resource/config/aradon-config.xml"));

		// add Plug in..
		loadPlugIn(aradon);

		// Now, let's start the component! Note that the HTTP server connector is also automatically started.
		aradon.startServer(options.getInt("port", 9002));
		launchAradonShell(aradon);

		final AradonServer as = this;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (!aradon.isStopped())
					as.stop();
			}
		});

		Debug.warn("AradonServer started : " + options.getInt("port", 9002));
		return aradon;
	}

	private Aradon testStart(Aradon aradon) throws Exception {
		aradon.startServer(options.getInt("port", 9002));
		launchAradonShell(aradon);
		return aradon;
	}

	public void stop() {
		if (gshell != null) gshell.destroy() ;
		if (aradon != null && (!aradon.isStarted())) {
			aradon.stop();
		}
	}

	private void launchAradonShell(final Aradon aradon) {
		XMLConfig rootConfig = aradon.getRootConfig();
		boolean autostart = Boolean.valueOf(rootConfig.getString("server-config.manage-port[@auto-start]", "false"));
		if (autostart == false) {
			aradon.getLogger().warning("aradon manage shell not loaded");
			return;
		}
		final int managePort = rootConfig.getInt("server-config.manage-port", 4567);
		aradon.getLogger().warning("aradon manage shell port :" + managePort);

		Thread shellThread = new Thread() {
			public void run() {
				gshell = new GroovyShellService(managePort);
				Map bindings = MapUtil.create("aradon", aradon);
				gshell.setBindings(bindings);
				gshell.launch() ;
			}
		};
		shellThread.start();
	}

	private Aradon createAradon() {
		Aradon aradon = new Aradon();
		aradon.getStatusService().setContactEmail("bleujin@i-on.net");
		aradon.getStatusService().setHomeRef(new Reference("http://localhost/"));

		aradon.getClients().add(Protocol.HTTP);
		aradon.getClients().add(Protocol.HTTPS);
		aradon.getClients().add(Protocol.FILE);
		return aradon;
	}

	void loadPlugIn(Aradon aradon) throws Exception {
		File plugInRootDir = new File("./plugin");
		if (!(plugInRootDir.exists() && plugInRootDir.isDirectory()))
			return;

		extractPluginZip(plugInRootDir);

		List<File> configFiles = ListUtil.newList();
		ClassAppender appender = ClassAppender.create();

		File[] plugDirs = plugInRootDir.listFiles(DIR_FILTER);
		for (File plugDir : plugDirs) {
			File[] xmlFiles = plugDir.listFiles(PLUGIN_CONFIG_FILE_FILTER);
			CollectionUtils.addAll(configFiles, xmlFiles);

			File[] jars = plugDir.listFiles(JAR_FILTER);
			appender.appendJar(jars);
		}
		appender.invokeURL();

		for (File xmlFile : configFiles) {
			aradon.appendPlugin(xmlFile, XMLConfig.create(xmlFile));
		}
	}

	private void extractPluginZip(File plugDir) throws IOException {
		ZipUtil zipper = new ZipUtil();
		File[] zipfiles = plugDir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".zip");
			}
		});

		for (File zip : zipfiles) {
			final String name = StringUtil.substringBeforeLast(zip.getName(), ".");
			File dir = new File(PathMaker.getFilePath(plugDir.getPath(), name));
			if (!dir.exists()) {
				zipper.unzip(zip, PathMaker.getFilePath(plugDir.getPath(), name));
			}
		}
	}

	public static void main(String[] args) throws Exception {
		AradonServer as = new AradonServer(new Options(args));
		Aradon aradon = as.start();

	}

}
