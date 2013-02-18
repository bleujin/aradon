package net.ion.radon.util.uriparser;

import java.util.regex.Pattern;

public interface Matchable {

	/**
	 * Indicates whether this token matches the specified part of a URL.
	 * 
	 * @param part
	 *            The part of URL to test for matching.
	 * 
	 * @return <code>true</code> if it matches; <code>false</code> otherwise.
	 */
	boolean match(String part);

	/**
	 * Returns a regular expression pattern corresponding to this object.
	 * 
	 * @return The regular expression pattern corresponding to this object.
	 */
	Pattern pattern();

}
