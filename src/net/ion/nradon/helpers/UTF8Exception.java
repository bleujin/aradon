package net.ion.nradon.helpers;

import net.ion.radon.core.except.AradonRuntimeException;

public class UTF8Exception extends AradonRuntimeException {
	private static final long serialVersionUID = 3599440257713569059L;

	public UTF8Exception(String reason) {
		super(reason);
	}
}
