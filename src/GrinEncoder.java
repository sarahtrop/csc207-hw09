import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GrinEncoder {

	public static void encode(String infile, String outfile) throws IOException {
		BitInputStream in = new BitInputStream(infile);
		BitOutputStream out = new BitOutputStream(outfile);
		
		// Build map
		Map<Short, Integer> map = createFrequencyMap(in);
		
		// Write the magic number to the output file
		out.writeBits(1846, 32);
		// Write the number of codes to the output file
		out.writeBits(map.size(), 32);
		// Write the frequency map to the output file
		writeFrequencyMap(map, out);
		
		// Construct HuffmanTree and encode
		HuffmanTree tree = new HuffmanTree(map);
		tree.encode(new BitInputStream(infile), out);
		
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
		
		// Iterate over the file, counting the characters
		int c = in.readBits(8);
		while(in.hasBits()) {
			if (ret.containsKey(c)) {
				ret.put((short)c, ret.get(c) + 1);
			} else {
				ret.put((short)c, 1);
			}
			c = in.readBits(8);
		}
		return ret;
	}

	public static void writeFrequencyMap(Map<Short, Integer> map, BitOutputStream out) {
		for(Short key : map.keySet()) {
			out.writeBits(key, 16);
			out.writeBits(map.get(key), 32);
		}
	}
	
}
