import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrinEncoder {

	public static void encode(String infile, String outfile) throws IOException {
		BitInputStream in = new BitInputStream(infile);
		BitOutputStream out = new BitOutputStream(outfile);
		
		// Build map
		Map<Short, Integer> map = createFrequencyMap(in);
		
		// Write the magic number to the output file
		out.writeBit(1846);
		// Write the number of codes to the output file
		out.writeBit(map.size());
		// Write the frequency map to the output file
		writeFrequencyMap(map, out);
		
		// Construct HuffmanTree and encode
		HuffmanTree tree = new HuffmanTree(map);
		tree.encode(in, out);
		
		in.close();
		out.close();
	}
	
	/**
	 * Builds a map from a BitInputStream
	 * @param in	a BitInputStream
	 * @return		a Map of shorts and ints
	 * @throws IOException 
	 */
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

	public static void writeFrequencyMap(Map<Short, Integer> map, BitOutputStream out) {
		for(Short key : map.keySet()) {
			out.writeBit(key);
			out.writeBit(map.get(key));
		}
	}
	
}
