import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class GeneBankCreateBTree{

	static String gbkFilename;
	static Cache cache;
	static int cacheArg, deg, sequenceSize, cacheSize, debug;
	
	public static void main(String args[]){
		
		parseArgs(args);
		
		
		//Method that creates the btree with dna sequences
		try {
			BTree tree;
			File gbkFile = new File(gbkFilename);
			BufferedReader gbkInput = new BufferedReader(new FileReader(gbkFile));
			
			if (cacheArg == 1) {				
				tree = new BTree(deg, sequenceSize, gbkFilename, cache);
			}
			else{
				tree = new BTree(deg, sequenceSize, gbkFilename, cache);
			}
			
			StringBuilder fullSequence = new StringBuilder("Start");
			
			while (fullSequence != null) {
				fullSequence = parseGbkFile(gbkInput);
				if (fullSequence != null) {
					int seqLength = sequenceSize;
					int endOfSubseq = fullSequence.length() - seqLength;
					String subSequence;
					long convertedSequence;
					for (int i = 0; i < endOfSubseq; i++) {	
						subSequence = fullSequence.substring(i, i + seqLength);
						if (subSequence.contains("N")) {
							subSequence = null;
						}
						else {
							convertedSequence = tree.sequenceToLong(subSequence);
							tree.insert(convertedSequence);
						}
					}
				}
			}
			gbkInput.close();
		
			if (cacheArg == 1) tree.writeCache();
			
			System.out.println("The B-Tree was created successfully!");
			System.out.println("The following files were created.");
			System.out.println("Metadata file: " + gbkFilename + ".btree.metadata." + sequenceSize  + "." + deg );
			System.out.println("B-Tree binary file: "  + gbkFilename + ".btree.data." + sequenceSize  + "." + deg);

			//Set debug argument
			if (debug == 1) {
				PrintStream outputFile = new PrintStream(new File("dump.txt"));
				PrintStream console = System.out;
				System.setOut(outputFile);
				tree.printToFile(tree.root, true);
				System.setOut(console);
				System.out.println("B-Tree file: dump.txt");
			}	
			
		} catch (IOException e) {
			System.out.println("Error ");
			e.printStackTrace();
		}
	}

	//parse the file
	private static StringBuilder parseGbkFile(BufferedReader gbkInput) {		
		String dnaSequence = null;
		StringBuilder fullSequence = null;
				
		try {
			
			do{
				dnaSequence = gbkInput.readLine();
			}while(dnaSequence != null && !dnaSequence.startsWith("ORIGIN"));
			
			if (dnaSequence == null)
				return fullSequence;
			
			char inputChar = 0;
			fullSequence = new StringBuilder();
			while(inputChar != '/'){
				inputChar = (char) gbkInput.read();
				inputChar = Character.toUpperCase(inputChar);

				switch(inputChar){
				case 'A':
					fullSequence.append(Character.toString(inputChar));
					break;
				case 'T':
					fullSequence.append(Character.toString(inputChar));
					break;
				case 'C':
					fullSequence.append(Character.toString(inputChar));;
					break;
				case 'G':
					fullSequence.append(Character.toString(inputChar));
					break;
				case 'N':
					fullSequence.append(Character.toString(inputChar));
					break;
				}
			}	
			
		} catch (IOException e) {
			System.err.print("Invalid File");
			e.printStackTrace();
		}		
		return fullSequence;
	}

	//Method to parse the arguments
	private static void parseArgs(String args[]) {
		if(args.length < 5 || args.length > 6) printUsage();

		try{			
			cacheArg = Integer.parseInt(args[0]);
			if(cacheArg == 1) 
				cache = new Cache(Integer.parseInt(args[4]));
			else if(cacheArg == 0) 
				cache = null;
			else printUsage();			
			
			deg = Integer.parseInt(args[1]);
			//if degree argument is set to 0 find the optimal deg within memory block of size 4096
			//node includes (2*t-1)*(8+4) + (2*t)*8 + 4 + 4 + 8 bytes
			//(2*t-t)*(8+4) + (2*t)*8 + 4 + 4 + 8 = 4096 => t = 145 => degree = 145
			if(deg == 0) deg = 145;
			else if(deg < 2) printUsage();			
			
			gbkFilename = args[2];
			
			sequenceSize = Integer.parseInt(args[3]);
			if(sequenceSize < 1 || sequenceSize > 31) 
				printUsage();
			
			if (args.length > 3) 
				debug = Integer.parseInt(args[5]);
		}catch(NumberFormatException e){
			printUsage();
		}

	}
	
	//Usage Method
	private static void printUsage()
	{
		System.err.println("Usage: Java GeneBankCreateBTree <cache 0/1> <degree> <gbk file> <sequence length> <cache size> [<debug level>]");
		System.err.println("<cache>: 0 for no cache or 1 to use a cache");
		System.err.println("<degree>: If 0 program will find optimal degree");
		System.err.println("<gbk file>: GeneBank File");
		System.err.println("<sequence length>: Desired sequence length beween 1-31");
		System.err.println("<Cache Size>: Specify Cache size");
		System.err.println("[<Debug Level>]: 0 for helpful diagnostics, 1 to dump.txt file ");
		System.exit(1);
	}

}
