package net.ion.bleujin.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.radon.impl.util.CsvReader;
import net.ion.radon.impl.util.CsvWriter;

public class CSVTest extends TestCase{

	public void testCSVWrite() throws Exception {
		
		FileWriter fw = new FileWriter(new File("./imsi/text.csv"), true) ;
		CsvWriter writer = new CsvWriter(fw) ;
		
		writer.writeLine(new String[]{"name", "path", "ddd"}) ;
		writer.close() ;
	}
	
	public void testRead() throws Exception {
		FileReader fr = new FileReader(new File("./imsi/text.csv")) ;
		CsvReader reader = new CsvReader(fr) ;
		
		
		String[] line = null ;
		while((line = reader.readLine()) != null) {
			Debug.debug(line);
		}
		reader.close() ;
	}
}
