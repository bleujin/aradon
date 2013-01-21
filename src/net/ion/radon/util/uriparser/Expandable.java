package net.ion.radon.util.uriparser;

import java.io.UnsupportedEncodingException;

public interface Expandable {

	/**
	 * Expands this object to produce a URI fragment as defined by the URI Template specifications.
	 * 
	 * @param parameters
	 *            The list of parameters and their values for substitution.
	 * 
	 * @return The expanded URI fragment
	 * @throws UnsupportedEncodingException 
	 */
	String expand(Parameters parameters) throws UnsupportedEncodingException;

}
