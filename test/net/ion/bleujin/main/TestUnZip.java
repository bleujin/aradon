package net.ion.bleujin.main;

import java.io.File;
import java.io.FileFilter;

import junit.framework.TestCase;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.framework.util.ZipUtil;

public class TestUnZip extends TestCase {

	public void testUnZip() throws Exception {

		ZipUtil zipper = new ZipUtil();
		File plugDir = new File("./plugin");

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
}
