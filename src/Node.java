
public class Node implements Comparable<Node> {
	
	public int freq;
	public short c;
	public Node left;
	public Node right;

	public Node (int freq, short c, Node l, Node r) {
		this.freq = freq;
		this.left = l;
		this.right = r;
		this.c = c;
	}

	public int compareTo(Node o) {
		if (this.freq > o.freq) { return 1; }
		else { return -1; }
	}

}
