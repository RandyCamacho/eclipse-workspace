import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
 * Test Class that uses the cache class. Takes in arguments from the command line and creates 
 * the specified caches.
 * Author: Randy Camacho
 */

public class Test {
	public static void main(String args[]) throws FileNotFoundException {
		int cacheLevel = 0;
		int NH = 0, NH1 = 0, NH2 = 0, NR = 0, NR1 = 0, NR2 = 0;
		
		double HR = 0, HR1 = 0, HR2 = 0;
		
		Cache cache1 = new Cache(1);
		Cache cache2 = new Cache(1);
		
		Scanner fileScan = new Scanner("txt");
		
		int index = 0;
		
		if(args[0].equals("1")) {
			cache1 = new Cache(Integer.valueOf(args[1]));
			System.out.println("First level cache with " + args[1] + " entries has been created");
			
			fileScan = new Scanner(new File(args[2]));
			fileScan.useDelimiter("\\s+");
			
			cacheLevel = 1;
		} else if (args[0].equals("2")) {
			cache1 = new Cache(Integer.valueOf(args[1]));
			cache2 = new Cache(Integer.valueOf(args[2]));
			
			System.out.println("First level cache with " + args[1] + " entries has been created");
			System.out.println("Second level cache with " + args[2] + " entries has been created");
			
			fileScan = new Scanner(new File(args[3]));
			fileScan.useDelimiter("\\s+");
			
			cacheLevel = 2;
		} else {
			System.out.println("Usage: java Test 1 <cache size> <input textfile name> or java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>\n");
			System.exit(0);
		} 
		
		System.out.println(".......Processing.......");
		
		while(fileScan.hasNext()) {
			String word = fileScan.next();
			if (cacheLevel == 1) {
				index = cache1.getObject(word);
				NR += 1;
				if (index == -1) {
					cache1.addObject(word);
				} else {
					cache1.removeObject(index);
					cache1.addObject(word);
					NH += 1;
				}
			} else if (cacheLevel == 2) {
				index = cache1.getObject(word);
				NR1 += 1;
				if(index == -1) {
					cache1.addObject(word);
					index = cache2.getObject(word);
					NR2 += 1;
					if (index == -1) {
						cache2.addObject(word);
					} else {
						cache2.removeObject(index);
						cache2.addObject(word);
						NH2 += 1;
					}
				} else {
					cache1.removeObject(index);
					cache1.addObject(word);
					
					cache2.removeObject(cache2.getObject(word));
					cache2.addObject(word);
					NH1 += 1;
				}
			}
		}
		if(cacheLevel == 1) {
			HR = NH/(NR*1.0);
			System.out.println("Total number of references: " + NR);
			System.out.println("Total number of cache hits: " + NH);
			System.out.println("The global hit ratio: " + HR);
		} else if (cacheLevel == 2) {
			HR = (NH1 + NH2)/(NR1 * 1.0);
			HR1 = NH1/(NR1 * 1.0);
			HR2 = NH2/(NR2 * 1.0);
			System.out.println("The number of global of references: " + (NR1));
			System.out.println("The number of global cache hits: " + (NH1 + NH2));
			System.out.println("The global hit ratio: " + HR);
			System.out.println(" ");
			System.out.println("Number of 1st-level references: " + NR1);
			System.out.println("Number of 1st-level cache hits: " + NH1);
			System.out.println("1st-level cache hit ratio: " + HR1);
			System.out.println(" ");
			System.out.println("Number of 2nd-level references: " + NR2);
			System.out.println("Number of 2nd-level cache hits: " + NH2);
			System.out.println("2nd-level cache hit ratio: " + HR2);
		}
		fileScan.close();
	}
}
