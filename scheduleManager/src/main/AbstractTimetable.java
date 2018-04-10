package main;

import java.util.*;

public abstract class AbstractTimetable implements ITimetable {
	
	protected Map <Term,Lesson> lessons = null;
	
	public boolean busy( Term term ) {
		boolean busy = false;
		for(Lesson l : lessons.values()) {
			if(l.getTerm().colide(term))
				busy = true;
		}
		return busy;
	}
	
	public boolean canBeTransferredTo( Term term ) {
			return !this.busy(term) && ( Lesson.isCorrectTermFullTime(term) || Lesson.isCorrectTermPartTime(term) );
	}
	
	public boolean put( Lesson lesson ) throws IllegalArgumentException {
		if( this.busy( lesson.getTerm() ) &&
				this.canBeTransferredTo( lesson.getTerm() )) {
			throw new IllegalArgumentException("Lesson " + lesson.toString() + " can't be transfered.");
		} else {
			lessons.put(lesson.getTerm(),lesson);
			return true;
		}
	}
	
	public Lesson get( Term term ) {
		return lessons.get(term);
	}
	
	public void perform( Action[] actions ) {
		Set <Term> keys = lessons.keySet();
		Iterator<Term> it = keys.iterator();
		for( Action action : actions ) {
			int i = 0;
			  switch( action ) {
			  case DAY_EARLIER:
				  lessons.get((Term)it).earlierDay();
				  it.next();
				  i++;
				  if( i == lessons.size()) {
					  i = i % lessons.size();
					  it = keys.iterator();
				  }
				  break;
			  case DAY_LATER:
				  lessons.get((Term)it).laterDay();
				  it.next();
				  i++;
				  if( i == lessons.size()) {
					  i = i % lessons.size();
					  it = keys.iterator();
				  }
				  break;
			  case TIME_EARLIER:
				  lessons.get((Term)it).earlierTime();
				  it.next();
				  i++;
				  if( i == lessons.size()) {
					  i = i % lessons.size();
					  it = keys.iterator();
				  }
				  break;
			  case TIME_LATER:
				  lessons.get((Term)it).laterTime();
				  it.next();
				  i++;
				  if( i == lessons.size()) {
					  i = i % lessons.size();
					  it = keys.iterator();
				  }
				  break;
			  }
		  }
	}
	
	@Override 
	public String toString() {
		String result = "";
		Collection <Lesson> lessonsCol = lessons.values();
		for( Lesson l : lessonsCol ) {
			result += l.toString();
		}
		return result;
	}
	
}
