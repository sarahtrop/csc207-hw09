import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.Set;

public class HuffmanTree {
	
	public Node root;

	/**
	 * Combines two nodes
	 * @param l1	a Node
	 * @param l2	a Node
	 * @return		a Node
	 */
	public Node combine(Node l1, Node l2) {
		return new Node(l1.v + l2.v, (short)0, l1, l2);
	}
	
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
	}
	
	/**
	 * Method combines nodes from a PriorityQueue until there is only 1 left, a tree
	 * @param queue	a PriorityQueue
	 * @return		a Node
	 */
	public Node buildTree(PriorityQueue<Node> queue) {
		if (queue.size() == 1) { // if there is only one thing left, return it 
			return queue.poll();
		} else { // otherwise, continue to combine nodes
			Node lowest = next(queue); //get the lowest priority thing
			Node nextLowest = next(queue); // get the next lowest priority thing
			queue.add(combine(lowest, nextLowest));
			return buildTree(queue);
		}
	}
	
	/**
	 * Method gets the lowest priority item in a PriorityQueue
	 * @param queue	a PriorityQueue
	 * @return		a Node
	 */
	public Node next(PriorityQueue<Node> queue) {
		Node temp = (Node) queue.toArray()[queue.size() - 1];
		queue.remove(queue.size() - 1);
		return temp;
	}
	
	/**
	 * Encodes a bit stream into a compressed representation
	 * @param in	a BitInputStream
	 * @param out	a BitOutputStream
	 */
	public void encode(BitInputStream in, BitOutputStream out) {
		
	}
	
	/**
	 * Decodes a bit stream into a compressed representation
	 * @param in	a BitInputStream
	 * @param out	a BitOutputStream
	 */
	public void decode(BitInputStream in, BitOutputStream out) {
		
	}
	
	/**
	 * Helper method to print a tree in order 	
	 * @param node	a Node
	 */
	private void inorderH(Node node) {
		if (node == null) {
			return;
		}
		inorderH(node.left);
		System.out.print("[" + node.c + ", " + node.v + "]");
		inorderH(node.right);
	}

	/**
	 * Calls helper method to print a tree in order
	 */
	public void treeToString() {
		inorderH(root);
	}
	
	public static void main(String[] args) {
		Map<Short, Integer> m = new HashMap<>();
		m.put((short)'z', 1);
		m.put((short)'a', 3);
		m.put((short)' ', 2);
		m.put((short)'b', 2);
		m.put((short)001, 1);
		
		System.out.println(Arrays.toString(m.entrySet().toArray()));
		
		HuffmanTree t = new HuffmanTree(m);
		
		t.treeToString();
	}
	
}