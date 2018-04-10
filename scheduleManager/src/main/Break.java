package main;

public class Break implements Comparable<Break> {
	
	private BasicTerm basicTerm;
	
	public Break( BasicTerm bterm, int duration ) {
		basicTerm = bterm;
		bterm.setDuration(duration);
	}
	
	public String toString() {
		return "Przerwa";
	}
	
	public BasicTerm getTerm() {
		return basicTerm;
	}
	
	public int compareTo(Break that) {
		return basicTerm.compareTo(that.basicTerm);
	}
	
	
	
}
