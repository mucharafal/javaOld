package bibParser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Category {
	private static Map<String, List<Paper>> categories = new TreeMap<String, List<Paper>>();
	
	/**
	 * Add new record to map defined in class Category
	 * @param category 
	 * @param paper
	 */
	public static void add(String category, Paper paper) {
		if(!categories.containsKey(category.toLowerCase())) {
			categories.put(category.toLowerCase(), new LinkedList<Paper>());
		}
		List<Paper> l = categories.get(category.toLowerCase());
		l.add(paper);
	}
	
	/**
	 * Return LinkedList of all paper from category
	 * @param category
	 * @return
	 */
	public static List<Paper> get(String category){
		return categories.get(category.toLowerCase());
	}
	
	/**
	 * Return if exist any paper of this category
	 * @param category
	 * @return
	 */
	public static boolean containCategory(String category) {
		return categories.containsKey(category.toLowerCase());
	}
}
