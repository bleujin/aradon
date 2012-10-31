package net.ion.bleujin.seminar.string;

import junit.framework.TestCase;
import net.ion.framework.file.HexUtil;
import net.ion.framework.util.Debug;

public class TestByte extends TestCase{

	
	public void testGetByte() throws Exception {
		String s = "한" ;
		
		Debug.line("default", HexUtil.toHex(s.getBytes())) ;
		Debug.line("euc-kr", HexUtil.toHex(s.getBytes("EUC-KR"))) ;
		Debug.line("ms949", HexUtil.toHex(s.getBytes("x-windows-949"))) ;
		Debug.line("utf-8", HexUtil.toHex(s.getBytes("UTF-8"))) ;
		Debug.line("utf-16", HexUtil.toHex(s.getBytes("UTF-16"))) ;
		Debug.line("utf-16le", HexUtil.toHex(s.getBytes("UTF-16LE"))) ;
		Debug.line("utf-16be", HexUtil.toHex(s.getBytes("UTF-16BE"))) ;
	}
}
