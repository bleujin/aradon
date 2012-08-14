package net.ion.radon.script;

import java.io.File;
import java.io.FileOutputStream;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.RandomUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.representation.JsonObjectRepresentation;
import net.ion.radon.core.script.ScriptFactory;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public class SampleScript {

	@Test
	public void testChartFilter() throws Exception {
		Aradon aradon = AradonTester.create().register("chart", "/", DataLet.class).getAradon() ;
 		aradon.getChildService("chart").getConfig().addAfterFilter(ScriptFactory.createGroovyFilter(new File("./groovy/net/ion/sample/ChartFilter.groovy")));

//		Response response = AradonClientFactory.create(aradon).createRequest("/chart/").handle(Method.GET) ;
//
//		FileOutputStream os = new FileOutputStream(new File("./resource/imsi/chart.png"));
//		IOUtil.copyNClose(response.getEntity().getStream(), os);
		aradon.startServer(9000) ;
		new InfinityThread().startNJoin() ;
	}

	
	@Test
	public void callGoogleChart() throws Exception {
		AradonClient client = AradonClientFactory.create("https://chart.googleapis.com", true) ; 
		IAradonRequest ir = client.createRequest("/chart") ;
		ir.addParameter("cht", "p3") ;
		ir.addParameter("chs", "500x250") ;
		String[] result = new String[]{"60","40"};
		ir.addParameter("chd", "s:" + StringUtil.join(result,"")) ;
		String label = "Hello|World";
		ir.addParameter("chl", label) ;
		
		Representation repr =  ir.get();
		File file = File.createTempFile("dd", "cc") ;
		IOUtil.copyNClose(repr.getStream(), new FileOutputStream(file)) ;
		Debug.line(file.getCanonicalPath()) ;
	}
}


class DataLet extends AbstractServerResource {
	
	@Get
	public Representation getData(){
		JsonObject result = new JsonObject() ;
		for (int i : ListUtil.rangeNum(1, 7)) {
			result.put(i + "한글", RandomUtil.nextInt(100)) ;
		}
		
		return new JsonObjectRepresentation(result) ;
	}
	
}

