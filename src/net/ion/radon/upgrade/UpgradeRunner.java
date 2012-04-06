package net.ion.radon.upgrade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.parse.html.HTag;
import net.ion.framework.util.DateUtil;
import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.PathMaker;

public class UpgradeRunner {

	private static String PREFIX = "upgrade_";

	private InstallContext context;
	private File configDir;

	private Map<String, List<ICommand>> stage = MapUtil.newMap();

	private UpgradeRunner(InstallContext context, File configDir) {
		this.context = context;
		this.configDir = configDir;
	}

	public static UpgradeRunner create(InstallContext context, File configDir) {
		return new UpgradeRunner(context, configDir);
	}

	public void upgrade() {
		upgrade(true) ;
	}

	public void upgrade(boolean renameFile) {
		init();
		for (Entry<String, List<ICommand>> entry : stage.entrySet()) {
			boolean isSuccess = true;
			for (ICommand cmd : entry.getValue()) {
				try {
					cmd.run();
				} catch (Exception e) {
					Debug.warn(cmd.getType() + " failed", e.getMessage());
					isSuccess = false;
				}
			}
			Debug.debug(entry.getKey() + " executed");
			if(renameFile) renameFile(isSuccess, entry.getKey());
		}
	}

	private void renameFile(boolean isSuccess, String fileName) {
		File srcFile = new File(fileName);
		String prefix =  isSuccess ? "success_" : "fail_" + DateUtil.calendarToString(Calendar.getInstance(Locale.getDefault()), "yyyyMMddHHmmss") + "_" ;
		String destFileName = PathMaker.getFilePath(srcFile.getParent(), prefix + srcFile.getName());
		srcFile.renameTo(new File(destFileName));
	}

	public void init() {
		if (configDir.exists() && configDir.canRead()) {
			File[] files = configDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.startsWith(PREFIX);
				}
			});

			stage.clear();

			for (File file : files) {

				String fileName = file.getAbsolutePath();
				Reader reader = null;
				try {
					reader = new InputStreamReader(new FileInputStream(file), "utf-8");
					HTag root = HTag.createGeneral(reader, "root");
					List<ICommand> cmds = CommandFactory.parse(context, root);

					stage.put(fileName, cmds);

				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					IOUtil.closeQuietly(reader);
				}

			}

		}

	}
}
