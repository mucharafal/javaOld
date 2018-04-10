package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import nbp.DOCProcessing;
import nbp.Symbols;

public class DOCProcessingTest {
	private String projectPath = "C:/Users/rafal/Programy/java/nbp";
	
	@Test
	public void getAllNodesWithTypeTest() {
		Document d = SymbolsTest.getFile(projectPath + "/src/tests/chf.xml");
		try {
			Method getAllNodesWithType = DOCProcessing.class.getDeclaredMethod("getAllNodesWithType", new Class[] {Node.class, String.class});
			List<Node> l = (List<Node>)getAllNodesWithType.invoke(null, d, "Cena");
			assertTrue(l.size() == 0);
			
			l = (List<Node>)getAllNodesWithType.invoke(null, d, "Rate");
			assertTrue(l.size() == 1);
			
			d = SymbolsTest.getFile(projectPath + "/src/tests/api.nbp.pl.xml");
			
			l = (List<Node>)getAllNodesWithType.invoke(null, d, "Cena");
			assertTrue(l.size() == 0);
			
			l = (List<Node>)getAllNodesWithType.invoke(null, d, "Rate");
			assertTrue(l.size() == 3);
			
			l = (List<Node>)getAllNodesWithType.invoke(null, d, "Code");
			assertTrue(l.size() == 3);
			
		
		} catch (Exception e) {
			System.out.println(e.toString());
			fail();
		}
	}
	
	

}
