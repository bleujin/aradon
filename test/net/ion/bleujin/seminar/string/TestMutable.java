package net.ion.bleujin.seminar.string;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

public class TestMutable extends TestCase {

	
	public void testRun() throws Exception {
		new StringTest().execute() ;
		Debug.line() ;
		
		new StringBufferTest().execute() ;
	}
	
}


class StringTest{
	public void add(String s){
		s += " World" ;
	}
	public void replace(String s){
		s = " World" ;
	}
	public void toNull(String s){
		s = null ;
	}

	public void execute(){
		String s = "Hello" ;
		add(s) ;
		System.out.println(s) ;
		replace(s) ;
		System.out.println(s) ;
		toNull(s) ;
		System.out.println(s) ;
	}
}


class StringBufferTest{

	public void add(StringBuffer s){
		s.append(" World") ;
	}
	public void replace(StringBuffer s){
		s = new StringBuffer("World") ;
	}
	public void toNull(StringBuffer s){
		s = null ;
	}

	public void execute(){
		StringBuffer s = new StringBuffer("Hello") ;
		
		add(s) ;
		System.out.println(s) ;
		replace(s) ;
		System.out.println(s) ;
		toNull(s) ;
		System.out.println(s) ;
	}

}