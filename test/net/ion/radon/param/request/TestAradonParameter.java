package net.ion.radon.param.request;

import java.util.Map;

import junit.framework.TestCase;
import net.ion.radon.param.MyParameter;

public class TestAradonParameter extends TestCase {

	String parameter = "{aradon:{query:\"¾ÆÀÌ¿Â\", sort:\"\", requestfilter:\"key:value AND range:[200 TO 300]\", page:{pageNo:3, listNum:10, screenCount:20}, color:[\"red\",\"white\",\"blue\"], param:{query:3, siteid:\"scat\"}}}";
	MyParameter jparam = MyParameter.create(parameter);
	String parameter2 = "{aradon.page:{pageNo:1, listNum:2}}";

	public void testParam() throws Exception {
		
		Map<String, Object> params = jparam.getMap("aradon.page");
		
		assertEquals("3", String.valueOf(params.get("pageNo")));

		params =  (Map<String, Object>) jparam.getParam("aradon/page") ;
		assertEquals("3", String.valueOf(params.get("pageNo")));
		
		Object param =  MyParameter.create(MyParameter.create(parameter2).getParam("aradon.page")).getParam("pageNo")  ;
		assertEquals("1", String.valueOf(param));
	}
	
	public void testGetMap() throws Exception {
		assertNotNull(MyParameter.create("{aradon.param:{a:22}}").getParam("aradon.param"));
	}
	
	// {aradon:[{name:'abcd', section:'sss', path:'/file', page:{pageNo:1, listNum:22, screenCount:1}, method:'get', resultpath:'result.rows[0].abc', resultformat:'json'}]}
	
	private String aparam = "{aradon:" + 
	"[{name:'abcd',  section:'sample', path:'/bulletin/bleujin', param:{p1:'abc', p2:'${abcd2}', p3:['red','green','white']}, page:{pageNo:1, listNum:22, screenCount:1}, method:'post', result:{format:'json'}}, " +
	" {name:'abcd2', section:'system', path:'/utils/sequence', 	 page:{pageNo:1, listNum:22, screenCount:1}, method:'get', result:{path:'result.rows[0].abc', type:'number', format:'json'}}]" +
		"}" ;
	
	public void testGetPath() throws Exception {
		ARadonRequest ars = RequestParser.parse(aparam) ;
		
		assertEquals("abc",  ars.getRequest("abcd").getValueAsString("param.p1"));
		assertEquals("result.rows[0].abc", ars.getRequest("abcd2").getValueAsString("result.path"));
		assertEquals("/utils/sequence",  ars.getRequest("abcd2").getValueAsString("path"));
	}
	
	public void testGetParam() throws Exception {
		ARadonRequest ars = RequestParser.parse(aparam) ;
		RequestParam param = ars.getRequest("abcd").getParam("p1");
		assertEquals("abc", String.valueOf(param.getValue()));
	}
	
}
