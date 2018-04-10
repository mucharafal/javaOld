package nbp;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
/**
 * Class to connect with site and parse file to DOC format
 * Can exist only one instance of class 
 * @author rafal
 *
 */
public class GetDOC {
	
	String format;
	static GetDOC getDoc = null;
	
	private GetDOC() {
		if(getDoc == null) {
			getDoc = this;
			this.format = "/?format=xml";
		}
	}
	
	/**
	 * Method allow to access to object type GetDOC
	 * @return GetDOC object
	 */
	public static GetDOC getInstance() {
		if(getDoc == null) {
			new GetDOC();
		}
		return getDoc;
	}
	/**
	 * 
	 * @param address			address of site to connect and parse content
	 * @return					object Document, which contain parsed site
	 * @throws IOException		when address is incorrect
	 */
	public Document getDOC(String address) throws IOException {
		Document doc = null;

		URL nbpApiSite = new URL(address + this.format);
		
		URLConnection nbpConnection = nbpApiSite.openConnection();
		nbpConnection.connect();
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(nbpConnection.getInputStream());
		} catch (Exception e) {
			System.err.println(e.toString());
			throw new RuntimeException("GetDOC: Exception during parsing file, check std:err");
		}
		return doc;
	}
}
