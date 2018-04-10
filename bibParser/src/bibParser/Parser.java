package bibParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
/**
 * Class contain static methods to cope with parsing 
 * bibTex files. Class is linked with class Paper
 *
 */
public class Parser {
	/**
	 * Method, which process bibTex file and create Paper objects
	 * Process String entries
	 * Sent type and content of entry and map of Strings to Paper constructor
	 * @param file			file in bibTex format, which is parsed by program
	 * @throws Exception	Exception from invoking Paper constructor, handled to add stacktrace
	 */
	public static void parse(File file) throws Exception {
		//Preparing
		//Preparing scanner
		Scanner input;
		try {
			input = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return;
		}
		
		//correct types
		String[] types = {
				"@ARTICLE", "@BOOK", "@INPROCEEDINGS",
				"@CONFERENCE", "@BOOKLET", "@INBOOK", "@INCOLLECTION",
				"@MANUAL", "@MASTERTHESIS", "@PHDTHESIS", "@TECHREPORT",
				"@MISC", "@UNPUBLISHED"
		};
		
		//map for @STRING
		Map<String,String> strings = new TreeMap<String, String>();
		initMonth(strings);
		
		//Processing
		while(input.hasNextLine()) {

			String line = input.nextLine();
			String restLine = "";
			
			//checking if line is not a comment <=>
			//if it is in form: typeName{cotent, maybe { or (
			//if it is, then splitedLine[0]: contain "typeName", restLine: "{content, maybe { or ("
			//***********************************************************************************
			String[] splitedLine;		//spliting line on { or ( sign
			if(line.contains("{")) {
				splitedLine = line.split("\\{");
				
				//repairing, what destroy split above
				StringBuilder content = new StringBuilder();
				for(int i = 1; i < splitedLine.length ; i++) {
					content.append('{');
					content.append(splitedLine[i]);
				}
				if(content.length()>0) {
					restLine = content.toString();
				} else {
					restLine = "\\{";
				}
				
			} else {
				if(line.contains("(")) {
					splitedLine = line.split("\\(");

					//the same as above
					StringBuilder content = new StringBuilder();
					for(int i = 1; i < splitedLine.length ; i++) {
						content.append('(');
						content.append(splitedLine[i]);
					}
					if(content.length()>0) {
						restLine = content.toString();
					} else {
						restLine = "\\(";
					}
				} else {
					//if it not contain ( nor { sign, so its comment
					continue;
				}
			}
			//***********************************************************************************
			//So far, line contain { or ( sign, in splitedLine[0] can be a type, in restLine as name
			//String processing
			if(splitedLine[0].toUpperCase().equals("@STRING")) {
				
				//getting proper content
				String content;
				if(restLine.charAt(0) == '(')
					content = cutPartInsideRoundBrackets(input, restLine);
				else
					content = cutPartInsideCurlyBrackets(input, restLine);
				
				//cleaning { or ( and } or ) sign
				StringBuilder toClean = new StringBuilder();
				toClean.append(content);
				toClean.deleteCharAt(0);
				toClean.deleteCharAt(toClean.length() - 1);
				
				content = toClean.toString();

				//spliting on two strings on first '=' sign
				StringBuilder stringName = new StringBuilder(), fieldContent = new StringBuilder();
				parseField(content, 0, strings, stringName, fieldContent);
				
				strings.put(stringName.toString(), fieldContent.toString());
				continue;
			}
			
			//Now, if it is not STRING, checking if it's one of correct types, which names are in types array
			if(Arrays.asList(types).contains(splitedLine[0].replace(" ", "").toUpperCase())){
				String type, content;
				//parsing type
				type = splitedLine[0].replace(" ", "").replace("@", "").toUpperCase();
				//parsing content of entry, from { to } or ( to )
				if(restLine.charAt(0) == '(')
					content = cutPartInsideRoundBrackets(input, restLine);
				else
					content = cutPartInsideCurlyBrackets(input, restLine);
				
				//sending type with content of entry to Paper constructor
				try {
					new Paper(type, content, strings);
				} catch(Exception e) {
					throw new Exception("Exception on Parser: \n" + e.toString());
				}
			}
		}
		input.close();
	}
	/**
	 * Take input and first line of text to parse
	 * and return well bracket-balanced entry from { to proper } 
	 * It use cutPartInsideCharacters
	 * @param input			Scanner which read parsed file
	 * @param firstLine		First line of text to parse 
	 * @return				Entry in form String, from { sign to }
	 */
	public static String cutPartInsideCurlyBrackets(Scanner input, String firstLine) {
		return cutPartInsideCharacters(input, firstLine, '{', '}');
	}
	
	/**
	 * Take input and first line of text to parse
	 * and return well bracket-balanced entry from ( to proper ) 
	 * It use cutPartInsideCharacters
	 * @param input			Scanner which read parsed file
	 * @param firstLine		First line of text to parse 
	 * @return				Entry in form String, from ( sign to )
	 */
	public static String cutPartInsideRoundBrackets(Scanner input, String firstLine) {
		return cutPartInsideCharacters(input, firstLine, '(', ')');
	}
	
	/**
	 * Take input and first line of text to parse
	 * and return well balanced entry from opening char to proper closing char 
	 * entries can be nested
	 * @param input			Scanner which read parsed file
	 * @param firstLine		First line of text to parse, has to contain openingChar
	 * @param openingChar	Sign which open entry
	 * @param closingChar	Sign which end entry
	 * @return
	 */
	public static String cutPartInsideCharacters(Scanner input, String firstLine, char openingChar, char closingChar) {
		
		//result String
		StringBuilder out = new StringBuilder();
		
		int numOpenedBrackets = 0;
		int iterator = 0;
		//getting position of first openingChar 
		while(iterator < firstLine.length() && firstLine.charAt(iterator) != openingChar) 	iterator++;
		
		//if firstLine not contain openingChar
		if(iterator == firstLine.length())	return null;
		
		out.append(firstLine.charAt(iterator));
		numOpenedBrackets++;
		iterator++;
		
		String line = firstLine;
		
		while(numOpenedBrackets != 0) {
			//if line end, get new line
			if(iterator == line.length()) {
				iterator = 0;
				if(!input.hasNextLine()) {
					throw new RuntimeException("Not closed brackets before EOF");
				}
				line = input.nextLine();
				//structure of file (new lines) are not lost
				out.append("\n");
			}
			//if line is empty
			if(iterator == line.length())	continue;
			
			if(line.charAt(iterator) == openingChar) {
				numOpenedBrackets++;
			}
				
			if(line.charAt(iterator) == closingChar) {
				numOpenedBrackets--;
			}
			out.append(line.charAt(iterator));
			iterator++;
		}

		return out.toString();
	}
	/**
	 * put name of months to set of strings
	 * @param strings	map of @STRING entries
	 */
	private static void initMonth(Map<String, String> strings) {
		strings.put("jan", "January");
		strings.put("feb", "February");
		strings.put("mar", "March");
		strings.put("apr", "April");
		strings.put("may", "May");
		strings.put("jun", "June");
		strings.put("jul", "July");
		strings.put("aug", "August");
		strings.put("sep", "September");
		strings.put("oct", "October");
		strings.put("nov", "November");
		strings.put("dec", "December");
	}
	/**
	 * Take whole entry(in), start processing from i'th sign
	 * Return fieldType and fieldContent of first field after i'th sign
	 * (in StringBuilder's) and position i after fieldContent 
	 * @param in				whole entry
	 * @param i					position in processing 'in'
	 * @param strings			map of String to parse
	 * @param fieldType			StringBuilder, add to it name of processed field
	 * @param fieldContent		StringBuilder, add to it content of processed field
	 * @return					position i after processing
	 */
	public static int parseField(String in, int i, Map<String, String> strings, StringBuilder fieldType, StringBuilder fieldContent) {
		String notParsed = in.substring(i, in.length());
		String[] splited = notParsed.split("=", 2);
		if(splited.length != 2) return in.length();		//EOF
		fieldType.append(splited[0].replace(" ", "").replace("\n", ""));
		//i += fiedlTypewithWhiteSigns + '=' + content + sign ','
		i += splited[0].length() + 1 + parseContentOfField(splited[1], 0, strings, fieldContent);
		if(i < in.length() && in.charAt(i) == ',')	i++;
		return i;
	}
	/**
	 * Return field content in res and position of i after processing
	 * Assume, that content to parse start on i'th position of in and 
	 * parse, until end of 'in' or sign ','
	 * @param in		whole entry
	 * @param i			position in 'in'
	 * @param strings	map of String to parse
	 * @param res		contentOfField
	 * @return			position of i after processing
	 */
	private static int parseContentOfField(String in, int i, Map<String, String> strings, StringBuilder res) {
		
		while(i < in.length() && in.charAt(i) != ',') {
			for(;i < in.length();i++) {
				if(in.charAt(i) == '#') {		//sign of concatenation
					i++;						//because everything there is concatenating, it can be skipped
				} else {
					if(in.charAt(i) == '{') {	
						i = curlyIn(in, res, i);
						break;
					}
					if(in.charAt(i) == '"') {
						i = quoteIn(in, res, i);
						break;
					}
					if(in.charAt(i) != ' ' && 
							in.charAt(i) != '\n' &&
							in.charAt(i) != '\t' ) {
						i = normalTypeIn(in, res, i, strings);
						break;
					}
				}
			}
		}
		
		return i;
	}
	
	/**
	 * Return well-bracketBalanced result, throw RuntimeException if entry is not well-balanced or "pointer" is not set on '{'
	 * @param in	processing String
	 * @param res	result of method, append well balanced entry
	 * @param i		position in 'in'
	 * @return		position i after processing
	 */
	protected static int curlyIn(String in, StringBuilder res, int i) {
		if(in.length() > i && in.charAt(i) != '{')	throw new RuntimeException("curlyIn: no curly bracket at begin");
		
		int numOpenedBrackets = 1;
		i++;
		while(i < in.length() && numOpenedBrackets != 0) {
			if(in.charAt(i) == '{') {
				numOpenedBrackets++;
			} else {
				if(in.charAt(i) == '}') {
					numOpenedBrackets--;
				} else {
					res.append(in.charAt(i));
				}
			}
			i++;
		}
		
		if(numOpenedBrackets != 0)	throw new RuntimeException("curlyIn: Curly brackets are not ballanced in: " + in);
		
		return i;
	}
	
	/**
	 * Return entry from one '"' sign to next, throw RuntimeException if entry has not next '"' sign
	 * @param in	processing String
	 * @param res	result of method, entry from '"' to next the '"'
	 * @param i		position in 'in'
	 * @return		position i after processing
	 */
	protected static int quoteIn(String in, StringBuilder res, int i) {
		if(in.length() > i && in.charAt(i) != '"')	throw new RuntimeException("quoteIn: no quote at begin");
		i++;
		while(in.length() > i && in.charAt(i) != '"') {
			
			if(in.charAt(i) == '{') {
				i = curlyIn(in, res, i);
			} 
			if(in.charAt(i) == '"') break;
			
			if(in.charAt(i) != '\\')
				res.append(in.charAt(i));
			i++;
		}
		
		if(in.length() == i)	throw new RuntimeException("quoteIn: Quotes are not ballanced in: " + in);
		
		i++;
		return i;
	}
	/**
	 * Take first word after i'th position and if it is number, or it is a key
	 * in strings map, return value from map or this number, in other case throw exception
	 * @param in		string to parse
	 * @param res		returning value
	 * @param i			position in 'in'
	 * @param strings	map of strings
	 * @return			position i after processing
	 */
	protected static int normalTypeIn(String in, StringBuilder res, int i, Map<String, String> strings) {
		
		String result;
		
		StringBuilder name = new StringBuilder();
		//parsing name, it end on white space or ','
		while((i < in.length()) && (in.charAt(i) != ' ' && in.charAt(i) != '\n' && in.charAt(i) != '\t' && in.charAt(i) != ',')) {
			name.append(in.charAt(i));
			i++;
		}
		
		if(strings.containsKey(name.toString())) {
			result = strings.get(name.toString());		//it's key in a map
		} else {
			if(name.toString().chars().allMatch(x -> x > 47 && x < 58)) {
				result = name.toString();				//or it's a number
			} else {
				throw new RuntimeException("normalTypeIn: Bad name in parsing " + name.toString());	//in other case
			}
		}

		res.append(result);
		return i;
	}
	
}
