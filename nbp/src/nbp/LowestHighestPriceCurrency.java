package nbp;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Set;

public class LowestHighestPriceCurrency {
	/**
	 * Finding when currency has the highest and the lowest price in given period
	 * @param code		code of currency i.e. USD
	 * @param begin		begin of period
	 * @param end		end of period
	 * @return			{TheHighestPriceDate, TheLowestPriceDate}
	 */
	public static Calendar[] get(String code, Calendar begin, Calendar end) {
		LinkedHashMap<Calendar, Double> rates = (LinkedHashMap<Calendar, Double>)Commons.getCurrencyLongPeriod(code, begin, end);
		Calendar min = null;
		Double minVal = Double.MAX_VALUE;
		Calendar max = null;
		Double maxVal = 0.0;
		Set<Calendar> keys = rates.keySet();
		for(Calendar key : keys) {
			if(minVal > rates.get(key)) {
				minVal = rates.get(key);
				min = key;
			}
			if(maxVal <  rates.get(key)) {
				maxVal = rates.get(key);
				max = key;
			}
		}
		
		Calendar[] res = {max, min};
		return res;
	}
}
