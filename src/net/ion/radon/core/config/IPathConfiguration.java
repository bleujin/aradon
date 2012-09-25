package net.ion.radon.core.config;

import java.util.List;

import net.ion.radon.core.EnumClass.IMatchMode;

public interface IPathConfiguration {

	public List<String> urlPatterns();

	public IMatchMode imatchMode();
}
