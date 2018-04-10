package test;

import static org.junit.Assert.*;
import org.junit.Test;
import main.Day;

public class DayTest {

	@Test
	public void testNextDay() {
		Day tester = Day.MON;
		assertEquals( tester.nextDay(), Day.TUE );
		tester = Day.TUE;
		assertEquals( tester.nextDay(), Day.WED );
		tester = Day.WED;
		assertEquals( tester.nextDay(), Day.THU );
		tester = Day.THU;
		assertEquals( tester.nextDay(), Day.FRI );
		tester = Day.FRI;
		assertEquals( tester.nextDay(), Day.SAT );
		tester = Day.SAT;
		assertEquals( tester.nextDay(), Day.SUN );
		tester = Day.SUN;
		assertEquals( tester.nextDay(), Day.MON );
	}

	@Test
	public void testPrevDay() {
		Day tester = Day.WED;
		assertEquals( tester.prevDay(), Day.TUE );
		tester = Day.THU;
		assertEquals( tester.prevDay(), Day.WED );
		tester = Day.FRI;
		assertEquals( tester.prevDay(), Day.THU );
		tester = Day.SAT;
		assertEquals( tester.prevDay(), Day.FRI );
		tester = Day.SUN;
		assertEquals( tester.prevDay(), Day.SAT );
		tester = Day.MON;
		assertEquals( tester.prevDay(), Day.SUN );
		tester = Day.TUE;
		assertEquals( tester.prevDay(), Day.MON );
	}
}
