import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneBankSearch {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {
	
	//Argument Checking
		if(args.length != 5){
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
		
		//Input Handling
		long tStart = System.currentTimeMillis();
		@SuppressWarnings("unused")
		byte debug = 0x00;
		byte cache = 0x00;
		String btreeFile = args[1];
		String queryFile = args[2];
		int cacheSize = Integer.parseInt(args[3]);
		
		cache = (byte) Integer.parseInt(args[0]);
		debug = (byte) Integer.parseInt(args[4]);
		
		
		//Start Cache
		Cache List = null;
		boolean weGonnaUseCache = false;
				
		if(cache == 0x01)
		{
			List = new Cache<String>(cacheSize); //create the cache
			weGonnaUseCache = true;
		}
		 //Input Handle end
		
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		File query = new File(currentPath + "\\" + queryFile);
		@SuppressWarnings("resource")
		Scanner queryScanner = new Scanner(query);
		PrintWriter out = new PrintWriter(new FileWriter(currentPath + "\\" + "DNA Results"));
		while (queryScanner.hasNextLine()) {
			String searchString = queryScanner.nextLine();
			out.println(searchString + " freq = " + btreeSearcher(btreeFile, searchString, weGonnaUseCache, List));
		}
		out.close();
		if(weGonnaUseCache == true)
			List.cacheResults();
		System.out.println("\nPlease find your DNA results in a file called \"DNA Results\" ");
		System.out.println("This program prints to this file instead of the console to improve speed by a large factor!");
		long tEnd = System.currentTimeMillis();
		System.out.println("The program ran in " + ((tEnd - tStart) / 1000.0) + " seconds");
	}
	
	public String queryFinder(Scanner query) {
		return query.next();
	}
	
	@SuppressWarnings("unchecked")
	public static int btreeSearcher(String btreeFile, String searchingString,boolean weGonnaUseCache, @SuppressWarnings("rawtypes") Cache List) throws FileNotFoundException {
		char inputChar;
		int frequency = 0;
		String keyString ="";
		for (int i = 0; i < searchingString.length(); i++) {
			inputChar = searchingString.charAt(i);
			if (inputChar == 'a' || inputChar == 'A') {
				keyString = keyString + "00";
			} else if (inputChar == 't' || inputChar == 'T') {
				keyString = keyString + "11";
			} else if (inputChar == 'c' || inputChar == 'C') {
				keyString = keyString + "01";
			} else if (inputChar == 'g' || inputChar == 'G') {
				keyString = keyString + "10";
			}
		}
		String searchString = Long.toString( (Long.parseLong(keyString,2)) );
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		Pattern pattern = Pattern.compile("(\\d+)\\s+(\\d+)");
		File btree = new File(currentPath + "\\" + btreeFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(btree);
		if(weGonnaUseCache == true)
			List.cacheAdd(searchString);
		while (scanner.hasNextLine()) {
		   String lineFromFile = scanner.nextLine();
		   Matcher matcher = pattern.matcher(lineFromFile);
		   while (matcher.find()) {
			   try {
				   if ( (matcher.group(1)).equals(searchString) ) {
					   frequency = Integer.parseInt(matcher.group(2));
				   }
			   } catch (IllegalStateException e) {
				   
			   }
			   try {
			   } catch (IllegalStateException e) {
				   
			   }
		   } 
		}
		return frequency;
	}

}
