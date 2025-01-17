import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class GeneBankSearch {
	
	static String btreeFileName, queryFileName, metadataFileName;
	static long key;
	static int cacheMax, metaSeqLength, debugLevel, cacheArg;
	static BTree tree;
	static Cache cache;

	public static void main(String args[]) throws URISyntaxException {

		parseArgs(args);
		
		try {		
			BTree tree = new BTree(new File(btreeFileName), new File(metadataFileName), cache);

			System.out.println("Btree File:" + btreeFileName);
			System.out.println("Metadata File:" + metadataFileName);
			System.out.println("Query File:" + queryFileName);

			Scanner queryScanner = new Scanner(new File(queryFileName));
			String curLine = "";
			do{				
				curLine = queryScanner.nextLine();
				long k = tree.sequenceToLong(curLine);
				BTreeNode searchKey = tree.search(tree.root, k);
				if(searchKey == null) return;
				for(int i = 0; i < searchKey.keys.length; i++){
					if(searchKey.keys[i].key == k)
						System.out.println(tree.longToDNAstring(k, metaSeqLength) + " " + searchKey.keys[i].frequency);
				}				
			}while(queryScanner.hasNextLine());			

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//parse command line arguments
	public static void parseArgs(String args[]){
		if(args.length > 5 || args.length < 3 || !args[4].equals("0"))	
			printUsage();
		
		if(args.length == 5)
			debugLevel = Integer.parseInt(args[4]);

		if(args[0].equals("1")){
			cacheArg = Integer.parseInt(args[3]);
			cache = new Cache(cacheMax);
		}else {
			cache = null;
		}

		btreeFileName = args[1];
		queryFileName = args[2];
		metadataFileName = btreeFileName.replace("data", "metadata");
		metaSeqLength = Integer.parseInt(metadataFileName.split("\\.")[4]);
	}

	private static void printUsage(){
		System.err.println("Usage: Java GeneBankSearch "
				+ "<0/1(no/with Cache)> <Btree File>"
				+ " <Query File> <Cache Size> [<Debug level>]");
		System.exit(1);
	}

}
