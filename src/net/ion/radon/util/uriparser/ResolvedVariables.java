package net.ion.radon.util.uriparser;

import java.util.Set;

public interface ResolvedVariables {

	/**
	 * Returns the names of the variables which have been resolved.
	 * 
	 * @return The names of the variables which have been resolved.
	 */
	public Set<String> names();

	/**
	 * Returns the object corresponding to the specified variable name.
	 * 
	 * @param name
	 *            The name of the variable.
	 * 
	 * @return The object corresponding to the specified variable; may be <code>null</code>.
	 */
	public Object get(String name);

}