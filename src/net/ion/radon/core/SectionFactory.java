package net.ion.radon.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.impl.section.PluginConfig;

import org.apache.commons.configuration.ConfigurationException;

public class SectionFactory {

	static SectionService parse(Aradon aradon, String sectionName, XMLConfig sconfig) throws ConfigurationException, InstanceCreationException {

		String sectionType = StringUtil.defaultIfEmpty(sconfig.getTagName(), "section");

		String parseClassName = sconfig.getAttributeValue("parse");
		if (StringUtil.isNotBlank(parseClassName)) {
			try {
				Class clz = Class.forName(parseClassName) ;
				Method method = clz.getMethod("parse", Aradon.class, String.class, XMLConfig.class) ;
				SectionService section = (SectionService) method.invoke(null, aradon, sectionName, sconfig) ;
				return section ;
			} catch (ClassNotFoundException e) {
				throw new ConfigurationException(e) ;
			} catch (SecurityException e) {
				throw new ConfigurationException(e) ;
			} catch (NoSuchMethodException e) {
				throw new ConfigurationException(e) ;
			} catch (IllegalArgumentException e) {
				throw new ConfigurationException(e) ;
			} catch (IllegalAccessException e) {
				throw new ConfigurationException(e) ;
			} catch (InvocationTargetException e) {
				throw new ConfigurationException(e) ;
			}
		} else {
			if ("section".equalsIgnoreCase(sectionType)) {
				return createSection(aradon, sectionName, sconfig);
//			} else if ("notification".equalsIgnoreCase(sectionType)) {
//				return createNotification(aradon, sectionName, sconfig);
			} else {
				throw new IllegalArgumentException("unknown section type : " + sectionType);
			}
		}

	}

	synchronized static Map<BasePathInfo, XMLConfig> makePaths(SectionService section, TreeContext scontext, XMLConfig config) throws ConfigurationException, InstanceCreationException {
		try {
			Map<BasePathInfo, XMLConfig> result = new HashMap<BasePathInfo, XMLConfig>();

			FilterUtil.setFilter(section, config);
			AttributeUtil.load(section, config);

			for (XMLConfig pconfig : (List<XMLConfig>) config.children("path")) {
				String name = pconfig.getAttributeValue("name");
				List<String> urls = pconfig.getList("urls");
				String matchMode = pconfig.getString("urls[@matchmode]");
				String description = pconfig.getString("description");
				String handlerClassName = pconfig.getString("handler[@class]");

				final Class handlerClass = Class.forName(handlerClassName);

				BasePathInfo pinfo = PathInfo.create(name, StringUtil.join(urls, ","), IMatchMode.fromString(matchMode), description, handlerClass);
				result.put(pinfo, pconfig);
			}

			return result;
		} catch (ClassNotFoundException ex) {
			throw new ConfigurationException(ex.getMessage(), ex);
		}
	}

	static RouterSection createSection(Aradon aradon, String sectionName, XMLConfig config) throws InstanceCreationException, ConfigurationException {
		TreeContext sectionContext = aradon.getServiceContext().createChildContext();
		final RouterSection section = new RouterSection(aradon, sectionName, sectionContext);

		Map<BasePathInfo, XMLConfig> pathServices = SectionFactory.makePaths(section, sectionContext, config);

		for (Entry<BasePathInfo, XMLConfig> entry : pathServices.entrySet()) {
			TreeContext pathContext = sectionContext.createChildContext();
			final PathService pservice = PathService.create(section, pathContext, entry.getKey());
			FilterUtil.setFilter(pservice, entry.getValue());
			AttributeUtil.load(pservice, entry.getValue());

			section.attach(entry.getKey(), pservice);
		}

		return section;
	}

//	static NotificationSection createNotification(Aradon aradon, String sectionName, XMLConfig config) throws InstanceCreationException, ConfigurationException {
//		TreeContext sectionContext = aradon.getServiceContext().createChildContext();
//		String host = config.getAttributeValue("host");
//		int port = config.getInt("[@port]", 8090);
//		final NotificationSection section = new NotificationSection(aradon, sectionName, sectionContext, host, port);
//
//		Map<BasePathInfo, XMLConfig> pathServices = SectionFactory.makePaths(section, sectionContext, config);
//
//		for (Entry<BasePathInfo, XMLConfig> entry : pathServices.entrySet()) {
//			TreeContext pathContext = sectionContext.createChildContext();
//			final PathService pservice = PathService.create(section, pathContext, entry.getKey());
//			FilterUtil.setFilter(pservice, entry.getValue());
//			AttributeUtil.load(pservice, entry.getValue());
//
//			section.attach(entry.getKey(), pservice);
//		}
//
//		return section;
//	}
}
