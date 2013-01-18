package net.ion.nradon.handler;

public interface TemplateEngine {
	String TEMPLATE_CONTEXT = "TEMPLATE_CONTEXT";
	
	byte[] process(byte[] template, String templatePath, Object templateContext) throws RuntimeException;
}
