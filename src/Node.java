
public class Node implements Comparable<Node> {
	
	public int val;
	public short code;
	public Node left;
	public Node right;
	
	/**
	 * Constructor for Node class
	 * 
	 * @param v		an integer
	 * @param code	a short, representing the binary code
	 * @param l		a Node
	 * @param r		a Node
	 */
	public Node (int v, short code, Node l, Node r) {
		this.val = v;
		this.code = code;
		this.left = l;
		this.right = r;
	}

	public int compareTo(Node o) {
		if (this.val > o.val) { return 1; }
		else { return -1; }
	}

}
