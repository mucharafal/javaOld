package bibParser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Author {
	private static Map<String, List<Paper>> authors = new TreeMap<String, List<Paper>>();
	/**
	 * Add paper to map in Author class, demand author or editor not to be empty
	 * @param newAuthor
	 */
	public static void add(Paper newAuthor) {
		//getting author name
		String author;
		if(newAuthor.containAuthor()) {
			author = newAuthor.getAuthor();
		} else {
			if(newAuthor.containEditor()) {
				author = newAuthor.getEditor();
			} else {
				throw new RuntimeException("No author or editor, paper type: " + newAuthor.getType() + " key " + newAuthor.getKey());
			}
		}

		//adding to map
		if(!authors.containsKey(author)) {
			authors.put(author, new LinkedList<Paper>());
		}
		List<Paper> l = authors.get(author);
		l.add(newAuthor);
	}
	
	/**
	 * Return if Map in class contain Author's name
	 * @param author
	 * @return
	 */
	public static boolean containAuthor(String author) {
		return authors.containsKey(author);
	}
	
	/**
	 * Return LinkedList all paper which are connected with Author
	 * @param author
	 * @return
	 */
	public static List<Paper> getPaperOfAuthor(String author){
		return authors.get(author);
	}
}
