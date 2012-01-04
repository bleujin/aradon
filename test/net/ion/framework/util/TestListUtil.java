package net.ion.framework.util;

import java.util.List;

import junit.framework.TestCase;

public class TestListUtil extends TestCase {

	public void testConstructor() throws Exception {
		List<String> strs = ListUtil.newList();
		strs.add("abc");

		Debug.debug(strs);
	}
}
