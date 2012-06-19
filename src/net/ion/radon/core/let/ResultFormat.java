package net.ion.radon.core.let;

import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.IFormat;

public class ResultFormat {

	private IFormat iformat ;
	private String templateId ;
	private ResultFormat(IFormat iformat, String templateId) {
		this.iformat = iformat ;
		this.templateId = StringUtil.defaultIfEmpty(templateId, "") ;
	}

	public static ResultFormat create(IFormat iformat, String templateId) {
		return new ResultFormat(iformat, templateId) ;
	}

	public IFormat getFormat() {
		return iformat;
	}

	public String getTemplateId() {
		return templateId ;
	}

	public String toStringExpression(){
		return iformat.toString() + "." + templateId ;
	}
	
	public String toString(){
		return toStringExpression() ;
	}
	
}
