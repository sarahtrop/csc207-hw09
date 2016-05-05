import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrinDecoder {

	/**
	 * Decides a .grin file
	 * @param infile	a file
	 * @param outfile	a file
	 * @throws IOException
	 */
	public static void decode(String infile, String outfile) throws IOException {
		BitInputStream in = new BitInputStream(infile);
		BitOutputStream out = new BitOutputStream(outfile);

		// Parse header
		in = checkGrin(in);

		// Get the number of codes
		int numCodes = in.readBits(32);
		
		// Build the map
		Map<Short, Integer> map = new HashMap<>();
		for (int i = 0; i < numCodes; i++) {
			int code = in.readBits(16);
			int occurences = in.readBits(32);
			map.put((short) code, occurences);
		}
		for (short k : map.keySet()) {
			System.out.println(k + ":" + map.get(k));
		}

		// Construct a Huffman Tree from the map
		HuffmanTree tree = new HuffmanTree(map);
		tree.decode(in, out);
		
		in.close();
		out.close();
	}
	
	/**
	 * Parse the magic number and f the file is not a .grin file, exit
	 * @return	a BitInputStream
	 */
	public static BitInputStream checkGrin(BitInputStream in) {
		int magicNum = in.readBits(32);
		if (magicNum != 1846) { throw new IllegalArgumentException(); }
		return in;
	}
}
