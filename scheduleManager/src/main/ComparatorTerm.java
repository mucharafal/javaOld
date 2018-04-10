package main;

import java.util.*;

public class ComparatorTerm implements Comparator<Term> {
	public int compare(Term term1, Term term2) {
		if(term1.getDay().toInt() == term2.getDay().toInt()) {
			if(term1.hour == term2.hour) {
				if(term1.minute == term2.minute) {
					return term1.duration - term2.duration;
				} else {
					return term1.minute - term2.minute;
				}
			} else {
				return term1.hour - term2.hour;
			}
		} else {
			return term1.getDay().toInt() - term2.getDay().toInt();
		}
	}
}
