package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

import nbp.LoadWeb;
import nbp.Symbols;

public class SymbolsTest {
	private String projectPath = "C:/Users/rafal/Programy/java/nbp";
	
	@Test
	public void testProcessDOC1() {
		Document d = getFile(projectPath + "/src/tests/chf.xml");
		try {
			Method process = Symbols.class.getDeclaredMethod("processDOC", new Class[] {Document.class});
			process.setAccessible(true);
			List<String> l = (List<String>)process.invoke(null, d);
			assertTrue(l.size() == 1);
			assertTrue(l.get(0).equals("CHF"));
		} catch (Exception e) {
			System.out.println(e.toString());
			fail();
		}
	}
	
	@Test
	public void testProcessDOC2() {
		Document d = getFile(projectPath + 	"/src/tests/api.nbp.pl.xml");
		try {
			Method process = Symbols.class.getDeclaredMethod("processDOC", new Class[] {Document.class});
			process.setAccessible(true);
			List<String> l = (List<String>)process.invoke(null, d);
			assertTrue(l.size() == 3);
			assertTrue(l.get(0).equals("THB"));
			assertTrue(l.get(1).equals("USD"));
			assertTrue(l.get(2).equals("AUD"));
		} catch (Exception e) {
			System.out.println(e.toString());
			fail();
		}
	}
	
	public static Document getFile(String path) {
		Document doc = null;
		try {
			File f = new File(path);
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage() + "\n getDOC" + e.toString());
		}
		return doc;
	}

}
