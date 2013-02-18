package net.ion.radon.util.uriparser;

import java.util.List;

public interface TokenOperator extends Token {

	/**
	 * Returns the list of variables used in this token.
	 * 
	 * @return the list of variables.
	 */
	List<Variable> variables();

}