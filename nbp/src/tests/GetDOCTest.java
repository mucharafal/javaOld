package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

import nbp.DOCProcessing;
import nbp.GetDOC;

public class GetDOCTest {
	private String projectPath = "C:/Users/rafal/Programy/java/nbp";
	
	@Test
	public void testGetDOC() {
		
		GetDOC getDoc = GetDOC.getInstance();
		
		String addressWithIncorrect = "http://api.nbp.pl/api/cenyzlota/2018-01-03";
		String addressWithCorrect = "http://api.nbp.pl/api/exchangerates/rates/a/chf/2018-01-03";
		try {
			File f = new File(projectPath + "/src/tests/chf.xml");
			Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
			assertFalse(DOCProcessing.equals(d, getDoc.getDOC(addressWithIncorrect)));
			
			assertTrue(DOCProcessing.equals(d, getDoc.getDOC(addressWithCorrect)));
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage() + "\n getDOC");
		}
	}

}
