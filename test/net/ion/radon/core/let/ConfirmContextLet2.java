package net.ion.radon.core.let;

import net.ion.radon.core.EnumClass.IZone;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class ConfirmContextLet2  extends DefaultLet{

	
	@Override
	public Representation myGet(){
		return new StringRepresentation(getContext().getAttributeObject(IZone.class.getCanonicalName(), IZone.class).toString())  ; 
	}
}