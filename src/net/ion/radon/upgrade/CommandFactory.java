package net.ion.radon.upgrade;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.ion.framework.configuration.ConfigurationException;
import net.ion.framework.parse.html.HTag;
import net.ion.framework.util.ListUtil;
import net.ion.radon.core.TreeContext;

import org.apache.commons.beanutils.MethodUtils;

public class CommandFactory {

	public static List<ICommand> parse(TreeContext context, HTag root) throws ConfigurationException {
		try {
			List<ICommand> result = ListUtil.newList();
			for (HTag tag : root.getChildren("command")) {
				result.add(parseCommand(context, tag));
			}
			return result;
		} catch (InstantiationException e) {
			throw new ConfigurationException(e);
		} catch (IllegalAccessException e) {
			throw new ConfigurationException(e);
		} catch (NoSuchMethodException e) {
			throw new ConfigurationException(e);
		} catch (InvocationTargetException e) {
			throw new ConfigurationException(e);
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException(e);
		} catch (IOException e) {
			throw new ConfigurationException(e);
		}
	}

	static ICommand parseCommand(TreeContext context, HTag tag) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {

		String clzName = tag.getAttributeValue("clz");
		Class<ICommand> clz = (Class<ICommand>) Class.forName(clzName);
		Object command = clz.newInstance();
		MethodUtils.invokeMethod(command, "init", new Object[] { context, tag });

		return (ICommand) command;
	}
}
