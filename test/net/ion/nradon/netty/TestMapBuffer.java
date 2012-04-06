package net.ion.nradon.netty;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class TestMapBuffer {

	@Test
	public void readFile() throws Exception{
		File file = new File("c:/setup/download/list.json");
		FileInputStream fis = new FileInputStream(file);
		FileChannel fc = fis.getChannel();

		MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());

		int limit = bb.limit(); // bytes in buffer
		
		for (int offset = 0; offset < limit; offset++) {
			byte b = bb.get(offset);
			System.out.println(b);
		}
	}
}
