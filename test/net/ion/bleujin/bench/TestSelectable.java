package net.ion.bleujin.bench;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.restlet.engine.io.NbChannelInputStream;
import org.restlet.representation.AppendableRepresentation;
import org.restlet.representation.InputRepresentation;

public class TestSelectable extends TestCase{

	
	public void testname() throws Exception {
		Debug.debug(new InputRepresentation(new ByteArrayInputStream(new byte[0])).isSelectable()) ;
		Debug.debug(new AppendableRepresentation("").isSelectable() ) ;
		
		Debug.debug(new InputRepresentation(new NbChannelInputStream(new ByteChannel() {
			
			public int write(ByteBuffer arg0) throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean isOpen() {
				return false;
			}
			
			public void close() throws IOException {
				
			}
			
			public int read(ByteBuffer arg0) throws IOException {
				return 0;
			}
		})).isSelectable()) ;
	}
}
