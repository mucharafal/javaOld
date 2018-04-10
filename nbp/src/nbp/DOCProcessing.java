package nbp;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * bunch of functions to process a type Document objects
 * @author rafal
 *
 */
public class DOCProcessing {
	/**
	 * Print DOC tree, show also depth of recursion
	 * @param n		node of tree
	 */
	public static void printDocument(Node n) {
		printDocumentMasked(n, 0);
	}
	/**
	 * Print DOC tree, show also depth of recursion
	 * @param n				node of tree
	 * @param recDepth		depth of recursion (first invoke = 0)
	 */
	private static void printDocumentMasked(Node n, int recDepth) {
		if(n.getNodeType() == 1) {
			System.out.println("Type " + n.getNodeName() + " " + recDepth);
		}
		if(n.getNodeType() == 3) {
			System.out.println("Value " + n.getNodeValue() + " " + recDepth);
		}
		
		NodeList l = n.getChildNodes();
		for(int i = 0;i < l.getLength(); i++) {
			printDocumentMasked(l.item(i), recDepth + 1);
		}
	}
	
	public static boolean equals(Node n1, Node n2) {
		return n1.isEqualNode(n2);
	}
	
	/**
	 * Return node, which type is equal to type parameter
	 * @param c		Node, node of tree
	 * @param type	
	 * @return		Node, which node.type.equals(type), or null if there is not such node in  tree
	 */
	public static Node getNodeWithType(Node c, String type) {
		List <Node> toRes = getAllNodesWithType(c, type);
		if(toRes.size() != 1) {
			throw new RuntimeException("Incorrect DOC file");
		}
		return toRes.get(0);
	}
	
	public static List<Node> getAllNodesWithType(Node c, String type) {
		List<Node> res = new LinkedList<Node>();
		if(!c.getNodeName().equals(type) && !c.hasChildNodes()) {
		} else {
			if(!c.getNodeName().equals(type)) {
				for(int i = 0; i < c.getChildNodes().getLength(); i++) {
					List<Node> n = getAllNodesWithType(c.getChildNodes().item(i), type);
					if(!n.isEmpty()) {
						res.addAll(n);
					}
				}
			} else {
				res.add(c);
			}
		}
		return res;
	}
}
