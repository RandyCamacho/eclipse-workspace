public class TreeObject {
	public long key;
	public int frequency;
	
	public TreeObject(long key, int freq) {
		this.key = key;
		this.frequency = freq;
	}
	
	public TreeObject(long key) {
		this.key = key;
		frequency = 1;
	}
	
	public TreeObject() {
		this.key = -1L;
		frequency = 0;
	}
}
