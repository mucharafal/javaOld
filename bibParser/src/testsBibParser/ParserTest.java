package testsBibParser;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.junit.Test;

import bibParser.Parser;

public class ParserTest extends Parser {

	/*
	@Test
	public void testCutPartInsideCharacters() throws Exception {
		File f = new File("C:/Users/rafal/Desktop/test1.txt");
		Scanner s = new Scanner(f);
		String k = super.cutPartInsideCharacters( s, s.nextLine(), '[', ']');
		assertTrue(k.equals("[   gg\n \n  awsds fd vf f s \n \njj \n \n]"));
		s.close();
	}
	*/
	
	@Test
	public void testNormalTypeIn1() {
		String in = "ala";
		Map <String, String> m = new TreeMap<String, String>();
		m.put("ala", "ala ma kota");
		StringBuilder res = new StringBuilder();
		int t = Parser.normalTypeIn(in, res, 0, m);
		assertEquals(t, 3);
		assertTrue(m.get(in).equals(res.toString()));
	}
	
	@Test
	public void testNormalTypeIn2() {
		Map <String, String> m = new TreeMap<String, String>();
		StringBuilder res = new StringBuilder();
		String in = "1999";
		int t = Parser.normalTypeIn(in, res, 0, m);
		assertEquals(t, 4);
		assertTrue(in.equals(res.toString()));
	}
	
	@Test
	public void testQuoteIn1() {
		String in = "\"Ala ma kota 1998\"";
		StringBuilder res = new StringBuilder();
		int t = Parser.quoteIn(in, res, 0);
		assertEquals(t, in.length());
		assertTrue(("Ala ma kota 1998").equals(res.toString()));
	}
	
	@Test
	public void testQuoteIn2() {
		String in = "\"Ala ma kota 1998\"";
		StringBuilder res = new StringBuilder();
		try {
			Parser.quoteIn(in, res, 2);
			fail();
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().equals("quoteIn: no quote at begin"));
		}
	}
	
	@Test
	public void testQuoteIn3() {
		String in = "\"Ala ma kota 1998";
		StringBuilder res = new StringBuilder();
		try {
			Parser.quoteIn(in, res, 0);
			fail();
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().equals("quoteIn: Quotes are not ballanced in: " + in));
		}
	}
	
	@Test
	public void testCurlyIn() {
		String in = "{a{a}a}";
		StringBuilder s = new StringBuilder();
		int i = 0;
		Parser.curlyIn(in, s, i);
		assertEquals("aaa", s.toString());
	}
	
	@Test
	public void testCurlyIn1() {
		String in = "{Ala ma kota 1998}";
		StringBuilder res = new StringBuilder();
		try {
			Parser.curlyIn(in, res, 1);
			fail();
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().equals("curlyIn: no curly bracket at begin"));
		}
	}
	
	@Test
	public void testCurlyIn2() {
		String in = "{Ala ma kota 1998";
		StringBuilder res = new StringBuilder();
		try {
			Parser.curlyIn(in, res, 0);
			fail();
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().equals("curlyIn: Curly brackets are not ballanced in: " + in));
		}
	}
	
}
