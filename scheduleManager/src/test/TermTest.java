package test;

import static org.junit.Assert.*;
import org.junit.*;

import main.Day;
//import org.junit.Test;
import main.Term;

//@RunWith( Parametrized.class )
public class TermTest {

	private Term t;
	
	@Before
	public void initialize() {
		t = new Term( 10, 40, Day.MON );
	}
	
	@Test
	public void testToString() {
		Term tester = new Term( 10, 20, Day.MON );
		assertEquals( "10:20 [90] Poniedzia³ek", tester.toString() );
	}

	@Test
	public void testEarilerThan() {
		Term tester = new Term( t.getHour() + 1, t.getMinute() + 1, Day.MON );
		assertFalse( t.earlierThan(tester));
		tester.setHour( tester.getHour() - 1 );
		assertFalse( t.earlierThan(tester));
		tester.setHour( tester.getHour() + 1 );
		tester.setMinute( tester.getMinute() - 2 );
		assertFalse( t.earlierThan(tester));
		tester.setHour( tester.getHour() - 2 );
		assertTrue( t.earlierThan(tester));
	}
	
	@Test
	public void testLaterThan() {
		Term tester = new Term( t.getHour() + 1, t.getMinute() + 1, Day.TUE );
		assertTrue( t.laterThan(tester));
		tester.setHour( tester.getHour() - 1 );
		assertTrue( t.laterThan(tester));
		tester.setHour( tester.getHour() + 1 );
		tester.setMinute( tester.getMinute() - 2 );
		assertTrue( t.laterThan(tester));
		tester.setHour( tester.getHour() - 2 );
		assertFalse( t.laterThan(tester));
	}
	
	@Test
	public void testEndTermWithOneArg() {
		Term tester = new Term( t.getHour() + 1, t.getMinute() + 1, Day.MON );
		assertEquals( 61, t.endTerm(tester).getDuration() );
		tester.setHour( tester.getHour() - 1 );
		assertEquals( 1, t.endTerm(tester).getDuration() );
		tester.setHour( tester.getHour() + 1 );
		tester.setMinute( tester.getMinute() - 2 );
		assertEquals( 59, t.endTerm(tester).getDuration() );
		tester.setHour( tester.getHour() - 2 );
		assertEquals( -61, t.endTerm(tester).getDuration() );
	}

	@Test
	public void testEndTermWithoutArgs() {
		assertEquals( t.endTerm().getDay(), Day.MON );
		assertEquals( t.endTerm().getHour(), 12 );
		assertEquals( t.endTerm().getMinute(), 10 );
		
		Term t = new Term( 23, 00, Day.MON );
		assertEquals( t.endTerm().getDay(), Day.TUE );
		assertEquals( t.endTerm().getHour(), 0 );
		assertEquals( t.endTerm().getMinute(), 30 );
	}
	
	@Test
	public void testEquals() {
		assertTrue( t.equals(t));
		Term k = new Term( t.getHour(), t.getMinute(), t.getDay() );
		assertTrue( t.equals(k));
		k.setDay( k.getDay().nextDay() );
		assertFalse( t.equals(k));
		k.setDay( k.getDay().prevDay() );
		k.setHour( k.getHour() - 1 );
		assertFalse( t.equals(k));;
		k.setHour( k.getHour() + 1 );
		k.setMinute( k.getMinute() - 1 );
		assertFalse( t.equals(k));
		k.setMinute( k.getMinute() + 1 );
		assertTrue( t.equals(k));
	}
	
	public void testHash() {
		Term q = new Term( t.getHour(), t.getMinute(), t.getDay() );
		assertEquals( q.hashCode(), t.hashCode() );
		q.setDuration(100);
		assertNotEquals( q.hashCode(), t.hashCode());
	}
}
