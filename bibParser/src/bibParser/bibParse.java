package bibParser;

import java.io.File;
import java.util.List;

/**
 * Class contain main method - bibParse which:
 * - process arguments from command line
 * - open file
 * - invoke parser
 * - invoke print
 * and two helper methods
 * @author rafal
 *
 */

public class bibParse {
	/**
	 * Process arguments from command line
	 * If list of arguments is empty, print help
	 * In other case, try to open and parse file and print 
	 * entries which are invoke in command line 
	 * @param args			argument from command line, if not empty:
	 * first argument is path to file
	 * last argument is sign using to create frame
	 * middle arguments can be name of type or name of Author, which 
	 * entries will be printed
	 * @throws Exception	
	 */
	public static void main(String[] args) throws Exception {
		
		if(args.length == 0) {
			printHelp();
		} else {
			//opening file
			String filePath = args[0];
			File f = new File(filePath);
			//parsing file

			Parser.parse(f);
			//processing arguments
			parseCommandAndPrint(args);
		}
	}
	
	
	/**
	 * Process arguments form command line, last argument treat as sign to create frame
	 * @param args	arguments from command line
	 */
	private static void parseCommandAndPrint(String[] args) throws Exception {
		
		if(args[args.length - 1].length() != 1) {
			throw new RuntimeException("Incorrect sign for frame: " + args[args.length - 1]);
		}
		
		for(int i = 1; i < args.length - 1; i++) {
			
			//getting list of papers to print
			List<Paper> l;
			if(Category.containCategory(args[i])) {
				l = Category.get(args[i]);
			} else {
				if(Author.containAuthor(args[i])) {
					l = Author.getPaperOfAuthor(args[i]);
				} else {
					System.out.println("\n" + args[i] + " no such category or author\n");
					continue;
				}
			}
			
			//invoking function to print list of papers
			System.out.println(args[i]);
			paperPrinter.print(l, args[args.length - 1].toCharArray()[0]);
		}
	}
	
	/**
	 * print help, invoke if no argument delivered
	 */
	private static void printHelp() {
		System.out.println("Bibparser- read file in format bibTex, and allow to find artefacts by category and authors.\nCommand format: \t java -classpath . bibParser.bibParse PathToFile.bib [\"AuthorName\"] [\"CategoryName\"] \"signForFrame\"");
	}
}
