package main;

public class DeanerySystem {
	public static void main( String[] args ) {
		
		try {
			Term term1 = new Term(9,45, Day.MON);
			Break[] breaks = { new Break(new BasicTerm(9,30), 5),
					new Break(new BasicTerm(11,5), 10),
					new Break(new BasicTerm(12,45), 5),
					new Break(new BasicTerm(14,20), 20),
					new Break(new BasicTerm(16,10), 5),
					new Break(new BasicTerm(17,45), 5),
					new Break(new BasicTerm(19,20), 10)};
			AbstractTimetable timetable = new Timetable2(breaks);
			
			new Lesson( timetable, new Term(9,35,Day.TUE), "PP", "AZ", 2017);
			new Lesson( timetable, new Term(8,0,Day.MON), "P1", "AZ", 2017);
			
			System.out.println(timetable.toString());
			
			Action[] actions = { Action.DAY_LATER, Action.DAY_EARLIER };
			timetable.perform(actions);
			
			
			System.out.println(timetable.toString());
		}
		catch( IllegalArgumentException e ) {
			System.out.println("Illegal argument: " + e.getMessage());
		}
	}
}
