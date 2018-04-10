package nbp;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;

public class LowestPrice {
	/**
	 * Return code of currency, which has the lowest price in given day
	 * @param date		day to check currency prices
	 * @return			symbol of currency
	 */
	public static String get(Calendar date) {
		String res = null;
		Map<String, String> c = getCurrenciesFromDay(date, "Bid");
		Set<String> keys = c.keySet();
		Double min = 100000000.0;
		for(String key : keys) {
			if(min > Double.parseDouble(c.get(key))) {
				res = key;
				min = Double.parseDouble(c.get(key));
			}
		}
		return res;
	}
	/**
	 * Return map <CurrencySymbol, Price> for given day
	 * @param date	
	 * @param what	"Bid" or "Ask"
	 * @return
	 */
	public static Map<String, String>getCurrenciesFromDay(Calendar date, String what) {
		String address = "http://api.nbp.pl/api/exchangerates/tables/c/" + Commons.getDate(date);
		Document document = null;
		try {
			document = GetDOC.getInstance().getDOC(address);
		} catch (IOException e) {
			System.err.println(e);
			throw new RuntimeException("Problem with web address, check std::err");
		}
		List<String> codes = Commons.getValueOfField(document, "Code");
		List<String> whatRet = Commons.getValueOfField(document, what);
		Map<String, String> res = new HashMap<String, String>();
		for(int i = 0; i < codes.size(); i++ ) {
			res.put(codes.get(i), whatRet.get(i));
		}
		return res;
	}
}
