package net.ion.radon.param.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.rest.IRequest;
import net.ion.framework.rest.IResponse;
import net.ion.framework.rest.JSONFormater;
import net.ion.framework.util.Debug;
import net.ion.framework.util.MapUtil;
import net.ion.radon.core.PageBean;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.let.MapListRepresentationHandler;
import net.ion.radon.param.MyParameter;

import org.junit.Test;
import org.restlet.Request;

public class TestAradonParameter  {

	String parameter = "{aradon:{query:\"¾ÆÀÌ¿Â\", sort:\"\", requestfilter:\"key:value AND range:[200 TO 300]\", page:{pageNo:3, listNum:10, screenCount:20}, color:[\"red\",\"white\",\"blue\"], param:{query:3, siteid:\"scat\"}}}";
	MyParameter jparam = MyParameter.create(parameter);
	String parameter2 = "{aradon.page:{pageNo:1, listNum:2}}";

	@Test
	public void param() throws Exception {
		
		Map<String, ? extends Object> params = jparam.getMap("aradon.page");
		
		assertEquals("3", String.valueOf(params.get("pageNo")));

		params =  (Map<String, Object>) jparam.getParam("aradon/page") ;
		assertEquals("3", String.valueOf(params.get("pageNo")));
		
		MyParameter pageParam = MyParameter.create(parameter2);
		Object param =  MyParameter.create(pageParam.getParam("aradon.page")).getParam("pageNo")  ;
		assertEquals("1", String.valueOf(param));
	}
	
	@Test
	public void testPage() throws Exception {
		Map<String, Object> params = MapUtil.newMap() ;
		params.put("aradon.page.listNum", 15) ;
		params.put("aradon.page.pageNo", 2) ;
		
		MyParameter myparam = MyParameter.create(Collections.EMPTY_MAP);
		for (Entry<String, Object> entry : params.entrySet()) {
			myparam.addParam(entry.getKey(), entry.getValue());
		}
		if (! myparam.has(RadonAttributeKey.ARADON_PAGE))  {
			return ;
		}
		
		PageBean bean = PageBean.create(myparam.childParameter(RadonAttributeKey.ARADON_PAGE).getJson()) ;
		assertEquals(15, bean.getListNum()) ;
		assertEquals(2, bean.getPageNo()) ;
		assertEquals(10, bean.getScreenCount()) ;
	}
	
	
	
	
	@Test
	public void getMap() throws Exception {
		// assertNotNull(MyParameter.create("{aradon.param:{a:22}}").getParam("aradon.param"));
		// Debug.line(MyParameter.create("{aradon.param:{A:22}}").getParam("aradon.param")) ;
		MyParameter param = MyParameter.create("{aradon.page.listNum:20, aradon.page.pageNo:2}");
		Map map = (Map) param.getParam("aradon.page") ;
		assertEquals(20L, map.get("listnum")) ; 
		assertEquals(2L, map.get("pageNo")) ; 
		
		Debug.line(param.childParameter("un").toBean(PageBean.class)) ;
	}
	
	// {aradon:[{name:'abcd', section:'sss', path:'/file', page:{pageNo:1, listNum:22, screenCount:1}, method:'get', resultpath:'result.rows[0].abc', resultformat:'json'}]}
	
	private String aparam = "{aradon:" + 
	"[{name:'abcd',  section:'sample', path:'/bulletin/bleujin', param:{p1:'abc', p2:'${abcd2}', p3:['red','green','white']}, page:{pageNo:1, listNum:22, screenCount:1}, method:'post', result:{format:'json'}}, " +
	" {name:'abcd2', section:'system', path:'/utils/sequence', 	 page:{pageNo:1, listNum:22, screenCount:1}, method:'get', result:{path:'result.rows[0].abc', type:'number', format:'json'}}]" +
		"}" ;
	
	@Test
	public void getPath() throws Exception {
		AradonRequest ars = RequestParser.parse(aparam) ;
		
		assertEquals("abc",  ars.getRequest("abcd").getValueAsString("param.p1"));
		assertEquals("result.rows[0].abc", ars.getRequest("abcd2").getValueAsString("result.path"));
		assertEquals("/utils/sequence",  ars.getRequest("abcd2").getValueAsString("path"));
	}
	
	@Test
	public void getParam() throws Exception {
		AradonRequest ars = RequestParser.parse(aparam) ;
		RequestParam param = ars.getRequest("abcd").getParam("p1");
		assertEquals("abc", String.valueOf(param.getValue()));
	}
	
}
