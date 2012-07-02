/*
 * Copyright 2011 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package net.ion.nradon.netty.codec.http.websocketx;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

/**
 * Web Socket Frame for closing the connection
 */
public class CloseWebSocketFrame extends WebSocketFrame {

	/**
	 * Creates a new empty close frame.
	 */
	public CloseWebSocketFrame() {
		setBinaryData(ChannelBuffers.EMPTY_BUFFER);
	}

	/**
	 * Creates a new close frame
	 * 
	 * @param finalFragment
	 *            flag indicating if this frame is the final fragment
	 * @param rsv
	 *            reserved bits used for protocol extensions
	 */
	public CloseWebSocketFrame(boolean finalFragment, int rsv) {
		setFinalFragment(finalFragment);
		setRsv(rsv);
	}

	public int getStatusCode() {
		ChannelBuffer binaryData = this.getBinaryData();
		if (binaryData == null || binaryData.capacity() == 0) {
			return -1;
		}

		binaryData.readerIndex(0);
		int statusCode = binaryData.readShort();
		binaryData.readerIndex(0);

		return statusCode;
	}

	public String getReasonText() {
		ChannelBuffer binaryData = this.getBinaryData();
		if (binaryData == null || binaryData.capacity() <= 2) {
			return "";
		}

		binaryData.readerIndex(2);
		String reasonText = binaryData.toString(CharsetUtil.UTF_8);
		binaryData.readerIndex(0);

		return reasonText;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
