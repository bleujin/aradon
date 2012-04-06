package net.ion.radon.core;

import org.restlet.data.MediaType;
import org.restlet.routing.Router;


public class EnumClass {
	public enum Scope {
		Application, Session, Thread, Request ;

		public boolean isApplication() {
			return this == Application;
		}
	}
	

	public enum IfMethod {
		POST, PUT, GET, DELETE, ALL, OPTIONS;

		public boolean isAllow(IfMethod[] methods) {
			for (IfMethod ifMethod : methods) {
				if (ifMethod == ALL || ifMethod == this){
					return true ;
				}
			}
			return false;
		}
	}
	
	public enum IFormat {
		JSON, XML, HTML, OBJECT ;
		
		public MediaType getMediaType(){
			if (this == JSON) {
				return MediaType.APPLICATION_JSON ;
			} else if (this == XML) {
				return MediaType.APPLICATION_XML ;
			} else if (this == HTML) {
				return MediaType.TEXT_HTML ;
			} else {
				return MediaType.ALL ;
			}
		}
	}
	
	public enum IValueType {
		STRING, LONG, DOUBLE, DATE, BOOLEAN, BIGDECIMAL
	}
	
	
	public enum IfForwardName {
		ADD, LIST, EDIT, DELETE, VIEW, ALL;

		
		public boolean isAllow(IfForwardName[] forwards) {
			for (IfForwardName forward : forwards) {
				if (forward == ALL || forward == this){
					return true ;
				}
			}
			return false;
		}

	}

	public enum IMatchMode {
		STARTWITH, EQUALS;

		public static IMatchMode fromString(String matchMode) {
			return "STARTWITH".equalsIgnoreCase(matchMode) ? IMatchMode.STARTWITH : IMatchMode.EQUALS;
		}

		public int toRouterMode() {
			return this == IMatchMode.STARTWITH ? Router.MODE_BEST_MATCH : Router.MODE_FIRST_MATCH;
		}
	}
	
	public enum IConvertType {
		BEAN, MAP
	}
	
	public enum ILocation {
		PRE, AFTER
	}
	
	public enum IZone {
		Application {
			public IZone getChildZone(){
				return Section ;
			}
		}, Section{
			public IZone getChildZone(){
				return Path ;
			}
		}, Path{
			public IZone getChildZone(){
				return Request ;
			}
		}, Request {
			public IZone getChildZone(){
				return NONE ;
			}
		}, NONE {
			public IZone getChildZone(){
				return null ;
			}
		} ;
		
		public abstract IZone getChildZone() ;
	}
	
	
	public enum PlugInApply {
		IGNORE, OVERWRITE, MERGE;
		
		public static PlugInApply create(String key){
			for (PlugInApply apply : PlugInApply.values()){
				if (apply.toString().equalsIgnoreCase(key)){
					return apply ;
				}
			}
			return IGNORE ;
		}
	}
	
	
	public enum FilterLocation {
		PRE, AFTER ;
		
		public String getString(){
			return getClass().getCanonicalName() + "/" + toString() ;
		}
	}
}
