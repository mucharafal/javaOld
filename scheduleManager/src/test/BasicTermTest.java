package test;

import static org.junit.Assert.*;

import org.junit.Test;
import main.*;

public class BasicTermTest {

	@Test
	public void testColide() {
		BasicTerm b = new BasicTerm(11,15);
		BasicTerm c = new BasicTerm(11,30);
		BasicTerm d = new BasicTerm(12,45);
		assertTrue( b.colide(c));
		assertTrue( c.colide(b));
		assertTrue( c.colide(c));
		assertTrue( c.colide(d));
		assertFalse( d.colide(b));
		assertFalse( b.colide(d));
	}

}
