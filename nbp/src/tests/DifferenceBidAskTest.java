package tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import nbp.DifferenceBidAsk;

public class DifferenceBidAskTest {

	@Test
	public void testSortMap() {

		Map<String, Double> m = new HashMap<String, Double>();
		m.put("ala", 2.3);
		m.put("kota", 3.2);
		m.put("ma", 3.0);
		m = DifferenceBidAsk.sortMap(m);
		Collection<Double> c = m.values();
		Iterator<Double> i = c.iterator();
		assertTrue((Double)i.next() == 2.3);
		assertTrue((Double)i.next() == 3.0);
		assertTrue((Double)i.next() == 3.2);
		Set<String> s = m.keySet();
		Iterator<String> iS = s.iterator();
		assertEquals(iS.next(), "ala");
		assertEquals(iS.next(), "ma");
		assertEquals(iS.next(), "kota");
	}
	
	@Test
	public void testGet() {
		Calendar date = Calendar.getInstance();
		date.setTime(Date.valueOf("2018-01-02"));
		Map<String, Double> m = DifferenceBidAsk.get(date);
		assertEquals(13, m.size());
		Set<String> keys = m.keySet();
		Iterator<String> it = keys.iterator();
		assertEquals("HUF", (String)it.next());
		assertEquals("JPY", (String)it.next());
		assertEquals("CZK", (String)it.next());
	}

}
