package nbp;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DifferenceBidAsk {
	/**
	 * Return map<CurrencySymbol, Ask-Bid> for given day, sorted by Ask-Bid
	 * @param date
	 * @return
	 */
	public static Map<String, Double> get(Calendar date){
		Map<String, Double> res = new HashMap<String, Double>();
		Map<String, String> bids = LowestPrice.getCurrenciesFromDay(date, "Bid");
		Map<String, String> asks = LowestPrice.getCurrenciesFromDay(date, "Ask");
		Set<String> keys = bids.keySet();
		for(String key : keys) {
			Double value = Double.parseDouble(asks.get(key)) - Double.parseDouble(bids.get(key));
			res.put(key, value);
		}
		res = sortMap(res);
		return res;
	}
	
	/**
	 * Sort map by value of entry
	 * @param m		map to sort
	 * @return		sorted map
	 */
	public static Map<String, Double> sortMap(Map<String, Double> m){	//from: https://www.mkyong.com/java/how-to-sort-a-map-in-java/
		List<Map.Entry<String, Double>> l = new LinkedList<Map.Entry<String, Double>>(m.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
		});
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : l) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
	}
}
