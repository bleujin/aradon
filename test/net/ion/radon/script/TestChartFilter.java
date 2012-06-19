package net.ion.radon.script;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.representation.Representation;

public class TestChartFilter extends IRadonFilter {

	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {

		AradonClient client = AradonClientFactory.create("http://chart.apis.google.com");
		IAradonRequest ir = client.createRequest("/chart?cht=p3&chs=500x250&chd=s:JMBJZ&chl=Open+Source|J2EE|Web+Service|Ajax|Other");
		Representation result = ir.get();

		response.setEntity(result) ;

//		
//		try {
//			response.setEntity(new InputRepresentation(getByteInputStream("http://chart.apis.google.com/chart?cht=p3&chs=500x250&chd=s:JMBJZ&chl=Open+Source|J2EE|Web+Service|Ajax|Other"))) ;
//		} catch (HttpException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
		
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private InputStream getByteInputStream(String httpURL) throws HttpException, IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpget = new HttpGet(httpURL);
			HttpResponse response = httpclient.execute(httpget);
			InputStream input = response.getEntity().getContent() ;
			InputStream result = new ByteArrayInputStream(IOUtils.toByteArray(input));
			input.close();
			return result;
		} catch (Exception ex) {
			throw new HttpException(ex.getMessage(), ex);
		} finally {
			httpclient.getConnectionManager().shutdown() ;
		}
	}
}
