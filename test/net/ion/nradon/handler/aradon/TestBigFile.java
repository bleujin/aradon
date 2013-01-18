package net.ion.nradon.handler.aradon;

import net.ion.framework.util.InfinityThread;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.DownloadLet;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;

public class TestBigFile{

	
	@Test
	public void bigFile() throws Exception {
		Aradon aradon  = AradonTester.create().register("file", "/{filename}", DownloadLet.class)
						.register("", "/hello", HelloWorldLet.class).getAradon() ;

		aradon.toRadon(8080).start().get() ;
		
		new InfinityThread().startNJoin() ;
	}
	
}
