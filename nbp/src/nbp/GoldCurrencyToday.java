package nbp;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Contain methods to get price of gold or currency from site nbp
 * 
 * @author rafal
 *
 */
public class GoldCurrencyToday {
	/**
	 * Return map of rates:
	 * key is name of rating good
	 * List of prices that good
	 * @param symbol	Symbol of currency, which will be returned in map
	 * @return			Map, described above
	 */
	public static Map<String, List<String>> getPrices(String symbol) {
		Map<String, List<String>> res = new HashMap<String, List<String>>();
		Calendar today = Calendar.getInstance();
		res.put("gold", getPriceGold(Commons.getDate(today)));
		res.put(symbol, getPriceCurrency(symbol));
		return res;
	}
	
	/**
	 * Connect with site api.nbp.pl and get price of gold from given day
	 * @param date	
	 * @return		price of gold in PLN/g
	 */
	private static List<String> getPriceGold(String date){
		String address = "http://api.nbp.pl/api/cenyzlota/" + date;
		List<String> res = null;
		try {
			res = getPrice(address, "Cena");
		} catch (IOException e) {
			System.err.println("GoldCurrencyToday.getPriceGold: " + e.toString());
			throw new RuntimeException("getPriceGold: Problem with web address, check std::err");
		}
		return res;
	}
	
	/**
	 * Return average price of currency/PLN from given day
	 * @param date
	 * @param symbol	Symbol of currency
	 * @return			Price of currency in PLN
	 */
	private static List<String> getPriceCurrency( String symbol){
		String address = ("http://api.nbp.pl/api/exchangerates/rates/a/" + symbol).toLowerCase();
		List<String> res = null;
		try {
			res = getPrice(address, "Mid");
		} catch (IOException e) {
			System.err.println("GoldCurrencyToday.getPriceCurrency: " + e.toString());
			throw new RuntimeException("getPriceCurrency: Problem with web address, check std::err");
		}
		return res;
	}
	
	/**
	 * Connect with given address and search in DOC file node with name whatLookFor
	 * and return value of this node		
	 * @param address		address of site, from which data will be taken
	 * @param whatLookFor	name of node which contain price
	 * @return				price of good
	 * @throws IOException	when address is incorrect
	 */
	private static List<String> getPrice(String address, String whatLookFor) throws IOException {
		Document d = GetDOC.getInstance().getDOC(address);
		
		List <String> res = new LinkedList<String>();
		Node price = DOCProcessing.getNodeWithType(d, whatLookFor);
		if(price != null) {
			res.add(price.getFirstChild().getNodeValue());
		} else {
			System.out.println("Nulle, wszêdzie nulle");
			throw new RuntimeException("nbp.GoldCurrencyToday.getPrice: problem with " + whatLookFor + " in DOC");
		}
		return res;
	}
	
}
