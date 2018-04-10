package nbp;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;

public class AvgGold {
	/**
	 * Function return average price of gold for given period
	 * @param begin		type Calendar, begin of period
	 * @param end		type Calendar, end of period
	 * @return			type Double, average price of gold in given period 
	 */
	public static double getAvgGoldPrice(Calendar begin, Calendar end) {
		double sum = 0;
		List <String> prices = getGoldPricesLongPeriod(begin, end);
		Iterator<String> it = prices.iterator();
		while(it.hasNext()) {
			sum += Double.parseDouble(it.next());
		}
		return (sum / prices.size());
	}
	/**
	 * Return list of prices gold for given period
	 * @param begin		begin of period
	 * @param end		end of period
	 * @return			list of prices
	 */
	public static List<String> getGoldPricesLongPeriod(Calendar begin, Calendar end){
		long t = ChronoUnit.DAYS.between(begin.toInstant(), end.toInstant());
		List <String> res = new LinkedList<String>();
		Calendar start = begin;
		Calendar stop = null;
		while(t > 93) {
			stop = (Calendar)start.clone();
			stop.add(Calendar.DATE, 93);
			
			res.addAll(getGoldPricesShortPeriod(Commons.getDate(start), Commons.getDate(stop)));
			
			start = stop;
			start.add(Calendar.DATE, 1);
			t = ChronoUnit.DAYS.between(start.toInstant(), end.toInstant());
		}
		
		res.addAll(getGoldPricesShortPeriod(Commons.getDate(start), Commons.getDate(end)));
		
		return res;
	}
	/**
	 * Return list of prices gold for given period, but it can't be longer than 93 days
	 * In other case an exception will be throw
	 * If longer period is needed, use getGoldPricesLongPeriod
	 * @param begin		begin of period
	 * @param end		end of period
	 * @return			list of prices
	 */
	public static List<String> getGoldPricesShortPeriod(String begin, String end){
		String address = ("http://api.nbp.pl/api/cenyzlota/" + begin + "/" + end).toLowerCase();
		Document document = null;
		try {
			document = GetDOC.getInstance().getDOC(address);
		} catch(IOException e) {
			System.err.println(e);
			throw new RuntimeException("Problem with web address, check std::err");
		}
		List <String> res = Commons.getValueOfField(document, "Cena");
		return res;
	}
}
