package tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import nbp.AvgGold;

public class AvgGoldTest {

	@Test
	public void testGetGoldPricesShortPeriod() {
		List<String> prices = AvgGold.getGoldPricesShortPeriod("2018-01-01", "2018-01-08");
		assertTrue(prices.size() == 5);
		assertEquals(prices.get(0), "145.10");
		assertEquals(prices.get(1), "145.72");
		assertEquals(prices.get(2), "146.36");
		assertEquals(prices.get(3), "145.68");
		assertEquals(prices.get(4), "146.06");
	}

	
	@Test
	public void  testGetGoldPricesLongPeriod() {
		Calendar b = Calendar.getInstance();
		b.set(2018, 0, 01);
		Calendar e = Calendar.getInstance();
		e.set(2018, 0, 8);
		List<String> l = AvgGold.getGoldPricesLongPeriod(b, e);
		assertEquals(l.size(), 5);
		b.set(2017, 0, 01);
		e.set(2018, 0, 01);
		l = AvgGold.getGoldPricesLongPeriod(b, e);
		assertEquals(l.size(), AvgGold.getGoldPricesShortPeriod("2017-01-01", "2017-03-01").size() +
				AvgGold.getGoldPricesShortPeriod("2017-03-02", "2017-06-01").size() + 
				AvgGold.getGoldPricesShortPeriod("2017-06-02", "2017-09-01").size() + 
				AvgGold.getGoldPricesShortPeriod("2017-09-02", "2018-01-01").size());
	}
	
	@Test
	public void testGetAvgGoldPrice() {
		Calendar b = Calendar.getInstance();
		b.set(2018, 0, 03);
		assertTrue(AvgGold.getAvgGoldPrice(b, b) == (145.72));
	}
}
