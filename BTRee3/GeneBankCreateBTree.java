import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

/**
 * Creates a BTree from a gbk file.
 * 
 * @author 
 * 
 */
public class GeneBankCreateBTree {

	/****************************************
	 * Variables
	 ****************************************/

	public static final long CODE_A = 00;
	public static final long CODE_T = 11;
	public static final long CODE_C = 01;
	public static final long CODE_G = 10;

	public static final int MAX_SEQUENCE_LENGTH = 31;
	public static final int MAX_DEBUG_LEVEL = 1;

	/****************************************
	 * Main method
	 ****************************************/

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		// Print usage if 
		if (args.length < 4) {
			printUsage();
		}

		int BTreeDegree = 0;

		// Checks specified degree
		try {
			int deg = Integer.parseInt(args[1]);
			if (deg < 0) printUsage();
			else if (deg == 0) BTreeDegree = findOptimalDegree();
			else BTreeDegree = deg;
		} catch (NumberFormatException e) {
			printUsage();
		} 

		int sequenceLength = 0;

		// Checks sequence length
		try {
			int len = Integer.parseInt(args[3]);
			if (len < 1 || len > MAX_SEQUENCE_LENGTH) printUsage();
			else sequenceLength = len;
		} catch (NumberFormatException e) {
			printUsage();
		}
		
		byte debug = 0x00;
		byte cache = 0x00; 
		
		int cacheSize = Integer.parseInt(args[4]);  //cacheSize
		debug = (byte) Integer.parseInt(args[5]);
		cache = (byte) Integer.parseInt(args[0]);
		
		@SuppressWarnings("rawtypes")
		Cache List = null;
		boolean useCache = false;
		
		
		if(cache == 0x01)
		{
			List = new Cache<String>(cacheSize); //create the cache
			useCache = true;
		}

		// Create File object from specified file path
		File gbk = new File(args[2]);

		// Create scanner to go through file
		Scanner in = null;
		try {
			in = new Scanner(gbk);
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + gbk.getPath());
		}

		// Create BTree with given parameters
		BTree tree = new BTree(BTreeDegree);

		String line = null;
		line = in.nextLine().toLowerCase().trim();

		boolean inSequence = false;
		int sequencePosition = 0;
		int charPosition = 0;
		long sequence = 0;
		long treeInput;
		String keyString = "";

		while (line != null) { 
			if (inSequence) { 
				// End of the sequence
				if (line.startsWith("//")) { 
					inSequence = false;
					sequence = 0;
					sequencePosition = 0;
				} else {

					while (charPosition < line.length()) {
						char c = line.charAt(charPosition++);
						switch (c) {
						case 'a':
							sequence = ((sequence<<2) | CODE_A);
							keyString = keyString + "00";
							if (sequencePosition < sequenceLength) sequencePosition++;
							break;
						case 't':
							sequence = ((sequence<<2) | CODE_T);
							keyString = keyString + "11";
							if (sequencePosition < sequenceLength) sequencePosition++;
							break;
						case 'c':
							sequence = ((sequence<<2) | CODE_C);
							keyString = keyString + "01";
							if (sequencePosition < sequenceLength) sequencePosition++;
							break;
						case 'g':
							sequence = ((sequence<<2) | CODE_G);
							keyString = keyString + "10";
							if (sequencePosition < sequenceLength) sequencePosition++;
							break;

							// End of subsequence
						case 'n': 
							sequencePosition = 0;
							sequence = 0;
							keyString = "";
							continue; 

							// Not part of sequence (space, number, etc.)
						default: 
							continue;
						} 

						if (sequencePosition >= sequenceLength) {
							tree.insert(sequence);
							treeInput = Long.parseLong(keyString, 2);
							
							if(useCache == true) {
								List.cacheAdd(treeInput);
							}
						}
					}
				} 
			} 

			// Beginning of a sequence 
			else if (line.startsWith("ORIGIN")) { 
				inSequence = true;
			} 

			// Sets next line to null if there is no next line.
			if (in.hasNextLine()) {
				line = in.nextLine();
			} else {
				line = null;
			}

			charPosition = 0;
		} 

		// Prints a debug dump if applicable
		if (debug == 0x01) {
			PrintWriter writer = new PrintWriter("dump.txt");
		
	    	writer.println(tree.debugFile());
	    	writer.close();
		} 
		
		if(useCache == true)
			List.cacheResults();

		in.close();
	}

	/****************************************
	 * Utility methods
	 ****************************************/

	/**
	 * Finds and returns optimal degree for B-Tree. 
	 * @return degree 
	 * 
	 */
	public static int findOptimalDegree() {
		double degree;
		int pointerSize = 4;
		int objectSize = 12;
		int metaSize = 5;
		double blockSize = 4096;

		degree = blockSize;
		degree += objectSize;
		degree -= pointerSize;
		degree -= metaSize;
		degree /= (2 * (objectSize + pointerSize));

		int result = (int) Math.floor(degree);

		return result;
	}

	/**
	 * Prints usage message. 
	 * 
	 */
	private static void printUsage() {
		StringBuilder usageMessage = new StringBuilder();
		usageMessage.append("Usage: java GeneBankCreateBTree <cache> <degree> <gbk file> <sequence length> [<cache size>] [<debuglevel>]");
		usageMessage.append("\n");
		usageMessage.append("<cache>: 1 or 0");
		usageMessage.append("\n");
		usageMessage.append("<degree>: degree of BTree (0 for default)");
		usageMessage.append("\n");
		usageMessage.append("<gbk file>: gene bank file");
		usageMessage.append("\n");
		usageMessage.append("<sequence length>: length of a sequence (1-31)");
		usageMessage.append("\n");
		usageMessage.append("[<cache size>]: size of cache (if applicable)");
		usageMessage.append("\n");
		usageMessage.append("[<debug level>]: debug level (0-1)");
		usageMessage.append("\n");

		System.err.println(usageMessage.toString());
		System.exit(1);
	} 
} 
