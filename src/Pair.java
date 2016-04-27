
public class Pair implements Comparable<Pair> {
	
	public short fst;
	public int snd;
	
	public Pair(short fst, int snd) {
		this.fst = fst;
		this.snd = snd;
	}
	
	public short getFst() {
		return fst;
	}
	
	public int getSnd() {
		return snd;
	}

	public int compareTo(Pair o) {
		if (this.snd > o.snd) { return 1; }
		else { return -1; }
	}
	
}
