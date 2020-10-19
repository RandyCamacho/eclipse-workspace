import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class GeneBankCreateBTree {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		
		//Argument Checking
		
		if(args.length != 6){
			System.err.println("Invalid arguments.");
			System.err.println("Proper Arguments:");
			System.err.println("\tCache: (1 for yes, 0 for no)");
			System.err.println("\tDegree of tree");
			System.err.println("\tgbk file");
			System.err.println("\tSequence Length");
			System.err.println("\tCache Size: (0 if no cache)");
			System.err.println("\tDebug Level");
			return;
		}
		
		long tStart = System.currentTimeMillis();
		byte debug = 0x00;
		byte cache = 0x00; 				//converting first argument
		int degree = Integer.parseInt(args[1]); 			//second argument
		int sequenceLength = Integer.parseInt(args[3]); 	//third argument
		int cacheSize = Integer.parseInt(args[4]);  //cacheSize
		String gbkFile = args[2];
		
		cache = (byte) Integer.parseInt(args[0]);
		debug = (byte) Integer.parseInt(args[5]);
		Path currentRelativePath = Paths.get("");
		
		
		
		//CACHE START!
		@SuppressWarnings("rawtypes")
		Cache List = null;
		boolean useCache = false;
		
		
		if(cache == 0x01)
		{
			List = new Cache<String>(cacheSize); //create the cache
			useCache = true;
		}
		
				
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		File file;
		file = new File(currentPath + "//" + gbkFile);
		Scanner sc = new Scanner(file);

		if (degree == 0) {
			int blockSize = 4096; 			
			int key = 8; 					//8 bytes = 64 bits this value is accurate for this project
			int pointer = 8; 				//updates at some point
			int nodeSize = 120; 			//updates at some point
			degree = (blockSize + key - nodeSize - pointer)/(2*(key + pointer));
		}
		BTree tree = new BTree(degree);
		boolean endOf = false;
		String input;
		char inputChar;
		
		int charInputCount = 0;
		long treeInput;
		String keyString = "";
		String DNAString = "";
		while (sc.hasNext()) {
			String s = sc.next();
			
			if (s.equals("ORIGIN")) {
				while(endOf == false) {
					input = sc.next();
					for (int i = 0; i < input.length(); i++) {
						inputChar = input.charAt(i);
						
						if (inputChar == 'a' || inputChar == 'A') {
							keyString = keyString + "00";
							DNAString = DNAString + "A";
							charInputCount++;
						} 
						else if (inputChar == 't' || inputChar == 'T') {
							keyString = keyString + "11";
							DNAString = DNAString + "T";
							charInputCount++;
						} 
						else if (inputChar == 'c' || inputChar == 'C') {
							keyString = keyString + "01";
							DNAString = DNAString + "C";
							charInputCount++;
						}
						else if (inputChar == 'g' || inputChar == 'G') {
							keyString = keyString + "10";
							DNAString = DNAString + "G";
							charInputCount++;
						}
						else if (inputChar == '/' || input.equals("////") || input.equals("//")) {
							endOf = true;
						}
						if ( charInputCount >= sequenceLength ) {
							treeInput = Long.parseLong(keyString,2);
						
							if(useCache == true)
								List.cacheAdd(treeInput); 
							tree.insert(DNAString);
							charInputCount = 0;
							keyString = "";
							DNAString = "";
						}
					}
					
				}
				endOf = false;
			}
		}	
		sc.close();
		
		if(useCache == true)
			List.cacheResults();
		
		if (debug == 0x01) {
			PrintWriter writer = new PrintWriter("dump.txt");
		
	    	writer.println(tree.toString());
	    	writer.close();
		}
		long tEnd = System.currentTimeMillis();
		System.out.println("The program ran in " + ((tEnd - tStart) / 1000.0) + " seconds");
	}
}
			