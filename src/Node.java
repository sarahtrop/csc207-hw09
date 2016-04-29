
public class Node implements Comparable<Node> {
	
	public int v;
	public short c;
	public Node left;
	public Node right;

	public Node (int v, short c, Node l, Node r) {
		this.v = v;
		this.left = l;
		this.right = r;
		this.c = c;
	}

	public int compareTo(Node o) {
		if (this.v > o.v) { return 1; }
		else { return -1; }
	}

}
