package bibParser;

import java.lang.reflect.Field;
import java.util.List;
/**
 * Contain a method to print list objects type Paper
 * @author rafal
 *
 */
public class paperPrinter {
	/**
	 * Print on standard output list of Papers
	 * @param papers
	 * @param frameSign
	 */
	public static void print(List<Paper> papers, char frameSign) throws Exception {
		
		//preparing horizontal part of frame
		StringBuilder framePart = new StringBuilder();
		for(int i = 0 ; i < 25 ; i++ )	framePart.append(frameSign);

		Class<?> cl = Paper.class;	//getting paper class
		Field[] fields = cl.getDeclaredFields();
		
		for(Object p : papers.toArray()) {
			System.out.println(framePart.toString());
			
			
			for(Field f : fields) {
				if(f.getName().equals("obligatoryFields") ||
						f.getName().equals("optionalFields") ||
						f.getName().equals("possibleFields"))	continue;
				f.setAccessible(true);
				Object content = f.get(p);
				if(content != null) {
					System.out.println(frameSign + " " + f.getName() + ": " + content.toString() + "\t");
				}
			}
			System.out.println(framePart.toString());
		}

	}
}
