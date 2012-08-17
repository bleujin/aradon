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

package net.ion.radon.core.server.netty.internal;

import static org.jboss.netty.channel.Channels.pipeline;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

import net.ion.framework.util.Debug;
import net.ion.radon.core.server.netty.NettyServerHelper;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;

public class HttpServerPipelineFactory implements ChannelPipelineFactory {

	private final NettyServerHelper helper;

	public HttpServerPipelineFactory(NettyServerHelper serverHelper) {
		this.helper = serverHelper;
	}

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpChunkAggregator(65536));

		pipeline.addLast("encoder", new HttpCompositeEncoder());
		// pipeline.addLast("encoder", new MyObjectEncoder());

		pipeline.addLast("streamer", new ChunkedWriteHandler());

		pipeline.addLast("handler", new HttpRequestHandler(this.helper));
		return pipeline;
	}

}

class MyObjectEncoder extends OneToOneEncoder {

	public MyObjectEncoder() {
		this(512);
	}

	public MyObjectEncoder(int estimatedLength) {
		if (estimatedLength < 0) {
			throw new IllegalArgumentException((new StringBuilder()).append("estimatedLength: ").append(estimatedLength).toString());
		} else {
			this.estimatedLength = estimatedLength;
			return;
		}
	}

	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		Debug.line(msg.getClass()) ;
		ChannelBufferOutputStream bout = new ChannelBufferOutputStream(ChannelBuffers.dynamicBuffer(estimatedLength, ctx.getChannel().getConfig().getBufferFactory()));
		bout.write(LENGTH_PLACEHOLDER);
		ObjectOutputStream oout = new CompactObjectOutputStream(bout);
		oout.writeObject(msg);
		oout.flush();
		oout.close();
		ChannelBuffer encoded = bout.buffer();
		encoded.setInt(0, encoded.writerIndex() - 4);
		return encoded;
	}

	private static final byte LENGTH_PLACEHOLDER[] = new byte[4];
	private final int estimatedLength;

}

class CompactObjectOutputStream extends ObjectOutputStream {

	CompactObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	protected void writeStreamHeader() throws IOException {
		writeByte(5);
	}

	protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
		Class clazz = desc.forClass();
		if (clazz.isPrimitive() || clazz.isArray()) {
			write(0);
			super.writeClassDescriptor(desc);
		} else {
			write(1);
			writeUTF(desc.getName());
		}
	}

	static final int TYPE_FAT_DESCRIPTOR = 0;
	static final int TYPE_THIN_DESCRIPTOR = 1;
}
