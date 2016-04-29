import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.Set;

public class HuffmanTree {
	
	public Node root;

	// Combines two nodes and returns the new node
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
		// Now that we a have priority queue of nodes, link them to each other
		for (int i = 0; i < m.size(); i++) {
			Node childNode = getChild(queue); //get the lowest priority thing
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
	
	// Method created for Huffman Tree creation, gets the lowest priority thing and then removes it.
	public Node getChild(PriorityQueue<Node> tree) {
		Node temp = (Node) tree.toArray()[tree.size() - 1];
		tree.remove(tree.size() - 1);
		return temp;
	}
	
	public void encode(BitInputStream in, BitOutputStream out) {
		
	}
	
	public void decode(BitInputStream in, BitOutputStream out) {
		
	}
	
	// Ascending order of values
		private void inorderH(Node node) {
			if (node == null) {
				return;
			}
			inorderH(node.left);
			System.out.print("[" + node.c + ", " + node.v + "]");
			inorderH(node.right);
		}

		public void inorderToString() {
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
		
		t.inorderToString();
	}
	
}