package nbp;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MostAmplitude {
	/**
	 * Return Code of currency, which rate has the biggest amplitude since begin to end
	 * @param begin		type Calendar, begin of time interval
	 * @param end		type Calendar, end of time interval
	 * @return			type String, code i.e. "USD"
	 */
	public static String mostAmplitudeCurrency(Calendar begin, Calendar end) {
		
		String res = null;
		Double mostAmplitude = 0.0;
		
		Iterator<String> codeIt = Symbols.getInstance().getSymbols().iterator();
		while(codeIt.hasNext()) {
			
			String code = codeIt.next();
			List<Double> prices = Commons.getCurrencyLongPeriod(code, begin, end).values().stream().collect(Collectors.toList()); //getting prices
			
			Comparator<Double> comp = (d1, d2) -> Double.compare(d1, d2);
			
			Double max = prices.parallelStream().max(comp).get();						//min i max price
			Double min = prices.parallelStream().min(comp).get();
			
			double amplitude = (max - min);
			if(mostAmplitude < amplitude) {
				mostAmplitude = amplitude;
				res = code;
			}
		}
		return res;
	}

	
}
