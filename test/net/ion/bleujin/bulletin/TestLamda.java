package net.ion.bleujin.bulletin;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

public class TestLamda extends TestCase {

	public void testLamda() throws Exception {
		Integer i = new Lamda("1111").getValue(Integer.class, 0);
		String s = new Lamda("1111").getValue(String.class, "22");

		Debug.line(new Lamda((Integer) null).getValue(Integer.class, 0).toString());
		Debug.line(new Lamda("1111").getValue(String.class, "22").toString());
		Debug.line(new Lamda(1111).getValue(String.class, "22").toString());
		// Debug.line(i) ;

	}
}

class Lamda {

	private Object obj;

	Lamda(Object obj) {
		this.obj = obj;
	}

	public <T> T getValue(Class<T> T) {
		return (T) obj;
	}

	public <T> T getValue(Class<T> T, T dftValue) {
		if (T.isInstance(obj))
			return (T) obj;
		return dftValue;
	}

}