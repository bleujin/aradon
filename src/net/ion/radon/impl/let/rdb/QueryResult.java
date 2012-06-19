package net.ion.radon.impl.let.rdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.MapUtil;

import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;

public class QueryResult implements Serializable{

	private static final long serialVersionUID = -4834641443133779120L;
	private Map<String, Serializable> data = MapUtil.newMap() ;
	private QueryResult() {
	}
	
	public final static QueryResult create(Map<String, Object> data) {
		final QueryResult result = new QueryResult();
		
		for (Entry<String, Object> entry : data.entrySet()) {
			result.data.put(entry.getKey(), (Serializable)entry.getValue()) ;
		}
		return result ;
	}

	public Representation toRepresentation() throws IOException {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(bos);
		output.writeObject(this);

		return new InputRepresentation(new ByteArrayInputStream(bos.toByteArray()));
	}
	
	public Map<String, Serializable> getData(){
		return data ;
	}
	
}
