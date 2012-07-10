package net.ion.bleujin.study;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.Debug;

public class TestJSON extends TestCase {

	public void testJSON() throws Exception {
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("name", "bleujin");
		dataModel.put("address", JsonParser.fromString("{city:'seoul'}"));

		JsonObject j = JsonParser.fromMap(dataModel);
		Debug.line(j.toString(), j.get("address").getClass());

	}
}
