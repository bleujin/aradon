package net.ion.radon.core.annotation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.ion.framework.util.InfinityThread;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.util.AradonTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public class TestAsynResource {

	private Aradon aradon ;
	private AradonClient ac ;
	
	@Before
	public void setUp() throws Exception {
		this.aradon = AradonTester.create()
			.putAttribute("cname", "bleujin")
			.register("", "/async", AsyncLet.class).getAradon() ;
		this.ac = AradonClientFactory.create(aradon);
	}
	
	@After
	public void tearDown() throws Exception {
		ac.stop() ;
		aradon.stop() ;
	}
	
	@Test
	public void viewAsyncResource() throws Exception {
		aradon.toRadon().start().get() ;
		
		new InfinityThread().startNJoin() ;
	}

}

class AsyncLet implements IServiceLet {
	
	@Get
	public Representation viewProgress(@AnResponse InnerResponse response){
		
		return new OutputRepresentation(MediaType.TEXT_PLAIN) {
			
			@Override
			public void write(OutputStream output) throws IOException {
				int count = 10 ;
				Writer writer = new OutputStreamWriter(output) ;
				while(count-- > 0){

					writer.write("Hello" + count + "\n") ;
					
					try {
						Thread.sleep(200) ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				writer.close() ;
			}
		};
	}
}
