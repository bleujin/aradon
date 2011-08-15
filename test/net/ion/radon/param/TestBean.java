package net.ion.radon.param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TestBean implements Serializable{

	private String query;
	private List<String> color;
	private Map<String, Object> param;

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	public List<String> getColor() {
		return color;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public Map<String, Object> getParam() {
		return param;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this) ;
	}
}
