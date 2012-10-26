package net.ion.radon.core.config;

import java.util.Map;

import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;

import org.apache.commons.lang.SystemUtils;

public class SslParameter {

//	Parameter name 	Value type 	Default value 	Description
//	certAlgorithm 	String 		SunX509 		SSL certificate algorithm
//	keyPassword 	String 						SSL key password
//	keystorePassword 	String 					SSL keystore password
//	keystorePath 	String 		${user.home}/.keystore 	SSL keystore path
//	keystoreType 	String 		JKS 			SSL keystore type
//	sslProtocol 	String 		TLS 			SSL protocol
	
	private Map<String, String> properties ;
	SslParameter(Map<String, String> properties) {
		this.properties = properties ;
	}
	
	public final static SslParameter testCreate(){
		Map<String, String> properties = MapUtil.newMap() ;
		properties.put("keystorePath", "./resource/keystore/webbit.keystore") ;
		properties.put("keystorePassword", "webbit") ;
		
		return new SslParameter(properties) ;
	}
	
	public String certAlgorithm(){
		return StringUtil.defaultIfEmpty(properties.get("certAlgorithm"), "SunX509") ;
	}
	
	public String keyPassword(){
		return StringUtil.defaultIfEmpty(properties.get("keyPassword"), keystorePassword()) ;
	}
	
	public String keystorePassword(){
		return StringUtil.defaultIfEmpty(properties.get("keystorePassword"), "") ;
	}
	
	public String keystorePath(){
		return StringUtil.defaultIfEmpty(properties.get("keystorePath"), SystemUtils.USER_HOME + "/.keystore") ;
	}
	
	public String keystoreType(){
		return StringUtil.defaultIfEmpty(properties.get("keystoreType"), "JKS") ;
	}
	
	public String sslProtocol(){
		return StringUtil.defaultIfEmpty(properties.get("sslProtocol"), "TLS") ;
	}
	

}
