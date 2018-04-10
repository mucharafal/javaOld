package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import main.*;

public class TimeTable2Test {

	private Break[] breaks;
	private AbstractTimetable timetable;
	private Lesson l1, l2;
	private Action[] actions = { Action.DAY_LATER, Action.TIME_LATER, Action.TIME_LATER };
	
	@Before
	public void initialize(){
		breaks = new Break[1];
		breaks[0] = new Break( new BasicTerm( 9, 30 ), 5);
		
		timetable = new Timetable2( breaks );
		l1 = new Lesson( timetable, new Term( 8, 0, Day.MON ), "PSI", "WR", 2017 );
		l2 = new Lesson( timetable, new Term( 9, 35, Day.MON), "TC", "JD", 2017 );
	}
	
	@Test
	
	public void testPerform() {
		timetable.perform(actions);
		System.out.println(timetable.toString());
	}
	
	public void testCanBeTransfered() {
		Term term = new Term( 8, 00, Day.MON);
		assertTrue( timetable.canBeTransferredTo(term));
	}
	
	public void testBusy() {
		Term term = new Term( 15, 20, Day.MON);
		assertTrue( timetable.busy(term));
	}

}
