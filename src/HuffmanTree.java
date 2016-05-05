import java.io.IOException;
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
		// -1 is the inner node character
		return new Node(l1.freq + l2.freq, (short)-1, l1, l2);
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
		queue.add(new Node(1, (short)256, null, null)); // EOF character
		// Now that we a have priority queue of nodes, recursively combine nodes until we have a queue with 1 thing
		root = buildTree(queue);
		codes = new HashMap<>();
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
		System.out.println("encode");
		short val;
		while(in.hasBits()) { // Until there are no more bits
			val = (short)in.readBits(8); // Read the next character
			//System.out.println(val);
			String code = codes.get(val); // Get the Huffman code for that character
			System.out.println("code " + code);
			if (code != null) {
				for (int i = 0; i < code.length(); i++) { // Write each bit into the out file
					//System.out.print(Integer.parseInt(code.substring(i, i+1)));
					out.writeBit(Integer.parseInt(code.substring(i, i+1)));
				}
			}
		}
		System.out.println("");
		in.close();
		out.close();
	}
	
	/**
	 * Finds the codes for the characters in the tree
	 * @param node
	 * @param code
	 */
	private void buildCodes(Node node, String code) {
		// If the node is an inner node, recur. 
		if (node.c == -1) {
			buildCodes(node.left, code + "0");
			buildCodes(node.right, code + "1");
		// Otherwise, add the code to the map
		} else {
			codes.put(node.c, code);
		}
	}
	
	/**
	 * Decodes a bit stream into a compressed representation
	 * @param in	a BitInputStream
	 * @param out	a BitOutputStream
	 */
	public void decode(BitInputStream in, BitOutputStream out) {
		String code = new String();
		System.out.println("decode");
		while(in.hasBits()) { // until there are no more bits
			 code += "" + in.readBit();
			if (codes.containsValue(code)) { // If the code is valid
				// MUST FIND EOF CODE AND EXIT IF FOUND
				for (int i = 0; i < code.length(); i++) { // Write each bit into the out file
					System.out.print(Integer.parseInt(code.substring(i, i+1)));
					out.writeBit(Integer.parseInt(code.substring(i, i+1)));
				}
				code = new String(); // Reset code
			}
		}
		in.close();
		out.close();
	}
	
	// temporary method DELETE WHEN DONE
	public static Map<Short, Integer> createFrequencyMap(BitInputStream in) throws IOException {
		Map<Short, Integer> ret = new HashMap<>();
		int freqArr[] = new int[26]; // Array of frequencies
		
		// Iterate over the file, counting the characters
		int c = in.readBits(8);
		while (c != -1) {
			int index = (char)c - 'a';
			if (index < 26 && index >= 0) { freqArr[index]++; }
			c = in.readBits(8);
		}
		
		// Put the characters and their frequencies in the map
		for (int i = 0; i < 26; i++) {
			if (freqArr[i] != 0) { ret.put((short)((char)i + 'a'), freqArr[i]); }
		}
		return ret;
	}
	
	// temporary method DELETE WHEN DONE
	public static void main(String[] args) throws IOException {
		Map<Short, Integer> m = createFrequencyMap(new BitInputStream("data/original.txt"));
		HuffmanTree t = new HuffmanTree(m);

		t.encode(new BitInputStream("data/original.txt"), new BitOutputStream("data/encoded.txt"));
		t.decode(new BitInputStream("data/encoded.txt"), new BitOutputStream("data/decoded.txt"));
	}
	
}