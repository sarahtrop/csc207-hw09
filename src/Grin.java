import java.io.IOException;

public class Grin {
	
	public static void main(String[] args) throws IOException {
		String codeDir = args[0];
		String infile = args[1];
		String outfile = args[2];
		
		if (codeDir.equals("encode")) {
			GrinEncoder.encode(infile, outfile);
		} else if (codeDir.equals("decode")) {
			GrinDecoder.decode(infile, outfile);
		} else {
			System.out.println("Error, must choose encode or decode");
			return;
		}
	}
}
