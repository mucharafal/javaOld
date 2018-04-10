package nbp;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Set;

public class ChartOf {
	/**
	 * Print table chart of prices given currency in period
	 * @param code			code of currency i.e. "USD"
	 * @param yearBegin		year since which start chart
	 * @param monthBegin	month since which start chart
	 * @param weekBegin		week of month since which start chart
	 * @param yearEnd		year of end of period
	 * @param monthEnd		month of end of period
	 * @param weekEnd		week of month of end of period
	 */
	public static void show(String code, int yearBegin, int monthBegin, int weekBegin, int yearEnd, int monthEnd, int weekEnd) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.YEAR, yearBegin);
		begin.set(Calendar.MONTH, monthBegin);
		begin.set(Calendar.WEEK_OF_MONTH, weekBegin);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.YEAR, yearEnd);
		end.set(Calendar.MONTH, monthEnd);
		end.set(Calendar.WEEK_OF_MONTH, weekEnd);
		LinkedHashMap<Calendar, Double> rates = (LinkedHashMap<Calendar, Double>)Commons.getCurrencyLongPeriod(code, begin, end);
		int dayOfWeek = Calendar.MONDAY;
		for(int i = 0 ; i < 5 ; i++ ) {
			switch(i) {
			case 0:
				System.out.println("Monday");
				break;
			case 1:
				System.out.println("Tuesday");
				break;
			case 2:
				System.out.println("Wednesday");
				break;
			case 3:
				System.out.println("Thursday");
				break;
			case 4:
				System.out.println("Friday");
				break;
			}
			showDayOfWeek(rates, i + dayOfWeek);
		}
	}
	/**
	 * Print prices table for one day of week
	 * @param rates			Collection with pairs Date and Price
	 * @param dayOfWeek		day of week, for which table will be printed, format Calendar.day_of_week
	 */
	public static void showDayOfWeek(LinkedHashMap<Calendar, Double> rates, int dayOfWeek) {
		Set<Calendar> keys = rates.keySet();
		int k = 0;
		for(Calendar day: keys) {
			if(day.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
				System.out.print("Week" + k + ":\t");
				for(int i = 0 ; i < rates.get(day); i++) {
					System.out.print("#");
				}
				System.out.println("\t" + rates.get(day));
				k++;
			}
			
		}
	}
}
