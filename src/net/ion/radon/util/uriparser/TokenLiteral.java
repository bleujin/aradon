package net.ion.radon.util.uriparser;

import java.util.Map;
import java.util.regex.Pattern;

public class TokenLiteral extends TokenBase implements Token, Matchable {

	/**
	 * Creates a new literal text token.
	 * 
	 * @param text
	 *            The text corresponding to this URI token.
	 * 
	 * @throws NullPointerException
	 *             If the specified text is <code>null</code>.
	 */
	public TokenLiteral(String text) throws NullPointerException {
		super(text);
	}

	/**
	 * {@inheritDoc}
	 */
	public String expand(Parameters parameters) {
		return this.expression();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean match(String part) {
		return this.expression().equals(part);
	}

	/**
	 * {@inheritDoc}
	 */
	public Pattern pattern() {
		return Pattern.compile(Pattern.quote(expression()));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * By definition, no variable in this token. This method does nothing and always returns <code>true</code>.
	 */
	public boolean resolve(String expanded, Map<Variable, Object> values) {
		// nothing to resolve - the operation is always successful.
		return true;
	}

}
