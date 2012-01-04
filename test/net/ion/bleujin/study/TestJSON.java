package net.ion.bleujin.study;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class TestJSON extends TestCase {

	public void testJSON() throws Exception {
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("name", "bleujin");
		dataModel.put("address", JSONObject.fromObject("{city:'seoul'}"));

		JSONObject j = JSONObject.fromObject(dataModel);
		Debug.line(j.toString(), j.get("address").getClass());

		Debug.debug(JSONUtils.mayBeJSON("{city:'seoul'}"));
	}
}
