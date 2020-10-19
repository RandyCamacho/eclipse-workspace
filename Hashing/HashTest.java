import java.io.File;
import java.util.concurrent.TimeUnit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import static java.lang.Thread.currentThread;

public class HashTest {
	public static void main(String[] args) throws IOException {
		int inputType = Integer.parseInt(args[0]);
		int debugLevel = 0;
		double loadFactor = Double.parseDouble(args[1]);
		HashTable hashTable = new HashTable(loadFactor, true);
		HashTable hashTable2 = new HashTable(loadFactor, false);
		if (args.length >= 3) {
			debugLevel = Integer.parseInt(args[2]);
		}
		if (inputType == 1) {
			System.out.println("Data source type: Random() int\n");
			double a = 0;
			int hashType = 0;
			System.out.println("Using Linear Hashing....");
			while (a < loadFactor) {
				Random rnd = new Random();
				a = hashTable.add(rnd.nextInt(2147483647) + 0, hashType);
			}
			System.out.println("Input " + (hashTable.getN()) + " elements, of which " + hashTable.getDuplicates() + " duplicates");
			System.out.printf("load factor = %.2f, Avg. no. of probes %f\n\n", loadFactor, (hashTable.getProbes()*1.0)/(hashTable.getN()*1.0) );
			hashType = 1;
			a = 0;
			System.out.println("Using Double Hashing....");
			while (a < loadFactor) {
				Random rand = new Random();
				a = hashTable2.add(rand.nextInt(2147483647) + 0, hashType);
			}
			System.out.println("Input " + (hashTable2.getN()) + " elements, of which " + hashTable2.getDuplicates() + " duplicates");
			System.out.printf("load factor = %.2f, Avg. no. of probes %f\n", loadFactor, (hashTable2.getProbes()*1.0)/(hashTable2.getN()*1.0) );
		} else if (inputType == 2) {
			System.out.println("Data source type: System.currentTimeMillis()\n");
			System.out.println("Please wait a moment\n");
			double alpha = 0;
			int hashType = 0;
			System.out.println("Using Linear Hashing....");
			while (alpha < loadFactor) {
				alpha = hashTable.add((int)System.currentTimeMillis(), hashType);
			}
			System.out.println("Input " + (hashTable.getN()) + " elements, of which " + hashTable.getDuplicates() + " duplicates");
			System.out.printf("load factor = %.2f, Avg. no. of probes %f\n\n", loadFactor, (hashTable.getProbes()*1.0)/(hashTable.getN()*1.0) );
			hashType = 1;
			alpha = 0;
			System.out.println("Using Double Hashing....");
			while (alpha < loadFactor) {
				alpha = hashTable2.add((int)System.currentTimeMillis(), hashType);
			}
			System.out.println("Input " + (hashTable2.getN()) + " elements, of which " + hashTable2.getDuplicates() + " duplicates");
			System.out.printf("load factor = %.2f, Avg. no. of probes %f\n", loadFactor, (hashTable2.getProbes()*1.0)/(hashTable2.getN()*1.0) );
		} else if (inputType == 3) {
			System.out.println("Data source type: word-list");
			System.out.println();
			Path currentRelativePath = Paths.get("");
			String currentPath = currentRelativePath.toAbsolutePath().toString();
			Scanner sc = new Scanner(new File(currentPath + "//word-list"));
			Scanner sc2 = new Scanner(new File(currentPath + "//word-list"));
			double alpha = 0;
			int hashType = 0;
			System.out.println("Using Linear Hashing....");
			while (alpha < loadFactor && sc.hasNext()) {
				String s = sc.next();
				alpha = hashTable.add(s, hashType);
			}
			System.out.println("Input " + (hashTable.getN()) + " elements, of which " + hashTable.getDuplicates() + " duplicates");
			System.out.printf("load factor = %.2f, Avg. no. of probes %f\n\n", loadFactor, (hashTable.getProbes()*1.0)/(hashTable.getN()*1.0));
			hashType = 1;
			alpha = 0;
			System.out.println("Using Double Hashing....");
			while (alpha < loadFactor && sc.hasNext()) {
				String s = sc2.next();
				alpha = hashTable2.add(s, hashType);
				}
			System.out.println("Input " + (hashTable2.getN()) + " elements, of which " + hashTable2.getDuplicates() + " duplicates");
			System.out.printf("load factor = %.2f, Avg. no. of probes %f\n", loadFactor, (hashTable2.getProbes()*1.0)/(hashTable2.getN()*1.0) );
		}
		if (debugLevel == 1) {
			hashTable.printTable();
			hashTable2.printTable();
		}	
	}
}
