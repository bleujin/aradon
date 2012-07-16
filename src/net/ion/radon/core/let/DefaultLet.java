package net.ion.radon.core.let;

import java.util.Collections;
import java.util.Set;

import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;

@Deprecated
public abstract class DefaultLet extends AbstractLet {

	private Set<Method> disAllow_Method;
	public static final String ATTRIBUTE_HEADERS = "org.restlet.http.headers";
	public static final String HEADER_X_HTTP_METHOD_OVERRIDE = "X-HTTP-Method-Override";
	public static final String ARADON_RESULT_FORMAT = "aradon.result.format";
	public static final String ARADON_HTTP_METHOD = "aradon.result.method";
	public final static String ARADON_PARAMETER = "aradon.parameter";

	public DefaultLet() {
		this(Collections.EMPTY_SET, MediaType.ALL);
	}

	public DefaultLet(MediaType allowMediaType) {
		this(Collections.EMPTY_SET, allowMediaType);
	}

	public DefaultLet(Set<Method> disAllowMethods, MediaType allowMediaType) {
		super();
		this.disAllow_Method = disAllowMethods;
		getVariants().add(new Variant(allowMediaType));
		setConditional(false);
		setNegotiated(false);
	}

	protected Representation myOptions() throws Exception {
		return EMPTY_REPRESENTATION;
		// return options();
	}

	protected Representation myPut(Representation entity) throws Exception {
		return notImpl(entity);
	}

	protected Representation myHead() throws Exception {
		return myGet();
	}

	protected Representation myDelete() throws Exception {
		return notImpl();
	}

	protected Representation myPost(Representation entity) throws Exception {
		return notImpl(entity);
	}

	protected Representation myGet() throws Exception {
		return notImpl();
	}



}
