package net.ion.radon.core.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.NumberUtil;
import net.ion.framework.util.StringUtil;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;

public class XMLConfig {

	public final static XMLConfig BLANK = new XMLConfig(new XMLConfiguration());

	private HierarchicalConfiguration config;

	private XMLConfig(HierarchicalConfiguration config) {
		this.config = config;
	}

	public static XMLConfig create(File file) throws ConfigurationException {
		return create(new XMLConfiguration(file));
	}

	public static XMLConfig load(String configString) throws IOException, ConfigurationException {

		File tempDir = SystemUtils.getJavaIoTmpDir();
		if (tempDir.canRead() && tempDir.canWrite()) {

			File tfile = File.createTempFile("aradon", "config");
			FileUtils.writeStringToFile(tfile, configString);

			return XMLConfig.create(tfile);
		} else {
			throw new IllegalStateException("javaIoTmpDir:" + tempDir.getAbsolutePath() + " can't rw") ;
		}
	}

	private static XMLConfig create(HierarchicalConfiguration hconfig) {
		return new XMLConfig(hconfig);
	}

	public static XMLConfig create(String filePath) throws ConfigurationException {
		return create(new File(filePath));
	}

	public XMLConfig firstChild(String key) throws ConfigurationException {
		List<XMLConfig> children = children(key);
		if (children == null || children.size() == 0)
			return BLANK;
		return children(key).get(0);
	}

	public List<XMLConfig> children(String path) {

		List<HierarchicalConfiguration> childs = config.configurationsAt(path);

		List<XMLConfig> result = ListUtil.newList();
		for (HierarchicalConfiguration hconfig : childs) {
			result.add(create(hconfig));
		}

		return result;
	}

	public XMLConfig findChild(String path, String attrId, String attrValue) {
		List<XMLConfig> childs = children(path);

		for (XMLConfig xc : childs) {
			if (attrValue.equals(xc.getAttributeValue(attrId)))
				return xc;
		}

		return BLANK;
	}

	public String getString(String key) {
		return config.getString(key);
	}

	public String getString(String key, String dftString) {
		String result = getString(key);
		return StringUtil.isBlank(result) ? dftString : result;
	}

	public int getInt(String key, int dftValue) {
		String str = getString(key);
		if (!(StringUtil.isBlank(str)) && NumberUtil.isNumber(str)) {
			return Integer.parseInt(str);
		} else
			return dftValue;
	}

	public String getElementValue() {
		return getString("");
	}

	public String getAttributeValue(String attrId) {
		return config.getString("[@" + attrId + "]");
	}

	public List getList(String key) {
		return config.getList(key);
	}

	public boolean hasChild(String key) {
		return config.containsKey(key) || children(key).size() > 0;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		Iterator<String> keys = config.getKeys();
		while (keys.hasNext()) {
			final String key = keys.next();
			builder.append(key + ": " + config.getList(key) + "\n");
		}

		return builder.toString();
	}

	public List<String> childValueList(String key) {
		Object props = config.getProperty(key);
		if (props instanceof String)
			return ListUtil.toList((String) props);
		return (List<String>) config.getProperty(key);
	}

	public Map<String, String> childMap(String key, String attrId) {
		List<String> keys = config.getList(key + "[@" + attrId + "]");
		List<String> values = config.getList(key);

		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < keys.size(); i++) {
			result.put(keys.get(i), values.get(i));
		}
		return result;
	}

	public Object toXML() {
		Debug.line(config.getSubstitutor().getVariablePrefixMatcher());
		return null;
	}

	public String getTagName() {
		return config.getRoot().getName();
	}

	public HierarchicalConfiguration getBaseConfig() {
		return this.config;
	}

	public Map<String, String> getAttrMap() {
		Iterator<String> keys = config.getKeys();
		Map<String, String> result = new HashMap<String, String>();
		while (keys.hasNext()) {
			final String key = keys.next();
			if (!key.startsWith("[@"))
				continue;
			result.put(key, config.getString(key));
		}
		return result;
	}

	public boolean hasAttribute(String attrId) {
		return StringUtil.isNotBlank(getAttributeValue(attrId));
	}

}
