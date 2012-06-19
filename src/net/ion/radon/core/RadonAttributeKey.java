package net.ion.radon.core;

import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.impl.section.PathInfo;

import org.restlet.Context;
import org.restlet.data.Form;

public interface RadonAttributeKey {
	public final static String FORM_ATTRIBUTE_KEY = Form.class.getCanonicalName();
	public final static String REQUEST_CONTEXT = Context.class.getCanonicalName() ; // default context finder
	public final static String PATH_CONTEXT = Context.class.getCanonicalName() + "/path";
	public final static String IZONE_ATTRIBUTE_KEY = IZone.class.getCanonicalName();

	public final static String PATH_SERVICE_KEY = PathService.class.getCanonicalName() ;
	
//	public static final String PATH_ATTRIBUTE = PathInfo.class.getCanonicalName();
//	public static final String SECTION_PAHT_CONTEXTS = SectionService.class.getCanonicalName();
//	public static final String PRE_FILTER = IRadonFilter.class.getCanonicalName() + "/_prefilter";
//	public static final String AFTER_FILTER = IRadonFilter.class.getCanonicalName() + "/_afterfilter";
	
	
	
	public static final String ATTRIBUTE_HEADERS = "org.restlet.http.headers";
	public static final String HEADER_X_HTTP_METHOD_OVERRIDE = "X-HTTP-Method-Override";
	public static final String ARADON_RESULT_FORMAT = "aradon.result.format";
	public static final String ARADON_HTTP_METHOD = "aradon.result.method";
	public final static String ARADON_PARAMETER = "aradon.parameter";
	public final static String ARADON_PREFIX = "aradon.";
	public final static String ARADON_PAGE = "aradon.page";
	
	
	public final static String LET_CONTACT_EMAIL = "let.contact.email" ;
	public final static String LET_CONTACT_HELP_DOC = "let.contact.help.doc" ;
	public final static String ARADON_VERSION_KEY = "X-Aradon-Version" ;
}
