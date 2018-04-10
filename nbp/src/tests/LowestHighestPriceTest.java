package tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;

import nbp.LowestHighestPriceCurrency;

public class LowestHighestPriceTest {

	@Test
	public void test() {
		Calendar date = Calendar.getInstance();
		date.setTime(Date.valueOf("2018-01-02"));
		Calendar[] c = LowestHighestPriceCurrency.get("USD", date, date);
		assertEquals(date, c[0]);
		assertEquals(date, c[1]);
		
		Calendar date2 = Calendar.getInstance();
		date2.setTime(Date.valueOf("2018-01-05"));
		c = LowestHighestPriceCurrency.get("USD", date, date2);
		date.add(Calendar.DATE, 1);
		assertEquals(date, c[0]);
		date.add(Calendar.DATE, 1);
		assertEquals(date, c[1]);
	}

}
