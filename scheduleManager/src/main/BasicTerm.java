package main;

public class BasicTerm implements Comparable<BasicTerm> {
	
	protected int hour, minute, duration;
	
	public BasicTerm( int hour, int minute ) {
		this.hour = hour;
		this.minute = minute;
		this.duration = 90;
	}
	
	public BasicTerm( int hour, int minute, int duration ) {
		this.hour = hour;
		this.minute = minute;
		this.duration = duration;
	}
	
	public String toString() {
		return ( hour + ":" + minute + " [" + duration + "]" );
	}
	
	public boolean earlierThan( BasicTerm termin ) {
		boolean result;
		if( termin.hour == this.hour ) {
			if( termin.minute < this.minute ) {
				result = true;
			}else {
				result = false;
			}
		}else{
			if( termin.hour < this.hour ) {
				result = true;
			}else {
				result = false;
			}
		}
		return result;
	}
	
	public boolean laterThan( BasicTerm termin ) {
		boolean result;
		if( !earlierThan( termin ) &&
				( termin.hour != this.hour || termin.minute != this.minute ) ) 
			result = true;
		else
			result = false;
		return result;
	}
	
	public BasicTerm endTerm( BasicTerm termin ) {
		int deltaHour = termin.hour - this.hour;
		int deltaMinute = termin.minute - this.minute;
		deltaMinute += deltaHour * 60;
		
		BasicTerm result = new BasicTerm( this.hour, this.minute );
		result.duration = deltaMinute;
		
		return result;
	}
	
	public BasicTerm endTerm() {
		BasicTerm result = new BasicTerm( ( this.hour + ( this.duration + this.minute ) / 60 ) % 24 ,
				( this.minute + this.duration ) % 60 );
		result.duration = this.duration;
		return result;
	}
	
	public boolean equals( BasicTerm termin ) {
		return ( this.hour == termin.hour &&
				this.minute == termin.minute &&
				this.duration == termin.duration );
	}
	
	public boolean colide( BasicTerm term ) {
		return !(( this.endTerm().hour < term.hour 
				|| ( this.endTerm().hour == term.hour  && this.endTerm().minute <= term.minute )) ||
				( this.hour > term.endTerm().hour 
						|| ( this.hour == term.endTerm().hour  && this.minute >= term.endTerm().minute ))
				);
	}
	
	public int getHour() {
		return hour;
	}

	
	public void setHour(int hour) {
		this.hour = hour;
	}

	
	public int getMinute() {
		return minute;
	}

	
	public void setMinute(int minute) {
		this.minute = minute;
	}

	
	public int getDuration() {
		return duration;
	}

	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int compareTo(BasicTerm that) {
		if(this.hour == that.hour) {
			if(this.minute == that.minute) {
				return this.duration - that.duration;
			} else {
				return this.minute - that.minute;
			}
		} else {
			return this.hour - that.hour;
		}
	}

}
