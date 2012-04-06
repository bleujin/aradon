/**
 * Copyright 2005-2011 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package net.ion.radon.core.server.netty;

import java.io.File;
import javax.net.ssl.SSLContext;

import net.ion.radon.core.server.netty.internal.HttpsServerPipelineFactory;

import org.jboss.netty.channel.ChannelPipelineFactory;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.ext.ssl.SslContextFactory;
import org.restlet.ext.ssl.internal.SslUtils;

public class HttpsServerHelper extends NettyServerHelper {

	/**
	 * This is the SSL context.
	 */
	private SSLContext sslContext;

	/**
	 * Constructor.
	 * 
	 * @param server
	 *            The helped server.
	 */
	public HttpsServerHelper(Server server) {
		super(server);
		getProtocols().add(Protocol.HTTPS);
	}

	/**
	 * Returns the SSL certificate algorithm.
	 * 
	 * @return The SSL certificate algorithm.
	 */
	@Deprecated
	public String getCertAlgorithm() {
		return getHelpedParameters().getFirstValue("certAlgorithm", "SunX509");
	}

	/**
	 * Returns the SSL key password.
	 * 
	 * @return The SSL key password.
	 */
	@Deprecated
	public String getKeyPassword() {
		return getHelpedParameters().getFirstValue("keyPassword", "");
	}

	/**
	 * Returns the SSL keystore password.
	 * 
	 * @return The SSL keystore password.
	 */
	@Deprecated
	public String getKeystorePassword() {
		return getHelpedParameters().getFirstValue("keystorePassword", "");
	}

	/**
	 * Returns the SSL keystore path.
	 * 
	 * @return The SSL keystore path.
	 */
	@Deprecated
	public String getKeystorePath() {
		return getHelpedParameters().getFirstValue("keystorePath", System.getProperty("user.home") + File.separator + ".keystore");
	}

	/**
	 * Returns the SSL keystore type.
	 * 
	 * @return The SSL keystore type.
	 */
	@Deprecated
	public String getKeystoreType() {
		return getHelpedParameters().getFirstValue("keystoreType", "JKS");
	}

	@Override
	public ChannelPipelineFactory getPipelineFatory() {
		return new HttpsServerPipelineFactory(this, getSslContext());
	}

	/**
	 * Gets the SSL context used by this server.
	 * 
	 * @return this returns the SSL context.
	 */
	public SSLContext getSslContext() {
		return sslContext;
	}

	/**
	 * Returns the SSL keystore type.
	 * 
	 * @return The SSL keystore type.
	 */
	@Deprecated
	public String getSslProtocol() {
		return getHelpedParameters().getFirstValue("sslProtocol", "TLS");
	}

	/**
	 * Indicates if we require client certificate authentication.
	 * 
	 * @return True if we require client certificate authentication.
	 */
	public boolean isNeedClientAuthentication() {
		return Boolean.parseBoolean(getHelpedParameters().getFirstValue("needClientAuthentication", "false"));
	}

	/**
	 * Indicates if we would like client certificate authentication.
	 * 
	 * @return True if we would like client certificate authentication.
	 */
	public boolean isWantClientAuthentication() {
		return Boolean.parseBoolean(getHelpedParameters().getFirstValue("wantClientAuthentication", "false"));
	}

	/**
	 * Sets the SSL context for the server.
	 * 
	 * @param sslContext
	 *            the SSL context
	 */
	public void setSslContext(SSLContext sslContext) {
		this.sslContext = sslContext;
	}

	@Override
	public void start() throws Exception {
		// Initialize the SSL context
		SslContextFactory sslContextFactory = SslUtils.getSslContextFactory(this);

		sslContext = sslContextFactory.createSslContext();

		super.start();
	}

}
