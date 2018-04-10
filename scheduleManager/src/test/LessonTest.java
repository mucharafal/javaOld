package test;

import main.*;
import main.Lesson;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.*;


public class LessonTest {
	
	private Lesson lessonTest = null;
	private Term termTest = null;
	
	@Before
	public void initialize() {
		termTest = new Term( 11, 15, Day.TUE );
		lessonTest = new Lesson( termTest, "PO", "Polak", 2017 );
	}
	
	@Test
	public void testConstructer() {
		assertTrue( lessonTest.isFull_time() );
		termTest.setDay( Day.SAT );
		Lesson u = new Lesson( termTest, "PO", "Polak", 2017 );
		assertFalse( u.isFull_time() );
		
		
	}

}
