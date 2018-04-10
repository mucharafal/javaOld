package tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;

import nbp.MostAmplitude;

public class MostAmplitudeTest {

	@Test
	public void testMostAmplitudeCurrency() {
		Calendar date = Calendar.getInstance();
		date.setTime(Date.valueOf("2018-01-02"));
		Calendar date1 = Calendar.getInstance();
		date1.setTime(Date.valueOf("2018-01-03"));
		assertEquals("GBP", MostAmplitude.mostAmplitudeCurrency(date, date1));
		date = Calendar.getInstance();
		date.setTime(Date.valueOf("2015-01-14"));
		date1 = Calendar.getInstance();
		date1.setTime(Date.valueOf("2015-01-16"));
		assertEquals("CHF", MostAmplitude.mostAmplitudeCurrency(date, date1));
	
	}

}
