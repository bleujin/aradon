package net.ion.radon.core.representation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonObject;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Preference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.engine.converter.DefaultConverter;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;

public class PlainObjectConverter extends ConverterHelper {

	private DefaultConverter dftConverter = new DefaultConverter() ;

	@Override
	public List<Class<?>> getObjectClasses(Variant source) {
		return dftConverter.getObjectClasses(source) ;
	}

	@Override
	public List<VariantInfo> getVariants(Class<?> source) {
		return dftConverter.getVariants(source) ;
	}

	@Override
	public float score(Object source, Variant target, Resource resource) {
		return Math.max(dftConverter.score(source, target, resource), 0.6F) ;
	}

	@Override
	public <T> float score(Representation source, Class<T> target, Resource resource) {
		return Math.max(dftConverter.score(source, target, resource), 0.6F) ;
	}


	@Override
	public <T> void updatePreferences(List<Preference<MediaType>> preferences, Class<T> entity) {
		if (Form.class.isAssignableFrom(entity)) {
			updatePreferences(preferences, MediaType.APPLICATION_WWW_FORM, 1.0F);
		} else if (Serializable.class.isAssignableFrom(entity)) {
			updatePreferences(preferences, MediaType.APPLICATION_JAVA_OBJECT, 1.0F);
			updatePreferences(preferences, MediaType.APPLICATION_JAVA_OBJECT_XML, 1.0F);
		} else if (String.class.isAssignableFrom(entity) || Reader.class.isAssignableFrom(entity)) {
			updatePreferences(preferences, MediaType.TEXT_PLAIN, 1.0F);
			updatePreferences(preferences, MediaType.TEXT_ALL, 0.5F);
		} else if (InputStream.class.isAssignableFrom(entity) || ReadableByteChannel.class.isAssignableFrom(entity)) {
			updatePreferences(preferences, MediaType.APPLICATION_OCTET_STREAM, 1.0F);
			updatePreferences(preferences, MediaType.APPLICATION_ALL, 0.5F);
		} else {
			updatePreferences(preferences, MediaType.APPLICATION_JAVA_OBJECT, 0.1F);
		}
	}
	public <T> T toObject(Representation source, Class<T> target, Resource resource) throws IOException {
		T result = dftConverter.toObject(source, target, resource) ;
		
		return result ;
	}

	@Override
	public Representation toRepresentation(Object source, Variant target, Resource resource) {
		return PlainObjectRepresentation.create(source, target, resource);
	}


}