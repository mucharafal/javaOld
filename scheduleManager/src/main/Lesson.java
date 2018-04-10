package main;

import java.lang.Math;

public class Lesson {
	
	private Term term;
	private String name;
	private String teacherName;
	private int year;
	private boolean full_time;
	private ITimetable timetable;
	
	public Lesson( Term term, String name, String teacherName, int year ){
		this.term = term;
		this.name = name;
		this.teacherName = teacherName;
		this.year = year;
		if( term.getDay() == Day.SAT ||
				term.getDay() == Day.SUN ||
				( term.getDay() == Day.FRI && term.getHour() > 16) ) {
			full_time = false;
		}else {
			full_time = true;
		}
		this.timetable = null;
	}
	
	public Lesson( ITimetable timetable, Term term, String name, String teacherName, int year ) throws IllegalArgumentException {
		if(timetable.busy(term)) throw new IllegalArgumentException("Term: " + term.toString() + " is busy; cannot create Lesson");
		this.term = term;
		this.name = name;
		this.teacherName = teacherName;
		this.year = year;
		if( term.getDay() == Day.SAT ||
				term.getDay() == Day.SUN ||
				( term.getDay() == Day.FRI && term.getHour() > 16) ) {
			full_time = false;
		}else {
			full_time = true;
		}
		timetable.put( this );
		this.timetable = timetable;
	}
	
	@Override
	public String toString() {
		return  name + " (" + term.getDay() + " " + term.getHour() + ":" + term.getMinute() + "-" + 
				term.endTerm().getHour() + ":" + term.endTerm().getMinute() + ")";
	}
	
	private static boolean isCorrectDayFullTime( Day day ) {
		return ( day == Day.MON || 
				day == Day.THU ||
				day == Day.WED ||
				day == Day.TUE );
	}
	
	private static boolean isCorrectDayPartTime( Day day ) {
		return ( day == Day.SUN || 
				day == Day.SAT );
	}
	
	private static boolean isCorrectHour( Term term, int begin, int end ) {
		return ( term.getHour() >= begin && ( term.endTerm().getHour() < end || ( term.endTerm().getHour() == end && term.endTerm().getMinute() == 0 ) ) );
	}
	
	public static boolean isCorrectTermFullTime( Term term ) {
		return  (( isCorrectHour( term, 8, 20 ) && isCorrectDayFullTime( term.getDay() )) ||
				( isCorrectHour(term, 8, 17 ) && term.getDay() == Day.FRI ) );
	}
	
	public static boolean isCorrectTermPartTime( Term term ) {
		return (( isCorrectDayPartTime(term.getDay()) && isCorrectHour( term, 8, 20 )) ||
				( term.getDay() == Day.FRI && isCorrectHour(term, 17, 20) ));
	}
	
	public boolean earlierDay() {
		Term earlierTerm = new Term( this.term.getHour(), this.term.getMinute(), this.term.getDay().prevDay() );
		if( this.timetable.canBeTransferredTo( earlierTerm ) ) {
			this.term = earlierTerm;
			return true;
		}
		return false;
		/*	
		if( this.full_time ) {
			if( isCorrectTermFullTime( new Term( this.term.getHour(), this.term.getMinute(), this.term.getDay().prevDay()))) {
				this.term.setDay( this.term.getDay().prevDay() );
				return true;
			}else {
				return false;
			}	
		}else {
			if( isCorrectTermPartTime( new Term( this.term.getHour(), this.term.getMinute(), this.term.getDay().prevDay()))) {
				this.term.setDay( this.term.getDay().prevDay() );
				return true;
			}else {
				return false;
			}
		}
		*/
	}
	
	public boolean laterDay() {
		Term laterTerm = new Term( this.term.getHour(), this.term.getMinute(), this.term.getDay().nextDay() );
		if( this.timetable.canBeTransferredTo( laterTerm ) ) {
			this.term = laterTerm;
			return true;
		}
		return false;
		/*
		if( this.full_time ) {
			if( isCorrectTermFullTime( new Term( this.term.getHour(), this.term.getMinute(), this.term.getDay().nextDay()))) 
				this.term.setDay( this.term.getDay().nextDay() );
			else
				return false;
		}else {
			if( isCorrectTermPartTime( new Term( this.term.getHour(), this.term.getMinute(), this.term.getDay().nextDay())))
				this.term.setDay( this.term.getDay().nextDay() );
			else
				return false;
		}
		return true;
		*/
	}
	
	public boolean earlierTime() {
		Term earlierTerm = new Term( ( int )( this.term.getHour() - Math.ceil( (double)( term.getDuration() - term.getMinute() ) / 60 )), ( term.getDuration() - term.getMinute()) % 60 , term.getDay() );
		
		if( this.timetable.canBeTransferredTo( earlierTerm ) ) {
			this.term = earlierTerm;
			return true;
		}
		return false;
		
		/*
		if( this.full_time ) {
			if( this.isCorrectTermFullTime( earlierTerm )) {
				this.term = earlierTerm;
			}else
				return false;
		}else {
			if( this.isCorrectTermPartTime(earlierTerm)) {
				this.term = earlierTerm;
			}
			else
				return false;
		}
		return true;
		*/
	}

	public boolean laterTime() {
		Term laterTerm = term.endTerm();
		
		if( this.timetable.canBeTransferredTo( laterTerm ) ) {
			this.term = laterTerm;
			return true;
		}
		return false;
		
		/*
		if( this.full_time ) {
			if( this.isCorrectTermFullTime( laterTerm )) {
				this.term = laterTerm;
				return true;
			}else {
				return false;
			}
		}else {
			if( this.isCorrectTermPartTime(laterTerm)) {
				this.term = laterTerm;
				return true;
			}else {
				return false;
			}
		}
		*/
	}

	
	public Term getTerm() {
		return term;
	}
	

	public void setTerm(Term term) {
		this.term = term;
	}
	

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	

	public String getTeacherName() {
		return teacherName;
	}
	

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	

	public int getYear() {
		return year;
	}
	

	public void setYear(int year) {
		this.year = year;
	}
	

	public boolean isFull_time() {
		return full_time;
	}
	

	public void setFull_time(boolean full_time) {
		this.full_time = full_time;
	}
	
	
}
