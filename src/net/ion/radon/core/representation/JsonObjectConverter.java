package net.ion.radon.core.representation;

import java.io.IOException;
import java.util.List;

import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonString;

import org.restlet.data.MediaType;
import org.restlet.data.Preference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;

public class JsonObjectConverter extends ConverterHelper {

	private static final VariantInfo VARIANT_JSON = new VariantInfo(MediaType.APPLICATION_JSON);

	@Override
	public List<Class<?>> getObjectClasses(Variant source) {
		List<Class<?>> result = null;

		if (VARIANT_JSON.isCompatible(source)) {
			result = addObjectClass(result, JsonElement.class);
		}

		return result;
	}

	@Override
	public List<VariantInfo> getVariants(Class<?> source) {
		List<VariantInfo> result = null;

		if (JsonElement.class.isAssignableFrom(source)) {
			result = addVariant(result, VARIANT_JSON);
		}

		return result;
	}

	@Override
	public float score(Object source, Variant target, Resource resource) {
		float result = -1.0F;

		if ((source instanceof JsonElement) || (source instanceof JsonString)) {
			if (target == null) {
				result = 0.3F;
			} else if (MediaType.APPLICATION_JSON.isCompatible(target.getMediaType())) {
				result = 0.4F;
			} else {
				result = 0.4F;
			}
		}

		return result;
	}

	@Override
	public <T> float score(Representation source, Class<T> target, Resource resource) {
		if (target != null) {
			if (JsonObjectRepresentation.class.isAssignableFrom(target)) {
				return 0.4F;
			} else if (JsonElement.class.isAssignableFrom(target)) {
				if (MediaType.APPLICATION_JSON.isCompatible(source.getMediaType())) {
					return 0.4F;
				} else {
					return 0.3F;
				}
			} else if (JsonString.class.isAssignableFrom(target)) {
				return 0.3F ;
			}
		}

		return -1.0F;
	}

	public <T> T toObject(Representation source, Class<T> target, Resource resource) throws IOException {
		if (source instanceof JsonObjectRepresentation){
			return ((JsonObjectRepresentation)source).getJsonObject().getAsObject(target) ;
		}
		return null;
	}

	@Override
	public Representation toRepresentation(Object source, Variant target, Resource resource) {
		Representation result = null;

		if (source instanceof JsonElement) {
			result = new JsonObjectRepresentation(source);
		}

		return result;

	}

	@Override
	public <T> void updatePreferences(List<Preference<MediaType>> preferences, Class<T> entity) {
		if (JsonElement.class.isAssignableFrom(entity) || JsonString.class.isAssignableFrom(entity)) {
			updatePreferences(preferences, MediaType.APPLICATION_JSON, 0.3F);
		}
	}


}