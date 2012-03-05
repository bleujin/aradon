package net.ion.radon.core.representation;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;

import org.restlet.data.MediaType;
import org.restlet.data.Preference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;

public class PlainObjectConverter extends ConverterHelper {

	private static final VariantInfo VARIANT_OBJECT = new VariantInfo(MediaType.APPLICATION_JAVA_OBJECT);

	@Override
	public List<Class<?>> getObjectClasses(Variant source) {
		List<Class<?>> result = null;

		if (VARIANT_OBJECT.isCompatible(source)) {
			result = addObjectClass(result, JsonElement.class);
		}

		return result;
	}

	@Override
	public List<VariantInfo> getVariants(Class<?> source) {
		List<VariantInfo> result = null;

		if (JsonElement.class.isAssignableFrom(source)) {
			result = addVariant(result, VARIANT_OBJECT);
		}

		return result;
	}

	@Override
	public float score(Object source, Variant target, Resource resource) {
		if (target == null) {
			return 0.5F;
		} else if (MediaType.APPLICATION_JSON.isCompatible(target.getMediaType())) {
			return 1.0F;
		} else {
			return 0.5F;
		}
	}

	@Override
	public <T> float score(Representation source, Class<T> target, Resource resource) {
		if (target != null) {
			return 0.3F;
		}

		return 0.1F;
	}

	public <T> T toObject(Representation source, Class<T> target, Resource resource) throws IOException {
		if (source.getMediaType().equals(MediaType.APPLICATION_JSON)) {
			return JsonObject.fromString(source.getText()).getAsObject(target);
		} else if (source.getMediaType().equals(MediaType.APPLICATION_JAVA_OBJECT)){
			return target.cast(((ObjectRepresentation<Serializable>)source).getObject()) ;
		}
		return null;
	}

	@Override
	public Representation toRepresentation(Object source, Variant target, Resource resource) {
//		String jsonExpr = JsonParser.fromObject(source).getAsJsonObject().toString();
//		return new ObjectRepresentation(jsonExpr) ;
		return new JsonObjectRepresentation(JsonParser.fromObject(source).getAsJsonObject());
	}

	@Override
	public <T> void updatePreferences(List<Preference<MediaType>> preferences, Class<T> entity) {
		updatePreferences(preferences, MediaType.APPLICATION_JAVA_OBJECT, 1.0F);
	}

}