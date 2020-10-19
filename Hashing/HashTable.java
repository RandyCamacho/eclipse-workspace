import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HashTable {
	private int mSize;		
	private int[] twinPrimes = {95789, 95801, 95957, 95987}; //twin primes: add 2
	private long nKeys;
	private long duplicates = 0;
	private long probes = 0;
	private double alpha;
	private double hashTypeInput;
	private HashObject[] hashTable;
	
	public HashTable(double alphaInput, boolean print) {
		alpha = alphaInput;
		tableSize(print);
		hashTable = new HashObject[mSize];
	}
	
	public void tableSize(boolean printSize) {
		int i = 0;
		while (i < twinPrimes.length) {
			mSize = twinPrimes[i] + 2;
			nKeys = (int) Math.floor(alpha * mSize);
			if (nKeys <= mSize) {
				i = twinPrimes.length;
			} else if (nKeys > mSize && i == (twinPrimes.length - 1)) {
				System.out.println("Unable to use load factor, choosing largest table size");
			}
			i++;
		}
		if (printSize) {
			System.out.println("A good table size is found: " + mSize);
		}
		nKeys = 0;
	}
	
	public void tableSize() {
		int i = 0;
		while (i < twinPrimes.length) {
			mSize = twinPrimes[i] + 2;
			nKeys = (int) Math.floor(alpha * mSize);
			if (nKeys <= mSize) {
				i = twinPrimes.length;
			} else if (nKeys > mSize && i == (twinPrimes.length - 1)) {
				System.out.println("Unable to use load factor, choosing largest table size");
			}
			i++;
		}
		System.out.println("A good table size is found: " + mSize);
		nKeys = 0;
	}
	
	public double add(long key, int hashType) {
		hashTypeInput = hashType;
		int hashResult = hashCode(key, hashType);
		nKeys++;
		if (hashResult == -1) {
			System.out.println("Out of space");
			nKeys--;
		}
		return ((nKeys*1.0 - duplicates) / (mSize*1.0));
	}
	
	public double add(int key, int hashType) {
		hashTypeInput = hashType;
		int hashResult = hashCode(key, hashType);
		nKeys++;
		if (hashResult == -1) {
			System.out.println("Out of space");
			nKeys--;
		}
		return ((nKeys*1.0 - duplicates) / (mSize*1.0));
	}
	
	public double add(String key, int hashType) {
		hashTypeInput = hashType;
		int hashResult = hashCode(key, hashType);
		nKeys++;
		if (hashResult == -1) {
			System.out.println("Out of space");
			nKeys--;
		}
		return ((nKeys*1.0 - duplicates) / (mSize*1.0));
	}
	
	public int hashCode(long k, int hashType) {
		long longM = (long) mSize;
		int i = 0;
		long hash = -1;
		long h1;
		long h2;
		boolean openSpace = false;
		if (hashType == 0) {
			while (!openSpace) {
				hash = ((k % mSize) + i) % mSize;
				if (hashTable[(int)hash] == null) {
					hashTable[(int)hash] = new HashObject((int)k, hashType);
					openSpace = true;
				} else if (hashTable[(int)hash].equals(k)) {
					hashTable[(int)hash].increaseFrequency();
					duplicates++;
					openSpace = true;
				}
				probes++;
				i++;
			}
		} else if (hashType == 1) {
			while (!openSpace) {
				h1 = k % longM;
				h2 = 1 + (k % (mSize - 2));
				hash = (h1 + i * h2) %mSize;
				if (hashTable[(int)hash] == null) {
					hashTable[(int)hash] = new HashObject((int)k, hashType);
					openSpace = true;
				} else if (hashTable[(int)hash].equals(k)) {
					hashTable[(int)hash].increaseFrequency();
					duplicates++;
					openSpace = true;
				}
				probes++;
				i++;
			}
		}
		
		return ((int)hash);
	}
	
	public int hashCode(int k, int hashType) {
		int i = 0;
		int hash = -1;
		int h1;
		int h2;
		boolean openSpace = false;
		if (hashType == 0) {
			while (!openSpace) {
				hash = ((k % mSize) + i) % mSize;
				if (hashTable[Math.abs((int)hash)] == null) {
					hashTable[Math.abs((int)hash)] = new HashObject((int)k, hashType);
					openSpace = true;
				} else if (hashTable[Math.abs((int)hash)].equals(k)) {
					hashTable[Math.abs((int)hash)].increaseFrequency();
					duplicates++;
					openSpace = true;
				}
				probes++;
				i++;
			}
		} else if (hashType == 1) {
			hash = 0;
			h1 = 0;
			h2 = 0;
			i = 0;
			while (!openSpace) {
				h1 = k % mSize;
				h2 = 1 + (k % (mSize - 2));
				hash = Math.abs((h1 + i * h2) % mSize);
				if (hashTable[Math.abs((int)hash)] == null) {
					hashTable[Math.abs((int)hash)] = new HashObject(k, hashType);
					openSpace = true;
				} else if (hashTable[Math.abs((int)hash)].equals(k)) {
					hashTable[Math.abs((int)hash)].increaseFrequency();
					duplicates++;
					openSpace = true;
				}
				probes++;
				i++;
			}
		}
		
		return hash;
	}
	
	public int hashCode(BigInteger kBigInt, int hashType, String stringInput) {
		long longM = (long) mSize;
		int i = 0;
		long hash = -1;
		long h1;
		long h2;
		boolean openSpace = false;
		if (hashType == 0) {
			while (!openSpace) {
				hash = ( ( (kBigInt.mod(BigInteger.valueOf(longM)) ).intValue() ) + i)%mSize;
				if (hashTable[Math.abs((int)hash)] == null) {
					hashTable[Math.abs((int)hash)] = new HashObject(kBigInt, hashType, stringInput);
					openSpace = true;
				} else if (hashTable[Math.abs((int)hash)].equals(kBigInt)) {
					hashTable[Math.abs((int)hash)].increaseFrequency();
					duplicates++;
					openSpace = true;
				}
				probes++;
				i++;
			}
		} else if (hashType == 1) {
			while (!openSpace) {
				h1 = (kBigInt.mod(BigInteger.valueOf(longM))).intValue();
				h2 = (BigInteger.valueOf(1).add( (kBigInt.mod(BigInteger.valueOf(mSize - 2))))).intValue();
				hash = (h1 + i*h2) % mSize;
				if (hashTable[Math.abs((int)hash)] == null) {
					hashTable[Math.abs((int)hash)] = new HashObject(kBigInt, hashType, stringInput);
					openSpace = true;
				} else if (hashTable[Math.abs((int)hash)].equals(kBigInt)) {
					hashTable[Math.abs((int)hash)].increaseFrequency();
					duplicates++;
					openSpace = true;
				}
				probes++;
				i++;
			}
		}
		
		return ((int)hash);
	}
	
	public int hashCode(String input, int hashType) {
		BigInteger bigInt = new BigInteger(input.getBytes()); 	//Use big int for probing because string.hashcode 
		if ( nKeys == 2147483647) {								//does not allow for unique numbers
			System.out.println("Out of bounds");				// 2 to the 31 power
		}														
		return hashCode(bigInt, hashType, input);
	}
	
	public long getProbes() {
		return probes;
	}
	
	public void resetProbes() {
		probes = 0;
	}
	
	public long getN() {
		return nKeys;
	}
	
	public void resetN() {
		nKeys=0;
	}
	
	public long getDuplicates() {
		return duplicates;
	}
	
	public void resetDuplicates() {
		duplicates = 0;
	}
	
	
	public void printTable() throws IOException {
		Path relatedPath = Paths.get("");
		String currentPath = relatedPath.toAbsolutePath().toString();
		if (hashTypeInput == 0) {
			PrintWriter out = new PrintWriter(new FileWriter(currentPath + "//linear-dump")); 
			for (int i = 0; i < hashTable.length; i++ ) {
				if (hashTable[i] != null) {
					out.println("table[" + i + "]: " + hashTable[i].toString() + " " + hashTable[i].getFrequency());
				}
			}
			out.close();
		} else if (hashTypeInput == 1) {
			PrintWriter out = new PrintWriter(new FileWriter(currentPath + "//double-dump")); 
			for (int i = 0; i < hashTable.length; i++ ) {
				if (hashTable[i] != null) {
					out.println("table[" + i + "]: " + hashTable[i].toString() + " " + hashTable[i].getFrequency());
				}
			}
			out.close();
		}
	}
}
