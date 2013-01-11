package net.ion.nradon.handler;

public class StaticFile implements TemplateEngine {

	public final static TemplateEngine SELF = new StaticFile() ;
	public byte[] process(byte[] template, String templatePath, Object templateContext) throws RuntimeException {
		return template;
	}

}
