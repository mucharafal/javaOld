package main;

public enum Day {
	MON, TUE, WED, THU, FRI, SAT, SUN;
	
	public String toString() {
		String result = "";
		switch( this ) {
		case MON:
			result = "Poniedzia³ek";
			break;
		case TUE:
			result = "Wtorek";
			break;
		case WED:
			result = "Œroda";
			break;
		case THU:
			result = "Czwartek";
			break;
		case FRI:
			result = "Pi¹tek";
			break;
		case SAT:
			result = "Sobota";
			break;
		case SUN:
			result = "Niedziela";
			break;
		}
		return result;
	}
	
	
	public Day prevDay() {
		Day result = null;
		switch( this ) {
		case MON:
			result = SUN;
			break;
		case TUE:
			result = MON;
			break;
		case WED:
			result = TUE;
			break;
		case THU:
			result = WED;
			break;
		case FRI:
			result = THU;
			break;
		case SAT:
			result = FRI;
			break;
		case SUN:
			result = SAT;
			break;
		}
		return result;
	}
	
	public Day nextDay() {
		Day result = null;
		switch( this ) {
		case MON:
			result = TUE;
			break;
		case TUE:
			result = WED;
			break;
		case WED:
			result = THU;
			break;
		case THU:
			result = FRI;
			break;
		case FRI:
			result = SAT;
			break;
		case SAT:
			result = SUN;
			break;
		case SUN:
			result = MON;
			break;
		}
		return result;
	}
	
	public int toInt() {
			int result = 0;
			switch( this ) {
			case MON:
				result = 0;
				break;
			case TUE:
				result = 1;
				break;
			case WED:
				result = 2;
				break;
			case THU:
				result = 3;
				break;
			case FRI:
				result = 4;
				break;
			case SAT:
				result = 5;
				break;
			case SUN:
				result = 6;
				break;
			}
			return result;
	}
}
