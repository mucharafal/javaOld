package nbp;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
/**
 * Class contain static methods used by other classes
 * @author rafal
 *
 */
public class Commons {

	/**
	 * Process date in c to format YYYY-MM-DD
	 * @param c		date
	 * @return
	 */
	public static String getDate(Calendar c) {
		String date = c.get(Calendar.YEAR) + "-";
		int field = Calendar.MONTH;
		if(c.get(field) < 9) {
			date += "0" + (c.get(field) + 1) + "-";
		} else {
			date += (c.get(field) + 1) + "-";
		}
		field = Calendar.DAY_OF_MONTH;
		if(c.get(field) < 10) {
			date += "0" + (c.get(field));
		} else {
			date += (c.get(field));
		}
		return date;
	}
	/**
	 * 
	 * @param document
	 * @param fieldName
	 * @return
	 * @throws IOException
	 */
	public static List<String> getValueOfField(Document document, String fieldName) {
		List <String> res = new LinkedList<String>();
		List <Node> prices = DOCProcessing.getAllNodesWithType(document, fieldName);
		Iterator<Node> it = prices.iterator();
		while(it.hasNext()) {
			res.add(it.next().getFirstChild().getNodeValue());
		}
		if(prices.isEmpty()) {
			System.out.println("Nulle, wszêdzie nulle");
			throw new RuntimeException("nbp.Commons.getValueOfField: problem with " + fieldName + " in DOC");
		}
		return res;
	}
	/**
	 * Return map <Day, Price> for given currency in given period
	 * @param code		code of currency i.e. "USD"
	 * @param begin		begin of period
	 * @param end		end of period
	 * @return			
	 */
	public static Map<Calendar, Double> getCurrencyLongPeriod(String code, Calendar begin, Calendar end){
		long t = ChronoUnit.DAYS.between(begin.toInstant(), end.toInstant());
		Map <Calendar, Double> res = new LinkedHashMap<Calendar, Double>();
		Calendar start = begin;
		Calendar stop = null;
		while(t > 93) {
			stop = (Calendar)start.clone();
			stop.add(Calendar.DATE, 93);
			
			res.putAll(getCurrencyShortPeriod(code, Commons.getDate(start), Commons.getDate(stop)));
			
			start = stop;
			start.add(Calendar.DATE, 1);
			t = ChronoUnit.DAYS.between(start.toInstant(), end.toInstant());
		}
		
		res.putAll(getCurrencyShortPeriod(code, Commons.getDate(start), Commons.getDate(end)));
		
		return res;
	}
	/**
	 * Return map <Day, Price> for given currency in given period
	 * Important: period can't be longer than 93 days, in other case an Exception will be thrown
	 * @param code		code of currency i.e. "USD"
	 * @param begin		begin of period
	 * @param end		end of period
	 * @return			
	 */
	public static Map<Calendar, Double> getCurrencyShortPeriod(String code, String begin, String end){
		String address = ("http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + begin + "/" + end).toLowerCase();
		Document document = null;
		try {
			document = GetDOC.getInstance().getDOC(address);
		} catch(IOException e) {
			System.err.println(e);
			throw new RuntimeException("Problem with web address, check std::err");
		}
		Map<Calendar, Double> res = new LinkedHashMap<Calendar, Double>();
		List<Double> prices = null;
		List<Calendar> dates = null;
		prices = Commons.getValueOfField(document, "Mid").stream().map(s -> Double.parseDouble(s)).collect(Collectors.toList());
		dates = Commons.getValueOfField(document, "EffectiveDate").stream().map(s -> {
			Calendar x = Calendar.getInstance();
			x.setTime(Date.valueOf(s));
			return x;
		}).collect(Collectors.toList());
		for(int i = 0 ; i < dates.size(); i++) {
			res.put(dates.get(i), prices.get(i));
		}
		return res;
	}
}
