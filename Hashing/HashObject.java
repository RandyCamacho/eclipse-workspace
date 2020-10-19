import java.math.BigInteger;

public class HashObject {
	private int hashType;
	private int frequency;
	private int key =  -2147483648;			//2 to the 31 power
	private BigInteger keyBigInt;
	private Integer objectNumber = null;
	private String objectString;
	
	
	public HashObject(int keyInput) {
		objectNumber = keyInput;
		key = keyInput;
		hashType = 0;
		frequency = 1;
		
	}

	public HashObject(int keyInput, int hashTypeInput) {
		objectNumber = keyInput;
		key = keyInput;
		hashType = hashTypeInput;
	}
	
	public HashObject(BigInteger bigIntKeyInput, int hashTypeInput, String inputString) {
		keyBigInt = bigIntKeyInput;
		hashType = hashTypeInput;
		objectString = inputString;
	}
	
	public int getKey() {
		return key;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public boolean equals(int kInput) {
		if (kInput == key) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equals(BigInteger bigIntInput) {
		if (bigIntInput.equals(keyBigInt)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void increaseFrequency() {
		frequency++;
	}
	
	public String toString() {
		String output;
		if (objectNumber != null) {
			output = "" + key;
		} else if (objectString != null) {
			output = objectString;
		} else {
			output = "error";
		}
		return output;
	}
}
