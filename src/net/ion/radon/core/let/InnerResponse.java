package net.ion.radon.core.let;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import net.ion.framework.rest.StdObject;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.RadonAttributeKey;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.AuthenticationInfo;
import org.restlet.data.CacheDirective;
import org.restlet.data.ChallengeRequest;
import org.restlet.data.CookieSetting;
import org.restlet.data.Dimension;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.RecipientInfo;
import org.restlet.data.Reference;
import org.restlet.data.ServerInfo;
import org.restlet.data.Status;
import org.restlet.data.Warning;
import org.restlet.engine.header.ContentType;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.util.Series;

public class InnerResponse extends Response {

	private Response inner ;
	private final InnerRequest innerRequest ;
	private StdObject sto ;
	private InnerResponse(Response response, InnerRequest innerRequest) {
		super(response.getRequest()) ;
		this.inner = response ;
		this.innerRequest = innerRequest ;
	}

	public static InnerResponse create(Response response, InnerRequest innerRequest) {
		final InnerResponse innerResponse = new InnerResponse(response, innerRequest);
		innerResponse.getHeaders().set(RadonAttributeKey.ARADON_VERSION_KEY, "0.5") ;
		return innerResponse;
	}
	
	public InnerRequest getInnerRequest(){
		return innerRequest ;
	}
	
	public Series<Header> getHeaders() {
		Series<Header> headers = (Series<Header>) inner.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS);
		if (headers == null) {
			headers = new Series(Header.class);
			inner.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, headers);
		}
		return headers;
	}
	
	public void setAllowedMethods(Set<Method> allowedMethods) {
		inner.setAllowedMethods(allowedMethods);
	}
	
	public void setChallengeRequests(List<ChallengeRequest> challengeRequests){
		inner.setChallengeRequests(challengeRequests) ;
	}
	
	public void setCookieSettings(Series<CookieSetting> cookieSettings) {
		inner.setCookieSettings(cookieSettings) ;
	}
	
	public void setDimensions(Set<Dimension> dimensions){
		inner.setDimensions(dimensions) ;
	}
	
	public void setProxyChallengeRequests(List<ChallengeRequest> proxyChallengeRequests){
		inner.setProxyChallengeRequests(proxyChallengeRequests) ;
	}
	
	public void abort(){
		inner.abort() ;
	}
	
	public void commit(){
		inner.commit() ;
	}
	public int getAge(){
		return inner.getAge() ;
	}
	
	public Set<Method> getAllowedMethods(){
		return inner.getAllowedMethods() ;
	}
	
	public AuthenticationInfo getAuthenticationInfo(){
		return inner.getAuthenticationInfo() ;
	}
	
	public List<ChallengeRequest> getChallengeRequests(){
		return inner.getChallengeRequests() ;
	}
	
	public Series<CookieSetting> getCookieSettings(){
		return inner.getCookieSettings() ;
	}
	
	public Set<Dimension> getDimensions(){
		return inner.getDimensions() ;
	}
	
	public Reference getLocationRef(){
		return inner.getLocationRef() ;
	}
	
	public List<ChallengeRequest> getProxyChallengeRequests(){
		return inner.getProxyChallengeRequests() ;
	}
	
	public Request getRequest(){
		return inner.getRequest() ;
	}
	
	public Date getRetryAfter() {
		return inner.getRetryAfter() ;
	}
	
	public ServerInfo getServerInfo(){
		return inner.getServerInfo() ;
	}
	
	public Status getStatus(){
		return inner.getStatus() ;
	}
	
	public boolean isAutoCommitting(){
		return inner.isAutoCommitting() ;
	}
	
	public boolean isCommitted(){
		return inner.isCommitted() ;
	}
	
	public boolean isConfidential(){
		return inner.isConfidential() ;
	}
	
	public void redirectPermanent(Reference targetRef){
		inner.redirectPermanent(targetRef) ;
	}
	
	public void redirectPermanent(String targetUri){
		inner.redirectPermanent(targetUri) ;
	}
	
	public void redirectSeeOther(Reference targetRef){
		inner.redirectSeeOther(targetRef) ;
	}
	
	public void redirectSeeOther(String targetUri){
		inner.redirectSeeOther(targetUri) ;
	}
	
	public void redirectTemporary(Reference targetRef){
		inner.redirectTemporary(targetRef) ;
	}
	public void redirectTemporary(String targetUri){
		inner.redirectTemporary(targetUri) ;
	}
	
	public void setAge(int age){
		inner.setAge(age) ;
	}
	
	public void setAuthenticationInfo(AuthenticationInfo authenticationInfo){
		inner.setAuthenticationInfo(authenticationInfo) ;
	}
	
	public void setAutoCommitting(boolean autoCommitting){
		inner.setAutoCommitting(autoCommitting) ;
	}
	
	public void setCommitted(boolean committed){
		inner.setCommitted(committed) ;
	}
	
	public void setLocationRef(Reference locationRef){
		inner.setLocationRef(locationRef) ;
	}
	public void setLocationRef(String locationUri){
		inner.setLocationRef(locationUri) ;
	}
	
	public void setRequest(Request request){
		inner.setRequest(request) ;
	}
	
	public void setRetryAfter(Date retryAfter){
		inner.setRetryAfter(retryAfter) ;
	}
	
	public void setServerInfo(ServerInfo serverInfo) {
		inner.setServerInfo(serverInfo) ;
	}
	
	public void setStatus(Status status){
		inner.setStatus(status) ;
	}
	
	public void setStatus(Status status, String message) {
		inner.setStatus(status, message) ;
	}
	
	public void setStatus(Status status, Throwable throwable){
		inner.setStatus(status, throwable) ;
	}
	
	public void setStatus(Status status, Throwable throwable, String message){
		inner.setStatus(status, throwable, message) ;
	}
	
	public String toString(){
		return inner.toString() ;
	}
	

	
	public void setAttributes(Map<String, Object> attributes) {
		inner.setAttributes(attributes) ;
	}
	
	public void setCacheDirectives(List cacheDirectives){
		inner.setCacheDirectives(cacheDirectives) ;
	}
	
	public void setRecipientsInfo(List recipientsInfo){
		inner.setRecipientsInfo(recipientsInfo) ;
	}
	
	public void setWarnings(List<Warning> warnings){
		inner.setWarnings(warnings) ;
	}
	
	public ConcurrentMap<String, Object> getAttributes(){
		return inner.getAttributes() ;
	}
	
	public List<CacheDirective> getCacheDirectives(){
		return inner.getCacheDirectives() ;
	}
	
	public Date getDate() {
		return inner.getDate() ;
	}
	
	public void setEntity(Representation entity){
		inner.setEntity(entity) ;
	}

	public Representation getEntity(){
		return inner.getEntity() ;
	}
	
	public String getEntityAsText(){
		return inner.getEntityAsText() ;
	}

	
	public Uniform getOnSent() {
		return inner.getOnSent() ;
	}
	
	public List<RecipientInfo> getRecipientsInfo(){
		return inner.getRecipientsInfo() ;
	}
	
	public List<Warning> getWarnings(){
		return inner.getWarnings();
	}
	
	public boolean isEntityAvailable(){
		return inner.isEntityAvailable() ;
	}
	
	public void release(){
		inner.release() ;
	}
	
	public void setDate(Date date){
		inner.setDate(date) ;
	}
	
	public void setEntity(String value, MediaType mediaType) {
		inner.setEntity(value, mediaType) ;
	}
	
	public void setOnSent(Uniform onSentCallback){
		inner.setOnSent(onSentCallback) ;
	}

	public void setHeader(String name, String value) {
		Series headers = getHeaders();
		headers.add(name, value);
	}

	void setResponseData(StdObject sto) {
		this.sto = sto ;
	}

	StdObject getStdObject() {
		return this.sto ;
	}

	public IService getPathService(Aradon aradon) {
		return getInnerRequest().getPathService(aradon) ;
	}

	public ContentType getContentType(){
		return new ContentType(getHeaders().getFirstValue("Content-Type")) ;
	}
	
    public long getLastModified() {
        String value = getHeaders().getFirstValue("Last-Modified");
        return getLastModified(value);
    }
    
	public static long getLastModified(String value) {
		try {
			if (StringUtil.isBlank(value)) return -1L ;
		    Date date = DateUtil.parseDate(value);
		    return date.getTime();
		} catch (DateParseException e) {
		    return -1L ;
		}
	}

    
}
