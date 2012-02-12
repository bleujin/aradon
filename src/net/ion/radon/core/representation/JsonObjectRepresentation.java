package net.ion.radon.core.representation;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.ion.framework.parse.gson.GsonBuilder;
import net.ion.framework.parse.gson.JsonArray;
import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonNull;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;

import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.WriterRepresentation;

public class JsonObjectRepresentation extends WriterRepresentation {

	private boolean indenting;
	private JsonElement jsonElement = JsonNull.INSTANCE;
	private Representation jsonRepresentation;

	public JsonObjectRepresentation(JsonElement jsonElement) {
		super(MediaType.APPLICATION_JSON);
		init(jsonElement);
	}

	public JsonObjectRepresentation(Map<String, ? extends Object> map) {
		this(JsonParser.fromMap(map));
	}

	public JsonObjectRepresentation(Object bean) {
		this(JsonParser.fromObject(bean));
	}

	public JsonObjectRepresentation(Representation jsonRepresentation) {
		super((jsonRepresentation == null) ? null : jsonRepresentation.getMediaType());
		this.jsonRepresentation = jsonRepresentation;
	}

	public JsonObjectRepresentation(String jsonString) {
		super(MediaType.APPLICATION_JSON);
		setCharacterSet(CharacterSet.UTF_8);
		this.jsonRepresentation = new StringRepresentation(jsonString);
	}

	public JsonArray getJsonArray() {
		return this.jsonElement.getAsJsonArray();
	}

	public JsonObject getJsonObject() {
		return this.jsonElement.getAsJsonObject() ;
	}

	private String getJsonText() {
		if (isIndenting()){
			return new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement) ;
		} else {
			return jsonElement.toString() ;
		}
	}

	@Override
	public long getSize() {
		if (this.jsonRepresentation != null) {
			return this.jsonRepresentation.getSize();
		}
		return super.getSize();
	}

	private void init(JsonElement jsonElement) {
		setCharacterSet(CharacterSet.UTF_8);
		this.jsonElement = jsonElement;
		this.indenting = false;
	}

	public boolean isIndenting() {
		return this.indenting;
	}

	public JsonObjectRepresentation setIndenting(boolean indenting) {
		this.indenting = indenting;
		return this ;
	}


	@Override
	public void write(Writer writer) throws IOException {
		writer.write(getJsonText());
	}

}
