package bibParser;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Class contain:
 * methods - constructor, getters and containField(Field is not null) methods
 * fields - three static map with information about fields in depend on type,
 * rest fields are fields of one "paper" i.e book or article - entries of bibTex file
 * @author rafal
 *
 */
public class Paper {
	
	private static Map<String, String[][]> obligatoryFields = new TreeMap<String, String[][]>();
	private static Map<String, String[]> optionalFields = new TreeMap<String, String[]>();
	//possibleFields = obligatoryFields + optionalFields
	private static Map<String, List<String>> possibleFields = new TreeMap<String, List<String>>();
	private String author, title, booktitle,
	publisher, editor, volume, series, typePaper, type,
	address, edition, note, journal, organization, crossref,
	howpublished, school, key, number, chapter, pages, month, year, institution;
	/**
	 * Create an paper object and add it to Author and Category maps
	 * @param typeIn		type of Paper, i.e Book or Article
	 * @param in			whole entry, from { to } sign
	 * @param strings		map of String type
	 * @throws Exception
	 */
	public Paper(String typeIn, String in, Map<String, String> strings) throws Exception {
		//reflection is used to get the field
		Class<?> cl = this.getClass();
		
		//deleting { and } or ( and )
		StringBuilder toClean = new StringBuilder();
		toClean.append(in);
		toClean.deleteCharAt(0);
		toClean.deleteCharAt(toClean.length() - 1);
		in = toClean.toString();
		
		typePaper = typeIn;
		
		StringBuilder fieldName = new StringBuilder();
		StringBuilder fieldContent = new StringBuilder();
		int i = 0;
		
		//key field parsing
		//assume, that entry looks: @Type{Key, ...
		while(in.charAt(i) != ',') {
			fieldContent.append(in.charAt(i));
			i++;
		}
		i++;
		key = fieldContent.toString();
		fieldContent = new StringBuilder();
		
		//parsing rest of fields
		for(; i < in.length();i++) {
			//parsing field
			fieldName = new StringBuilder();
			fieldContent = new StringBuilder();
			i = Parser.parseField(in, i, strings, fieldName, fieldContent);
			//it can be false, when last entry end ',' sign
			if(!fieldName.toString().equals("")) {
				//parsing field
				Field[] fields = cl.getDeclaredFields();
				String[] fieldNames = Arrays.stream(fields).map(x -> x.getName()).toArray(size -> new String[size]);
				if(Arrays.asList(fieldNames).contains(fieldName.toString().toLowerCase())) {
					Field at = cl.getDeclaredField(fieldName.toString().toLowerCase());
					at.set(this, fieldContent.toString());
				} else {
					//there an exception should be thrown, but it will break parse process
					System.out.println("Field type: " + fieldName.toString() + " doesn't exist!");
				}
			}
		}
		
		//*********************************************************
		//parsing fields finished, now checking if they are correct
		
		if(obligatoryFields.isEmpty())	initPaper();

		String[][] obligatory = obligatoryFields.get(this.typePaper);
		boolean isCorrect = true;
		
		if(obligatory == null) System.out.println("Id¿ graj w WOTa");
		
		for(String[] f : obligatory) {
			//its enough if exist only one obligatory field, i.e Author or Editor
			boolean contain = false;
			for(String c : f ) {
				Field at = cl.getDeclaredField(c);
				if(at.get(this) != null) {
					contain = true;
				}
			}
			if(!contain)	isCorrect = false;
		}
		
		List<String> possible = possibleFields.get(this.typePaper);
		Field[] fields = cl.getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			if(field.getName().equals("possibleFields") ||
					field.getName().equals("obligatoryFields") ||
					field.getName().equals("optionalFields") ||
					field.getName().equals("typePaper")) {
				continue;
			} else {
				if( field.get(this) != null && !possible.contains(field.getName())) {
					isCorrect = false;
				}
			}
		}
		
		//there an exception should be thrown, but it is not to break processing 
		if(!isCorrect)	System.out.println("Incorrect fields, or obligatory fields are empty! Type:" + this.typePaper + " key: " + this.key);
		
		Category.add(typePaper, this);
		
		//to avoid breaking parsing process, an exception is handled
		try {
			Author.add(this);
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Initialize sets: obligatoryFields, optionalFields and possibleFields
	 */
	public static void initPaper() {
		obligatoryFields.put("ARTICLE", new String[][]{{"author"},{"title"},{"journal"},{"year"}});
		optionalFields.put("ARTICLE", new String[]{"volume","number","pages","month","note","key"});
		
		obligatoryFields.put("BOOK", new String[][]{{"author","editor"},{"title"},{"publisher"},{"year"}});
		optionalFields.put("BOOK", new String[] {"volume", "series", "address", "edition", "month", "note", "key"});
		
		obligatoryFields.put("INPROCEEDINGS", new String[][]{{"author"},{"title"},{"booktitle"},{"year"}});
		optionalFields.put("INPROCEEDINGS", new String[]{"editor", "volume", "number", "series", "pages", "address", "month", "organization", "publisher", "note", "key"});
		
		obligatoryFields.put("CONFERENCE", new String[][]{{"author"},{"title"},{"year"},{"booktitle"}});
		optionalFields.put("CONFERENCE", new String[]{"editor", "volume", "number", "series", "pages", "address", "month", "organization", "publisher", "note", "key"});
		
		obligatoryFields.put("BOOKLET", new String[][]{{"title"}});
		optionalFields.put("BOOKLET", new String[] {"author", "howpublished", "address", "month", "year", "note", "key"});
		
		obligatoryFields.put("INBOOK", new String[][]{{"author","editor"},{"title"},{"publisher"},{"year"},{"chapter","pages"}});
		optionalFields.put("INBOOK", new String[] {"volume", "number", "series", "type", "address", "edition", "month", "note", "key"});
		
		obligatoryFields.put("INCOLLECTION", new String[][]{{"author"},{"title"},{"booktitle"},{"publisher"},{"year"}});
		optionalFields.put("INCOLLECTION", new String[] {"editor", "volume", "number", "series", "type", "address", "edition", "month", "note", "key"});
		
		obligatoryFields.put("MANUAL", new String[][]{{"title"}});
		optionalFields.put("MANUAL", new String[] {"author", "organization", "address", "edition", "month", "year", "note", "key"});
		
		obligatoryFields.put("MASTERTHESIS", new String[][]{{"author"},{"title"},{"school"},{"year"}});
		optionalFields.put("MASTERTHESIS", new String[] {"type", "address", "month", "note", "key"});
		
		obligatoryFields.put("PHDTHESIS", new String[][]{{"author"},{"title"},{"school"},{"year"}});
		optionalFields.put("PHDTHESIS", new String[] {"type", "address", "month", "note", "key"});
		
		obligatoryFields.put("TECHREPORT", new String[][]{{"author"},{"title"},{"institution"},{"year"}});
		optionalFields.put("TECHREPORT", new String[] {"editor", "volume", "number", "series", "address", "month", "organization", "publisher", "note", "key"});
		
		obligatoryFields.put("MISC", new String[][]{});
		optionalFields.put("MISC", new String[] {"author", "title", "howpublished", "month", "year", "note", "key"});
		
		obligatoryFields.put("UNPUBLISHED", new String[][]{{"author"},{"title"},{"note"}});
		optionalFields.put("UNPUBLISHED", new String[] {"month", "year", "key"});
		
		//possibleFields = obligatoryFields + optionalFields
		Set<String> fieldNames = obligatoryFields.keySet();
		for(String fieldName : fieldNames) {
			possibleFields.put(fieldName, new LinkedList<String>());
			List<String> toPut = possibleFields.get(fieldName);
			for(String[] fields : obligatoryFields.get(fieldName)) {
				for(String field : fields) {
					toPut.add(field);
				}
			}
			for(String field: optionalFields.get(fieldName)) {
				toPut.add(field);
			}
		}
		
	}

	public String getAuthor() {
		return author;
	}
		
	public String getTitle() {
		return title;
	}
	public String getBooktitle() {
		return booktitle;
	}
	public String getPublisher() {
		return publisher;
	}
	public String getEditor() {
		return editor;
	}
	public String getVolume() {
		return volume;
	}
	public String getSeries() {
		return series;
	}
	public String getType() {
		return typePaper;
	}
	public String getAddress() {
		return address;
	}
	public String getEdition() {
		return edition;
	}
	public String getNote() {
		return note;
	}
	public String getJournal() {
		return journal;
	}
	public String getOrganization() {
		return organization;
	}
	public String getHowpublished() {
		return howpublished;
	}
	public String getSchool() {
		return school;
	}
	public String getKey() {
		return key;
	}
	public String getYear() {
		return year;
	}
	public String getNumber() {
		return number;
	}
	public String getChapter() {
		return chapter;
	}
	public String getPages() {
		return pages;
	}
	public String getMonth() {
		return month;
	}
	public Boolean containAuthor() {
		return author != null;
	}
	public Boolean containTitle() {
		return title != null;
	}
	public Boolean containBooktitle() {
		return booktitle != null;
	}
	public Boolean containPublisher() {
		return publisher != null;
	}
	public Boolean containEditor() {
		return editor != null;
	}
	public Boolean containVolume() {
		return volume != null;
	}
	public Boolean containSeries() {
		return series != null;
	}
	public Boolean containtypePaper() {
		return typePaper != null;
	}
	public Boolean containAddress() {
		return address != null;
	}
	public Boolean containEdition() {
		return edition != null;
	}
	public Boolean containNote() {
		return note != null;
	}
	public Boolean containJournal() {
		return journal != null;
	}
	public Boolean containOrganization() {
		return organization != null;
	}
	public Boolean containHowpublished() {
		return howpublished != null;
	}
	public Boolean containSchool() {
		return school != null;
	}
	public Boolean containKey() {
		return key != null;
	}
	public Boolean containYear() {
		return year != null;
	}
	public Boolean containNumber() {
		return number != null;
	}
	public Boolean containChapter() {
		return chapter != null;
	}
	public Boolean containPages() {
		return pages != null;
	}
	public Boolean containMonth() {
		return month != null;
	}
}

