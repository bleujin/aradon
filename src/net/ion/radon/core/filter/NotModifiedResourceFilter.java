package net.ion.radon.core.filter;

import java.util.Date;

import net.ion.framework.util.HashFunction;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.IService;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.let.AbstractLet;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.CacheDirective;
import org.restlet.data.Status;
import org.restlet.data.Tag;
import org.restlet.representation.Representation;
import org.restlet.util.Series;

public class NotModifiedResourceFilter extends IRadonFilter{

	private final static int SECOND_PER_MONTH = 24*60*60*30 ;
	
	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		Representation result = response.getEntity() ;
		if (result == null || result == AbstractLet.EMPTY_REPRESENTATION)
			return IFilterResult.CONTINUE_RESULT;

		String tagValue = StringUtil.toString(Tag.parse(StringUtil.defaultIfEmpty(getHeader(request, "If-None-Match", true), "")));
		String modified = getHeader(request, "If-Modified-Since", true);
		final long hashValue = HashFunction.hashGeneral(request.getResourceRef().getIdentifier()) + result.getSize();
		String newTagValue = String.valueOf(hashValue);
		if (StringUtil.isNotBlank(tagValue) && StringUtil.isNotBlank(modified)) {
			try {
				Date lastModDate = DateUtils.parseDate(modified);
				Date resultModDate = result.getModificationDate();

				if (resultModDate != null && lastModDate.compareTo(resultModDate) <= 0 && tagValue.equals(newTagValue)) {
					response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
					result.setSize(0) ;
					return IFilterResult.CONTINUE_RESULT;
				}
			} catch (DateParseException ignore) {
			}
		} else {
			result.setTag(new Tag(String.valueOf(hashValue)));
		}
		// Debug.line('@', tagValue, modified);
		return IFilterResult.CONTINUE_RESULT;
		
	}

	
	@Override
	public IFilterResult preHandle(IService service, Request request, Response response) {
		request.getResourceRef().getPath() ;
		response.setCacheDirectives(ListUtil.toList(CacheDirective.publicInfo(), CacheDirective.sharedMaxAge(SECOND_PER_MONTH))) ;

		if (existCache(request)){
			response.setStatus(Status.REDIRECTION_NOT_MODIFIED) ;
			return IFilterResult.SKIP_RESULT ;
		}

		return IFilterResult.CONTINUE_RESULT;
	}

	private boolean existCache(Request request) {
		String tag = StringUtil.toString(Tag.parse(StringUtil.defaultIfEmpty(getHeader(request, "If-None-Match", true), ""))) ;
		return false;
	}

	
	protected String getHeader(Request request, String headerName, boolean ignoreCase) {
		final Series extraHeaders = (Series) request.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS);
		return extraHeaders.getFirstValue(headerName, ignoreCase);
	}

}
