package main;

import java.util.*;

public class Timetable2 extends AbstractTimetable {

	Break [] breaks = null;
	private boolean skipBreaks;
	
	public Timetable2( Break [] breaks ) {
		lessons = new TreeMap <Term,Lesson> (new ComparatorTerm());
		this.breaks = breaks;
		skipBreaks = true;
		Arrays.sort(this.breaks);
	}
	
	private Break colideWithBreaks( Term term ) {
		Break colide = null;
		for( Break b : breaks ) {
			if( b.getTerm().colide(term))
				colide = b;
		}
		return colide;
	}
	
	@Override
	public boolean canBeTransferredTo( Term term ) {
		boolean conflictWithLessons = super.busy(term);
		boolean conflictWithBreaks = (this.colideWithBreaks(term)!=null);
		return !(conflictWithLessons || conflictWithBreaks);
	}

	@Override
	public void perform(Action[] actions) {
		Set <Term> keys = lessons.keySet();
		Iterator<Term> it = keys.iterator();
		if( skipBreaks ) {
			int i = 0;
			for( Action action : actions ) {
				Lesson l;
				  switch( action ) {
				  case DAY_EARLIER:
					  l = lessons.get(it.next());
					  i++;
					  if( i == lessons.size()) {
						  i = 0;
						  it = keys.iterator();
					  }
					  
					  Term earlierTerm = new Term( l.getTerm().getHour(), l.getTerm().getMinute(), l.getTerm().getDay().prevDay() );
					  earlierTerm.setDuration(l.getTerm().getDuration());

					  if( this.canBeTransferredTo(earlierTerm)) {
							  if (Lesson.isCorrectTermFullTime(earlierTerm) &&
									  Lesson.isCorrectTermFullTime(l.getTerm())) {
								  lessons.remove(l.getTerm());
								  l.setTerm(earlierTerm);
								  lessons.put(earlierTerm, l);

								  keys = lessons.keySet();
								  it = keys.iterator();
								  for(int j = 0 ; j < i ; j++ )	it.next();
							  }
							  if(Lesson.isCorrectTermPartTime(earlierTerm) &&
								  Lesson.isCorrectTermPartTime(l.getTerm())){
								  lessons.remove(l.getTerm());
								  l.setTerm(earlierTerm);
								  lessons.put(earlierTerm, l);

								  keys = lessons.keySet();
								  it = keys.iterator();
								  for(int j = 0 ; j < i ; j++ )	it.next();
							  } 
					  }
					  break;
				  case DAY_LATER:
					  l = lessons.get(it.next());
					  i++;
					  if( i == lessons.size()) {
						  i = 0;
						  it = keys.iterator();
					  }
					  
					  Term laterTerm = new Term( l.getTerm().getHour(), l.getTerm().getMinute(), l.getTerm().getDay().nextDay() );
					  laterTerm.setDuration(l.getTerm().getDuration());

					  if( this.canBeTransferredTo(laterTerm)) {
							  if (Lesson.isCorrectTermFullTime(laterTerm) &&
									  Lesson.isCorrectTermFullTime(l.getTerm())) {
								  lessons.remove(l.getTerm());
								  l.setTerm(laterTerm);
								  lessons.put(laterTerm, l);

								  keys = lessons.keySet();
								  it = keys.iterator();
								  for(int j = 0 ; j < i ; j++ )	it.next();
							  }
							  if(Lesson.isCorrectTermPartTime(laterTerm) &&
								  Lesson.isCorrectTermPartTime(l.getTerm())){
								  lessons.remove(l.getTerm());
								  l.setTerm(laterTerm);
								  lessons.put(laterTerm, l);

								  keys = lessons.keySet();
								  it = keys.iterator();
								  for(int j = 0 ; j < i ; j++ )	it.next();
							  } 
					  }
					  break;
				  case TIME_EARLIER:
					  l = lessons.get(it.next());
					  i++;
					  if( i == lessons.size()) {
						  i = 0;
						  it = keys.iterator();
					  }
					  
					  earlierTerm = new Term( ( int )( l.getTerm().getHour() - Math.ceil( (double)( l.getTerm().getDuration() - l.getTerm().getMinute() ) / 60 )), ( l.getTerm().getDuration() - l.getTerm().getMinute()) % 60 , l.getTerm().getDay() );
					  earlierTerm.setDuration(l.getTerm().getDuration());

					  Break earlierBreak = null;
					  for( Break b : breaks ) {
						  if( earlierTerm.colide(b.getTerm())) {
							  earlierBreak = b;
						  }
					  }
					  
					  if( earlierBreak != null ) {
						  earlierTerm = new Term( ( int )( earlierBreak.getTerm().getHour() - Math.ceil( (double)( l.getTerm().getDuration() - earlierBreak.getTerm().getMinute() ) / 60 )), ( l.getTerm().getDuration() - earlierBreak.getTerm().getMinute()) % 60 , l.getTerm().getDay() );
						  earlierTerm.setDuration(l.getTerm().getDuration());
					  }
					  
					  if( this.canBeTransferredTo(earlierTerm)) {
							  if (Lesson.isCorrectTermFullTime(earlierTerm) &&
									  Lesson.isCorrectTermFullTime(l.getTerm())) {
								  lessons.remove(l.getTerm());
								  l.setTerm(earlierTerm);
								  lessons.put(earlierTerm, l);

								  keys = lessons.keySet();
								  it = keys.iterator();
								  for(int j = 0 ; j < i ; j++ )	it.next();
							  }
							  if(Lesson.isCorrectTermPartTime(earlierTerm) &&
								  Lesson.isCorrectTermPartTime(l.getTerm())){
								  lessons.remove(l.getTerm());
								  l.setTerm(earlierTerm);
								  lessons.put(earlierTerm, l);

								  keys = lessons.keySet();
								  it = keys.iterator();
								  for(int j = 0 ; j < i ; j++ )	it.next();
							  } 
					  }
					  break;
				  case TIME_LATER:
					  l = lessons.get(it.next());
					  i++;
					  if( i == lessons.size()) {
						  i = 0;
						  it = keys.iterator();
					  }
					  
					  if( !this.skipBreaks ) break;
					  
					  laterTerm = l.getTerm().endTerm();
					  laterTerm.setDuration(l.getTerm().getDuration());

					  Break laterBreak = this.colideWithBreaks(laterTerm);
					  
					  if( laterBreak != null ) {
						  BasicTerm temp = laterBreak.getTerm().endTerm();
						  laterTerm = new Term(temp.getHour(), temp.getMinute(), laterTerm.getDay());
						  laterTerm.setDuration(l.getTerm().getDuration());
					  }
					  
					  if( this.canBeTransferredTo(laterTerm)) {
							  if (Lesson.isCorrectTermFullTime(laterTerm) &&
									  Lesson.isCorrectTermFullTime(l.getTerm())) {
								  lessons.remove(l.getTerm());
								  l.setTerm(laterTerm);
								  lessons.put(laterTerm, l);

								  keys = lessons.keySet();
								  it = keys.iterator();
								  for(int j = 0 ; j < i ; j++ )	it.next();
							  }
							  if(Lesson.isCorrectTermPartTime(laterTerm) &&
								  Lesson.isCorrectTermPartTime(l.getTerm())){
								  lessons.remove(l.getTerm());
								  l.setTerm(laterTerm);
								  lessons.put(laterTerm, l);

								  keys = lessons.keySet();
								  it = keys.iterator();
								  for(int j = 0 ; j < i ; j++ )	it.next();
							  } 
					  }
					  
					  break;
				  }
			  }
		} else {
			super.perform(actions);
		}
	}
	
}
