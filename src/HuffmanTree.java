import java.util.Map;
import java.util.Set;

public class HuffmanTree {
	
	public Node root;

	// Combines two nodes and returns the new node
	public Node combine(Node node1, Node node2) {
		return new Node(node1.val + node2.val, (short) (node1.code + node2.code), null, null);
	}
	
	public HuffmanTree(Map<Short, Integer> m) {
		// Create a temporary priority queue to sort the stuff in the Map
		PriorityQueue<Node> queue = new PriorityQueue<>();
		// get all keys in the map
		Set<Short> keys = m.keySet();
		// iterate over the map
		for (int i = 0; i < m.size(); i++) {
			// begin with the lowest priority thing
			short key = findNextLowestKey(m, keys);
			keys.remove(key);
			// create a new node with the code and integer
			Node tempNode = new Node(m.get(key), key, null, null);
			// add node to the queue
			queue.enqueue(tempNode);
		}
		// Now that we a have priority queue of nodes, link them to each other
		for (int i = 0; i < m.size(); i++) {
			Node childNode = queue.getChild(); //get the lowest priority thing
			if (root == null) { root = childNode; } // if the queue is null, make the root the new node
			else if (root.left == null) { // if the left node is null, combine and add it to the left
				root = combine(root, childNode);
				root.left = childNode;
			} else {  // if the right node is null, combine and add it to the right
				root = combine(root, childNode);
				root.right = childNode;
			}
		}
	}
	
	// method that finds the lowest priority thing in the map
	public short findNextLowestKey(Map<Short, Integer> m, Set<Short> keys) {
		short ret = (short) keys.toArray()[0];
		int lowest = m.get(ret);
		// iterate through the map, find the lowest priority thing
		for (short key : keys) {
			if (m.get(key) < lowest) { 
				lowest = m.get(key);
				ret = key; 
			}
		}
		return ret;
	}
	
	public void encode(BitInputStream in, BitOutputStream out) {
		
	}
	
	public void decode(BitInputStream in, BitOutputStream out) {
		
	}
	
}