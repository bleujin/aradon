package net.ion.nradon.netty;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.StringHttpHandler;

import org.junit.Test;

public class TestFlashPolicyFile {

	@Test
	public void returnsCrossDomainXML() throws IOException,
			InterruptedException {
		Radon webServer = RadonConfiguration.newBuilder(59504).add(
				new StringHttpHandler("text/plain", "body")).startRadon();
		try {

			Socket client = new Socket(InetAddress.getLocalHost(), 59504);
			OutputStream out = client.getOutputStream();
			out.write(("<policy-file-request/>\0").getBytes("ASCII"));
			out.flush();
			InputStream in = client.getInputStream();
			String result = convertStreamToString(in);
			client.close();

			assertEquals(getPolicyFile("59504"), result);

		} finally {
			webServer.stop().join();
		}
	}
	
	@Test
	public void returnsCrossDomainXMLWithPublicPort() throws IOException,
			InterruptedException {
		
		Executor executor = Executors.newSingleThreadScheduledExecutor();
		InetSocketAddress address = new InetSocketAddress(59504);
		URI publicUri = URI.create("http://localhost:800/");
		
		Radon webServer = RadonConfiguration.newBuilder(executor, address, publicUri).add(
				new StringHttpHandler("text/plain", "body")).startRadon();
		try {
		
			Socket client = new Socket(InetAddress.getLocalHost(), 59504);
			OutputStream out = client.getOutputStream();
			out.write(("<policy-file-request/>\0").getBytes("ASCII"));
			out.flush();
			InputStream in = client.getInputStream();
			String result = convertStreamToString(in);
			client.close();
		
			assertEquals(getPolicyFile("800"), result);
		
		} finally {
			webServer.stop().join();
		}
	}
	
	@Test
	public void returnsCrossDomainXMLWithDefaultHTTPPublicPort() throws IOException,
			InterruptedException {
		
		Executor executor = Executors.newSingleThreadScheduledExecutor();
		InetSocketAddress address = new InetSocketAddress(59504);
		URI publicUri = URI.create("http://localhost/");
		
		Radon webServer = RadonConfiguration.newBuilder(executor, address, publicUri).add(
				new StringHttpHandler("text/plain", "body")).startRadon();
		try {
		
			Socket client = new Socket(InetAddress.getLocalHost(), 59504);
			OutputStream out = client.getOutputStream();
			out.write(("<policy-file-request/>\0").getBytes("ASCII"));
			out.flush();
			InputStream in = client.getInputStream();
			String result = convertStreamToString(in);
			client.close();
		
			assertEquals(getPolicyFile("80"), result);
		
		} finally {
			webServer.stop().join();
		}
	}
	
	@Test
	public void returnsCrossDomainXMLWithDefaultHTTPSPublicPort() throws IOException,
			InterruptedException {
		
		Executor executor = Executors.newSingleThreadScheduledExecutor();
		InetSocketAddress address = new InetSocketAddress(59504);
		URI publicUri = URI.create("https://localhost/");
		
		Radon webServer = RadonConfiguration.newBuilder(executor, address, publicUri).add(
				new StringHttpHandler("text/plain", "body")).startRadon();
		try {
		
			Socket client = new Socket(InetAddress.getLocalHost(), 59504);
			OutputStream out = client.getOutputStream();
			out.write(("<policy-file-request/>\0").getBytes("ASCII"));
			out.flush();
			InputStream in = client.getInputStream();
			String result = convertStreamToString(in);
			client.close();
		
			assertEquals(getPolicyFile("443"), result);
		
		} finally {
			webServer.stop().join();
		}
	}
	
	private String getPolicyFile(String port) {
		String policyFile = "<?xml version=\"1.0\"?>\r\n"
				+ "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n"
				+ "<cross-domain-policy>\r\n"
				+ "  <site-control permitted-cross-domain-policies=\"master-only\"/>\r\n"
				+ "  <allow-access-from domain=\"*\" to-ports=\"" + port + "\" />\r\n"
				+ "</cross-domain-policy>\r\n";
		return policyFile;		
	}

	private String convertStreamToString(InputStream is) {
		return new Scanner(is).useDelimiter("\\A").next();
	}

}
