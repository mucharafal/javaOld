package main;

public class Term extends BasicTerm {
	private Day day;
	
	public Term( int hour, int minute, Day day ) {
		super( hour, minute );
		this.day = day;
	}
	
	@Override
	public String toString() {
		return ( super.toString() + " " + this.day.toString() );
	}
	
	public Term endTerm( Term termin ) {
		int deltaHour = termin.hour - this.hour;
		int deltaMinute = termin.minute - this.minute;
		deltaMinute += deltaHour * 60;
		
		Term result = new Term( this.hour, this.minute, this.day );
		result.duration = deltaMinute;
		
		return result;
	}
	
	@Override
	public Term endTerm() {
		Term result = new Term( ( this.hour + ( this.duration + this.minute ) / 60 ) % 24 ,
				( this.minute + this.duration ) % 60,
				( this.hour + ( this.duration + this.minute ) / 60 > 23 ) ? this.day.nextDay() : this.day );
		result.duration = this.duration;
		return result;
	}
	
	public boolean equals( Term termin ) {
		return( super.equals( (BasicTerm) termin) &&
				this.day == termin.day );
	}

	
	public Day getDay() {
		return day;
	}

	
	public void setDay(Day day) {
		this.day = day;
	}

	public int hashCode() {
		int hash = ((( this.hour * 32 + this.minute ) * 64 + this.day.toInt() ) * 1024 ) + this.duration;
		return hash;
	}
	
	public boolean colide(Term term) {
		return ((super.colide(term))&&(term.day == this.day));
	}

}
