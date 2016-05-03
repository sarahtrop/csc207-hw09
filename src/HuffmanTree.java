import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.Set;

public class HuffmanTree {
	
	private Node root;
	private Map<Short, String> codes;

	/**
	 * Combines two nodes
	 * @param l1	a Node
	 * @param l2	a Node
	 * @return		a Node
	 */
	public Node combine(Node l1, Node l2) {
		return new Node(l1.v + l2.v, (short)-1, l1, l2);
	}
	
	/**
	 * Constructor for a HuffmanTree
	 * @param m	a Map
	 */
	public HuffmanTree(Map<Short, Integer> m) {
		// Create a temporary priority queue to sort the stuff in the Map
		PriorityQueue<Node> queue = new PriorityQueue<>();
		// get all keys in the map
		Set<Short> keys = m.keySet();
		// iterate over the map
		for (Short key : keys) {
			// create a new node with the code and integer
			queue.add(new Node(m.get(key), key, null, null));
		}
		// Now that we a have priority queue of nodes, recursively combine nodes until we have a queue with 1 thing
		root = buildTree(queue);
		codes = new HashMap();
		buildCodes(root, "0");
	}

	/**
	 * Method combines nodes from a PriorityQueue until there is only 1 left, a tree
	 * @param queue	a PriorityQueue
	 * @return		a Node
	 */
	private Node buildTree(PriorityQueue<Node> queue) {
		if (queue.size() == 1) { // if there is only one thing left, return it 
			return queue.poll();
		} else { // otherwise, continue to combine nodes
			Node lowest = queue.poll(); //get the lowest priority thing
			Node nextLowest = queue.poll(); // get the next lowest priority thing
			queue.add(combine(lowest, nextLowest)); // combine and add back into the queue
			return buildTree(queue);
		}
	}

	/**
	 * Encodes a bit stream into a compressed representation
	 * @param in	a BitInputStream
	 * @param out	a BitOutputStream
	 */
	public void encode(BitInputStream in, BitOutputStream out) {
		for (Short c : codes.keySet()) { // for every code
			out.writeBit(c); // write to the out stream
		}
	}
	
	/**
	 * Decodes a bit stream into a compressed representation
	 * @param in	a BitInputStream
	 * @param out	a BitOutputStream
	 */
	public void decode(BitInputStream in, BitOutputStream out) {
		
	}
	
	/**
	 * Finds the codes for the characters in the tree
	 * @param node
	 * @param code
	 */
	private void buildCodes(Node parent, String code) {
		System.out.print("Parent:" + parent.c + ", " + parent.v);
		Node left = parent.left;
		System.out.print(" Left:" + left.c + ", " + left.v);
		Node right = parent.right;
		System.out.print(" Right:" + right.c + ", " + right.v);
		System.out.println(" Code: " + code);
		
		if (left == null && right == null) {
			codes.put(parent.c, code);
		}
		
		// If either child is the last one
		if (isInnerNode(left) || isInnerNode(right)) {
			// If the left is the last one
			if (isInnerNode(left)) {
				// Store the code and iterate down the right side
				System.out.println(code);
				codes.put(left.c, code);
				buildCodes(right, code + "1");
			// If the right is the last one
			} else {
				// Store the code and iterate down the left side
				System.out.println(code);
				codes.put(right.c, code);
				buildCodes(left, code + "0");
			}
		// Otherwise, go down both sides
		} else {
			buildCodes(left, code + "0");
			buildCodes(right, code + "1");
		}
	}
	
	/**
	 * Method that checks if the node is an inner node, without a character
	 * @param node	a Node
	 * @return		a boolean
	 */
	private boolean isInnerNode(Node node) {
		return node.v == -1;
	}
	
	/**
	 * Helper method to print a tree in order 	
	 * @param node	a Node
	 */
	private void preorder(Node node) {
		if (node == null) {
			return;
		}
		System.out.print("[" + node.c + ", " + node.v + "]");
		preorder(node.left);
		preorder(node.right);
	}

	/**
	 * Calls helper method to print a tree in order
	 */
	public void treeToString() {
		preorder(root);
	}
	
	public static void main(String[] args) {
		Map<Short, Integer> m = new HashMap<>();
		m.put((short)'z', 1);
		m.put((short)'a', 3);
		m.put((short)' ', 2);
		m.put((short)'b', 2);
		m.put((short)001, 1);
		
		HuffmanTree t = new HuffmanTree(m);
		t.treeToString();
		//System.out.println(Arrays.toString(t.codes.entrySet().toArray()));
	}
	
}