package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.w3c.dom.Document;

import nbp.AvgGold;
import nbp.Commons;
import nbp.GetDOC;

public class CommonsTest {

	@Test
	public void testGetDate() {
		Calendar d = Calendar.getInstance();
		d.setTime(Date.valueOf("2018-01-01"));
		assertEquals(Commons.getDate(d), "2018-01-01");
		d.setTime(Date.valueOf("2017-02-01"));
		assertEquals(Commons.getDate(d), "2017-02-01");
	}

	@Test
	public void testGetCurrencyPricesShortPeriod() {
		Map<Calendar, Double> prices = Commons.getCurrencyShortPeriod("CHF", "2018-01-02", "2018-01-03");
		assertTrue(prices.size() == 2);
		Calendar c = Calendar.getInstance();
		c.setTime(Date.valueOf("2018-01-02"));
		assertTrue((Double)prices.get(c) == 3.5567);
		c.add(Calendar.DATE, 1);
		assertTrue((Double)prices.get(c) == 3.5592);
	}

	
	@Test
	public void  testGetCurrencyLongPeriod() {
		Calendar b = Calendar.getInstance();
		b.set(2018, 0, 01);
		Calendar e = Calendar.getInstance();
		e.set(2018, 0, 8);
		Map<Calendar, Double> l = Commons.getCurrencyLongPeriod("USD", b, e);
		assertEquals(l.size(), 5);
		b.set(2017, 0, 01);
		e.set(2018, 0, 01);
		l = Commons.getCurrencyLongPeriod("USD", b, e);
		assertEquals(l.size(), Commons.getCurrencyShortPeriod("USD","2017-01-01", "2017-03-01").size() +
				Commons.getCurrencyShortPeriod("USD", "2017-03-02", "2017-06-01").size() + 
				Commons.getCurrencyShortPeriod("USD", "2017-06-02", "2017-09-01").size() + 
				Commons.getCurrencyShortPeriod("USD", "2017-09-02", "2018-01-01").size());
	}
	
	@Test
	public void testGetValueOfField() {
		Document document = null;
		try {
			document = GetDOC.getInstance().getDOC("http://api.nbp.pl/api/exchangerates/tables/c");
		} catch(IOException e) {
			System.out.println(e);
			fail();
		}
		List<String> l = Commons.getValueOfField(document, "Code");
		assertEquals(l.size(), 13);
		assertEquals(l.get(0), "USD");
		assertEquals(l.get(12), "XDR");
	}
}
