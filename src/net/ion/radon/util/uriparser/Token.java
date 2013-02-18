package net.ion.radon.util.uriparser;

import java.util.Map;

public interface Token extends Expandable {

	/**
	 * The expression corresponding to this token.
	 * 
	 * @return The expression corresponding to this token.
	 */
	String expression();

	/**
	 * Indicates whether this token can be resolved.
	 * 
	 * <p>
	 * A resolvable token contains variables which can be resolved.
	 * 
	 * @return <code>true</code> if variables can be resolved from the specified pattern; <code>false</code> otherwise.
	 */
	boolean isResolvable();

	/**
	 * Resolves the specified expanded URI part for this token.
	 * 
	 * <p>
	 * The resolution process requires all variables referenced in the token to be mapped to the value that is present in the expanded URI data.
	 * 
	 * @param expanded
	 *            The part of the URI that correspond to an expanded version of the token.
	 * @param values
	 *            The variables mapped to their values as a result of resolution.
	 * 
	 * @return <code>true</code> this operation was successful; <code>false</code> otherwise.
	 */
	public boolean resolve(String expanded, Map<Variable, Object> values);

}
