package net.ion.radon.script;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import net.ion.framework.util.IOUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

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
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
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

		HttpClient httpclient = new HttpClient();
		GetMethod httpget = new GetMethod(httpURL);
		try {

			httpclient.executeMethod(httpget);

			InputStream input = httpget.getResponseBodyAsStream();
			InputStream result = new ByteArrayInputStream(IOUtils.toByteArray(input));
			input.close();
			return result;

		} catch (Exception ex) {
			throw new HttpException(ex.getMessage(), ex);
		} finally {
			httpget.releaseConnection();
		}
	}
}
