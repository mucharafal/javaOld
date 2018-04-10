package tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import nbp.LowestPrice;

public class LowestPriceTest {

	@Test
	public void testGetCurreciesFromDay() {
		Calendar date = Calendar.getInstance();
		date.setTime(Date.valueOf("2018-01-02"));
		Map<String, String> m = LowestPrice.getCurrenciesFromDay(date, "Bid");
		assertEquals(13, m.size());
		Set<String> k = m.keySet();
		//assertEquals("USD", (String)it.next());
		assertEquals("2.6970", (String)m.get("AUD"));
		assertEquals("3.4506", (String)m.get("USD"));
	}

	@Test
	public void testGet() {
		Calendar date = Calendar.getInstance();
		date.setTime(Date.valueOf("2018-01-02"));
		assertEquals("HUF", LowestPrice.get(date));
	}
}
