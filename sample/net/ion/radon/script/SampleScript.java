package net.ion.radon.script;

import java.io.File;

import junit.framework.TestCase;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.RandomUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.representation.JsonObjectRepresentation;
import net.ion.radon.core.script.ScriptFactory;
import net.ion.radon.util.AradonTester;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public class SampleScript extends TestCase{

	public void testChartFilter() throws Exception {
		Aradon aradon = AradonTester.create().register("chart", "/", DataLet.class).getAradon() ;
 		aradon.getChildService("chart").addAfterFilter(ScriptFactory.createGroovyFilter(new File("./groovy/net/ion/sample/ChartFilter.groovy")));

//		Response response = AradonClientFactory.create(aradon).createRequest("/chart/").handle(Method.GET) ;
//
//		FileOutputStream os = new FileOutputStream(new File("./resource/imsi/chart.png"));
//		IOUtil.copyNClose(response.getEntity().getStream(), os);
		aradon.startServer(9000) ;
		new InfinityThread().startNJoin() ;
	}

}


class DataLet extends AbstractServerResource {
	
	@Get
	public Representation getData(){
		JsonObject result = new JsonObject() ;
		for (int i : ListUtil.rangeNum(1, 7)) {
			result.put(i + "¿ù", RandomUtil.nextInt(100)) ;
		}
		
		return new JsonObjectRepresentation(result) ;
	}
	
}

