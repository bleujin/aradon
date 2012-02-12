package net.ion.radon.util;

import java.io.File;
import java.io.FileInputStream;

import junit.framework.TestCase;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.Debug;

import org.apache.commons.io.IOUtils;

public class TestJSONParse extends TestCase{
	
	public void testJSONRead() throws Exception {
		String str = IOUtils.toString(new FileInputStream(new File("c:/temp/xxxx.json"))) ;
		JsonObject obj = JsonParser.fromString(str).getAsJsonObject() ;
		
		Debug.debug(obj) ;
	}
}
