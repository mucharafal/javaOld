package nbp;

import java.io.IOException;
import java.util.List;

import org.w3c.dom.Document;
/**
 * Class Symbols 
 * object of this class contain list of symbols of currencies from site api.nbp.pl 
 * contain methods to process DOC object and get from it symbols of currencies
 * demand class GetDOC, or different to connect with web
 * @author rafal
 *
 */
public class Symbols {
	private List<String> symbols;
	private static Symbols obj = null;
	private Symbols() {
		if(obj == null) {
			obj = this;
			Document d = null;
			try {
				d = GetDOC.getInstance().getDOC("http://api.nbp.pl/api/exchangerates/tables/a");
			} catch(IOException e) {
				System.err.println(e);
				throw new RuntimeException("Problem with link, check std::err");
			}
			this.symbols = extractSymbolsFromDOC(d);
		}
	}
	/**
	 * Return instance of class, if it is not created invoke constructor
	 * @return
	 */
	public static Symbols getInstance() {
		if(obj == null) {
			new Symbols();
		}
		return obj;
	}
	/**
	 * Return list of currencies symbols, which are avaliable on website api.nbp.pl
	 * @return
	 */
	public List<String> getSymbols(){
		return this.symbols;
	}
	/**
	 * Return list of currencies symbols, which from given Document
	 * @param document	Document object
	 * @return
	 */
	private static List<String> extractSymbolsFromDOC(Document document) {
		List <String> s = Commons.getValueOfField(document, "Code");
		return s;
	}
}
