package main;

public class ActionsParser {
	
	public static Action[] parse( String[] array ) throws IllegalArgumentException {
		Action[] result = new Action[ array.length ];
		for( int i = 0 ; i < array.length ; i++ ) {
			switch( array[ i ] ) {
			case "d-":
				result[ i ] = Action.DAY_EARLIER;
				break;
			case "d+":
				result[ i ] = Action.DAY_LATER;
				break;
			case "t-":
				result[ i ] = Action.TIME_EARLIER;
				break;
			case "t+":
				result[ i ] = Action.TIME_LATER;
				break;
			default:
				throw new IllegalArgumentException("Translation " + array[i] + " is incorrect." );
			}
		}
		return result;
	}

}
