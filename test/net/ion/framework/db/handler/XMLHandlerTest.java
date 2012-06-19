package net.ion.framework.db.handler;

import junit.framework.TestCase;

public class XMLHandlerTest extends TestCase{

	/*public void testXMLHandlerSimple() throws Exception {
		Rows vrows = makeRows();
		assertEquals(1, vrows.getRowCount()) ;
		
		ResultSetHandler rsh = new XMLHandler(new String[]{"name", "age", "date"}, new String[]{});
		XML root = (XML)vrows.toHandle(rsh) ;

		XML child = (XML)root.elements().nextElement() ;
		assertEquals("bleu", child.getAttribute("name")) ;
		assertEquals("12", child.getAttribute("age")) ;
		assertEquals(new Date(System.currentTimeMillis()).toString(), child.getAttribute("date")) ;
	}
	
	
	public void testJSONHandlerSimple() throws Exception {
		
		
		
		Rows vrows = makeRows();
		assertEquals(1, vrows.getRowCount()) ;
		
		ResultSetHandler rsh = new JSONHandler();
		JSONObject jroot = (JSONObject)vrows.toHandle(rsh) ;
		
		Debug.debug(jroot) ;

		JSONArray header = jroot.getJSONObject("RESULT").getJSONArray("HEADER") ;
		assertEquals("name", header.getString(0)) ;
		assertEquals("age", header.getString(1)) ;
		assertEquals("date", header.getString(2)) ;
		
		JSONArray rows = jroot.getJSONObject("RESULT").getJSONArray("ROWS") ;
		assertEquals("bleu", rows.getJSONArray(0).getString(0)) ;
		assertEquals("12", rows.getJSONArray(0).getString(1)) ;
		assertEquals(new Date(System.currentTimeMillis()).toString(), rows.getJSONArray(0).getString(2)) ;
		
		
	}
	

	private Rows makeRows() {
		FakeRows vrows = new FakeRows() ;
		vrows.addColumn("name", Types.VARCHAR) ;
		vrows.addColumn("age", Types.INTEGER) ;
		vrows.addColumn("date", Types.DATE) ;
		
		Map<String, Object> newRow = new HashMap<String, Object>() ;
		newRow.put("name", "bleu") ;
		newRow.put("age", 12) ;
		newRow.put("date", new Date(System.currentTimeMillis())) ;
		
		
		vrows.addRow(newRow) ;
		return vrows;
	}*/
}
