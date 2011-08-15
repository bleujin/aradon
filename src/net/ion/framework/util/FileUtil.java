package net.ion.framework.util;

import java.io.File;
import java.io.FileFilter;

public class FileUtil {
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

	public static FileFilter DIR_FILTER = new DirFilter() ;
	public static FileFilter PLUGIN_CONFIG_FILE_FILTER = new configFilter() ;
}
