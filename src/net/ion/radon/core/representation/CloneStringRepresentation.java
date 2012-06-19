package net.ion.radon.core.representation;

import net.ion.framework.rest.CloneableRepresentation;

import org.restlet.data.CharacterSet;
import org.restlet.data.Language;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class CloneStringRepresentation extends StringRepresentation implements CloneableRepresentation{

	private volatile CharSequence text;

	public CloneStringRepresentation(CharSequence text) {
		this(text, MediaType.TEXT_PLAIN);
	}

	public CloneStringRepresentation(CharSequence text, Language language) {
		this(text, MediaType.TEXT_PLAIN, language);
	}

	public CloneStringRepresentation(CharSequence text, MediaType mediaType) {
		this(text, mediaType, null);
	}

	public CloneStringRepresentation(CharSequence text, MediaType mediaType, Language language) {
		this(text, mediaType, language, CharacterSet.UTF_8);
	}

	public CloneStringRepresentation(CharSequence text, MediaType mediaType, Language language, CharacterSet characterSet) {
		super(text, mediaType, language, characterSet);
	}

	public Representation cloneRepresentation() {
		final CloneStringRepresentation clone = new CloneStringRepresentation(this.text, getMediaType());
		clone.setCharacterSet(getCharacterSet()) ;
		clone.setLanguages(getLanguages()) ;
		return clone ;
	}
}
