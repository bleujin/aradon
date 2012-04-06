package net.ion.nradon.netty.codec.http.websocketx;

import java.util.List;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.StringUtil;

import org.apache.tools.ant.types.selectors.SizeSelector.ByteUnits;
import org.junit.Test;

public class TestWebSocketUtil {

	@Test
	public void randomBytes() throws Exception {
		List<String> store = ListUtil.newList() ;
		for (int i : ListUtil.rangeNum(10)) {
			store.add(getHexString(WebSocketUtil.randomBytes(16))) ;
		}
		
		
		for (int i : ListUtil.rangeNum(10)) {
			Debug.debug(store.get(i)) ;
		}
	}
	
	private static String getHexString(byte[] b) throws Exception {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
		}
}
