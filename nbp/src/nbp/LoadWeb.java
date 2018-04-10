package nbp;

import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoadWeb {
	public static void main(String[] args) {
		if(args.length == 0) {
			printHelp();
			return;
		}
		if(args.length == 1) {
			printHelp();
		} else {
		if(args.length == 2) {
			//1
			if(args[0].equals("goldAndCurrency")) {
				if(!Symbols.getInstance().getSymbols().contains(args[1])) {
					throw new RuntimeException("Incorrect currency code");
				}
				Map<String, List<String>> m = GoldCurrencyToday.getPrices(args[1]);
				printMap(m);
			} else {
			//3
			if(args[0].equals("mostAmplitude")) {
				Calendar begin = Calendar.getInstance();
				begin.setTime(Date.valueOf(args[1]));
				System.out.println(MostAmplitude.mostAmplitudeCurrency(begin, Calendar.getInstance()));
			} else {
			//4
			if(args[0].equals("minBid")) {
				Calendar date = Calendar.getInstance();
				date.setTime(Date.valueOf(args[1]));
				System.out.println(LowestPrice.get(date));
			} else {
			//5
			if(args[0].equals("differenceBidAskSort")) {
				Calendar date = Calendar.getInstance();
				date.setTime(Date.valueOf(args[1]));
				LinkedHashMap<String, Double> r = (LinkedHashMap<String, Double>)DifferenceBidAsk.get(date);
				Iterator<Map.Entry<String, Double>> i = r.entrySet().iterator();
				while(i.hasNext()) {
					Map.Entry<String, Double> e = i.next();
					System.out.println(e.getKey() + " " + e.getValue());
				}
			} else {
				printHelp();
			}}}}
		} else {
		
		if(args.length == 3) {
			//2
			if(args[0].equals("avgGold")) {
				Calendar begin = Calendar.getInstance();
				begin.setTime(Date.valueOf(args[1]));
				Calendar end = Calendar.getInstance();
				end.setTime(Date.valueOf(args[2]));
				
				System.out.println(AvgGold.getAvgGoldPrice(begin, end));
			} else {
				printHelp();
			}
		} else {
		if(args.length == 4) {
			//6
			if(args[0].equals("lowestAndHighestPriceOf")) {
				Calendar begin = Calendar.getInstance();
				begin.setTime(Date.valueOf(args[2]));
				Calendar end = Calendar.getInstance();
				end.setTime(Date.valueOf(args[3]));
				Calendar[] dates = LowestHighestPriceCurrency.get(args[1], begin, end);
				System.out.println("Max price: " + Commons.getDate(dates[0]));
				System.out.println("Min price: " + Commons.getDate(dates[1]));
			} else {
				printHelp();
			}
		} else {
		
		if(args.length == 8) {
			//7
			if(args[0].equals("showChart")) {
				ChartOf.show(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]),
						Integer.parseInt(args[4]), Integer.parseInt(args[5]),
						Integer.parseInt(args[6]), Integer.parseInt(args[7]));
			} else {
				printHelp();
			}
		} else {
			printHelp();
		}}}}}
	}

	private static void printMap(Map<String, List<String>> m) {
		Set<String> keys = m.keySet();
		for(String key : keys) {
			System.out.println(key);
			List<String> l = m.get(key);
			Iterator<String> i = l.iterator();
			while(i.hasNext()) {
				System.out.println(i.next());
			}
		}
	}
	
	private static void printHelp() {
		System.out.println("Pomoc: \n"
				+ "Mo¿liwe wywo³ania:\n"
				+ "goldAndCurrency KodWaluty- zwraca obecn¹ cenê z³ota i podanej waluty\n"
				+ "np. goldAndCurrency USD\n"
				+ "mostAmplitude Data- zwraca walutê, której kurs mia³ najwiêksz¹ amplitudê od podanej daty\n"
				+ "np. mostAmplitude 2013-01-03\n"
				+ "minBid Data- zwraca kod waluty, której kurs sprzeda¿y by³ najni¿szy w podanym dniu\n"
				+ "np. minBid 2013-01-03\n"
				+ "differenceBidAskSort Data- zwraca tabelê walut uporz¹dkowan¹ rosn¹co wzglêdem ró¿nicy miêdzy kursem sprzeda¿y i kupna z podanego dnia\n"
				+ "np. differenceBidAskSort 2013-01-03\n"
				+ "avgGold DataPocz¹tkowa DataKoñcowa- zwraca œredni¹ cenê z³ota za podany okres\n"
				+ "np. avgGold 2012-01-01 2013-01-03\n"
				+ "lowestAndHighestPriceOf KodWaluty DataPocz¹tkowa DataKoñcowa- zwraca kiedy waluta mia³a najwy¿sz¹ i najni¿sz¹ cenê w podanym okresie\n"
				+ "np. lowestAndHighestPriceOf USD 2012-01-01 2013-01-03\n"
				+ "showChart KodWaluty Pocz¹tkowe: Rok Miesi¹c TydzieñMiesi¹ca Koñcowe: Rok Miesi¹c TydzieñMiesi¹ca- zwraca tabelê...\n"
				+ "np. showChart USD 2012 01 01 2013 02 03\n");
	}
}
