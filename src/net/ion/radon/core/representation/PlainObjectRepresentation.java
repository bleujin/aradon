package net.ion.radon.core.representation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;

public class PlainObjectRepresentation extends Representation{

	private Object source ;
	private Variant target ;
	private Resource resource ;
	
	public PlainObjectRepresentation(Object source, Variant target, Resource resource) {
		this.source = source ;
		this.target = target ;
		this.resource = resource ;
	}

	public static Representation create(Object source, Variant target, Resource resource) {
		return new PlainObjectRepresentation(source, target, resource);
	}

	
	@Override
	public ReadableByteChannel getChannel() throws IOException {
		throw new IllegalStateException(source.getClass() + " is not serializable" );
	}

	@Override
	public Reader getReader() throws IOException {
		throw new IllegalStateException(source.getClass() + " is not serializable" );
	}

	@Override
	public InputStream getStream() throws IOException {
		throw new IllegalStateException(source.getClass() + " is not serializable" );
	}

	@Override
	public void write(Writer writer) throws IOException {
		throw new IllegalStateException(source.getClass() + " is not serializable" );
	}

	@Override
	public void write(WritableByteChannel writablebytechannel) throws IOException {
		throw new IllegalStateException(source.getClass() + " is not serializable" );
	}

	@Override
	public void write(OutputStream outputstream) throws IOException {
		throw new IllegalStateException(source.getClass() + " is not serializable" );
	}

	public Object getSourceObject(){
		return source ;
	}

}
